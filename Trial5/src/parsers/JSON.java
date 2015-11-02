package parsers;
import java.awt.Color;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import eg.edu.alexu.csd.oop.draw.Shape;

public class JSON implements Parser{
	
	private File data ;
	public void getPath(String path) {
		data = new File(path);
	}
	public ArrayList<Shape> read(){
		ArrayList<Shape> shapes = new  ArrayList<Shape> ();
		try{
    		FileInputStream fis = new FileInputStream(data);
    		@SuppressWarnings("resource")
			BufferedReader bu = new BufferedReader(new InputStreamReader(fis));
    		String sCurrentLine,json ="";
    		while ((sCurrentLine = bu.readLine()) != null) {
    			json=json+sCurrentLine;
            }
    		String[] parts = json.replaceAll("\"|\\{|\\}|\"","").split("\"?(:|,)(?![^\\{]*\\})(?![^\\[]*\\])\"?");
    		String[] arr1=new String[20];
    		String[] arr2=new String[20];
    		int j=0;
    		 for (int i = 0; i < parts.length -1; i+=2){
    		    	arr1[j]=parts[i];
    		    	arr2[j]=parts[i+1];
    		    	Shape s = (Shape)(Class.forName(arr1[j])).newInstance();
    		    	Map<String,Double> map = s.getProperties();
    		    	String[] myParts=arr2[j].replaceAll("\\[|\\]|\"", "").split(",");
    		    	String[] position=myParts[0].split("\\:");
    		    	if (!position[1].equals("x")){
    		    		String[] points = position[1].split("\\;");
    		    		s.setPosition(new Point(Integer.parseInt(points[0]),Integer.parseInt(points[1])));
    		    	}else s.setPosition(null);
					String[] color=myParts[1].split("\\:");
    		    	s.setColor(new Color(Integer.parseInt(color[1])));
    		    	String[] fill=myParts[2].split("\\:");
    		    	int rgb = Integer.parseInt(fill[1]);
    		    	Color c;
    		    	if (rgb==0)c=null;
    		    	else c = new Color(rgb);
    		    	s.setFillColor(c);
    		    	if (map!=null){
	    		    	for(int k=3;k<myParts.length;k++){
	    		    		String[] lastPart=myParts[k].split("\\:");
	    		    		if (!lastPart[1].equals("x")){
	    		    			map.put(lastPart[0],Double.parseDouble(lastPart[1]));
	    		    		}
	    		    	}
    		    	}
    		    	s.setProperties(map);
    		    	shapes.add(s);
    		    	j++;
    		    }	
		}catch (FileNotFoundException ex){
			throw new RuntimeException ("FileNotFoundException");
		}
		catch(IOException ex){throw new RuntimeException ("IOException");}
		catch (InstantiationException ex){throw new RuntimeException ("InstantiationException");}
		catch (IllegalAccessException ex){throw new RuntimeException ("IllegalAccessException");}
		catch (ClassNotFoundException ex){throw new RuntimeException ("ClassNotFoundException");}
		return shapes;
	}
	public void write(ArrayList<Shape> shapes)  {
		Shape[] arr = new Shape[shapes.size()];
		arr = shapes.toArray(arr); 
		try{
			FileWriter fw = new FileWriter(data.getAbsoluteFile());
	        BufferedWriter bw = new BufferedWriter(fw);
	        bw.write("{");
	        for (int i=0;i<arr.length;i++){
	        	if (arr[i]==null)continue;
	        	String shape = arr[i].getClass().getName();
	        	bw.write("\""+shape+"\""+":"+"[");
	        	bw.newLine();
	        	String point;
	        	if (arr[i].getPosition()==null)point="x";
	        	else point = ((Integer)(arr[i].getPosition().x)).toString()+";"+((Integer)(arr[i].getPosition().y)).toString();
				bw.write("\""+"position"+"\""+":"+"\""+point+"\",");
				bw.newLine();
				Integer rgb1,rgb2;
				Color c1=arr[i].getColor(),c2=arr[i].getFillColor();
				if (c1==null)rgb1=0;
				else rgb1 = c1.getRGB();
				if (c2==null)rgb2=0;
				else rgb2=c2.getRGB();
				bw.write("\""+"color"+"\""+":"+"\""+rgb1.toString()+"\",");
				bw.newLine();
				bw.write("\""+"fillColor"+"\""+":"+"\""+rgb2.toString()+"\",");
				bw.newLine();
				Map<String,Double> map = arr[i].getProperties();
				if (map!=null){
					int temp=0;
					for (Map.Entry<String,Double>entry : map.entrySet()){
						String e = entry.getKey();
						String value;
						if (entry.getValue()==null)value="x";
						else value=Double.toString(entry.getValue());
						bw.write("\""+e+"\""+":"+"\""+value+"\"");
						temp++;
						if (temp<map.size()) bw.write(",");
						bw.newLine();
					}
				}
				if(i==arr.length-1)
					bw.write("]");
				else
				   bw.write("]"+",");
				bw.newLine();
	        }
	        bw.write("}");
	        bw.close();
		}catch(IOException ex){throw new RuntimeException ("IOException");}
	}
}
