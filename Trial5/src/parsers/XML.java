package parsers;
import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import eg.edu.alexu.csd.oop.draw.Shape;
public class XML implements Parser{
	private File data;
	@Override
	public void getPath(String path) {
		data = new File(path);
	}
	@Override
	public ArrayList<Shape> read (){
		ArrayList<Shape> shapes = new  ArrayList<Shape> ();
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(data);
			Element root = doc.getDocumentElement();
			NodeList children = root.getChildNodes();
			
			for (int i=0;i<children.getLength();i++){
				Shape s = (Shape)(Class.forName(children.item(i).getNodeName())).newInstance();
				Map<String,Double> map = s.getProperties();
				//
				Element properties = (Element)((Element)children.item(i)).getElementsByTagName("properties").item(0);
				Element pos = (Element)((Element)children.item(i)).getElementsByTagName("position").item(0);
				Element color = (Element)((Element)children.item(i)).getElementsByTagName("color").item(0);
				Element fillColor = (Element)((Element)children.item(i)).getElementsByTagName("fillColor").item(0);
				//
				if (properties.getTextContent().equals("x")){
					map=null;
				}
				else if (map!=null){
					NodeList list = properties.getChildNodes();
					int temp=0;
					for (Map.Entry<String,Double>entry : map.entrySet()){
						String content = list.item(temp).getTextContent();
						if (!content.equals("x")){
							entry.setValue(Double.parseDouble(content));
						}
						else entry.setValue(null);
						temp++;
					}
				}
				s.setProperties(map);
				String content = pos.getTextContent();
				if (!content.equals("x")){
					String[]points = content.split("\\,");
					s.setPosition(new Point(Integer.parseInt(points[0]),Integer.parseInt(points[1])));
				}
				int rgb = Integer.parseInt(fillColor.getTextContent());
				Color c;
				if (rgb==0)c=null;
				else c = new Color(rgb);
				s.setFillColor(c);
				s.setColor(new Color(Integer.parseInt(color.getTextContent())));
				shapes.add(s);
			}
		}catch(ParserConfigurationException ex){throw new RuntimeException ("ParserConfigurationException");}
		catch(SAXException ex){throw new RuntimeException ("SAXException");}
		catch (IOException ex){throw new RuntimeException ("IOException");}
		catch (InstantiationException ex){throw new RuntimeException ("InstantiationException");}
		catch(IllegalAccessException ex){throw new RuntimeException ("IllegalAccessException");}
		catch(ClassNotFoundException ex1 ) {throw new RuntimeException ("ClassNotFoundException");}
		return shapes;
	}
	@Override
	public void write(ArrayList<Shape> shapes){
		Shape[] arr = new Shape[shapes.size()];
		arr = shapes.toArray(arr);
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element parent = doc.createElement("shapes");
			for (int i=0;i<arr.length;i++){
				if (arr[i]==null)continue;
				Element shape;
				shape = doc.createElement(arr[i].getClass().getName());
				Element prop = doc.createElement("properties");
				shape.appendChild(prop);
				parent.appendChild(shape);
				Map<String,Double> map = arr[i].getProperties();
				if (map==null){
					prop.appendChild(doc.createTextNode("x"));
				}
				else{
					for (Map.Entry<String,Double>entry : map.entrySet()){
						Element e;
						// if key == null
						e = doc.createElement(entry.getKey());
						String content;
						if (entry.getValue()==null)content="x";
						else{
							content = Double.toString(entry.getValue());
						}
						e.appendChild(doc.createTextNode(content));
						prop.appendChild(e);
					}
				}
				Element color = doc.createElement("color");
				Element fillColor = doc.createElement("fillColor");
				Element pos = doc.createElement("position");
				String point;
				if (arr[i].getPosition()==null) point = "x";
				else{ 
					point = ((Integer)(arr[i].getPosition().x)).toString()+","+((Integer)(arr[i].getPosition().y)).toString();
				}
				pos.appendChild(doc.createTextNode(point));
				shape.appendChild(pos);
				Color c1=arr[i].getColor();
				Color c2=arr[i].getFillColor();
				Integer rgb1,rgb2;
				if (c1==null){
					rgb1=0;
				}
				else {
					rgb1 = c1.getRGB();
				}
				if (c2==null){
					rgb2=0;
				}
				else {
					rgb2=c2.getRGB();
				}
				//
				color.appendChild(doc.createTextNode(rgb1.toString()));
				fillColor.appendChild(doc.createTextNode(rgb2.toString()));
				//
				shape.appendChild(color);
				shape.appendChild(fillColor);
			}
			doc.appendChild(parent);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(data);
			transformer.transform(source, result);
		}catch (ParserConfigurationException ex1) {
			throw new RuntimeException("ParserConfigurationException");
		}catch (TransformerConfigurationException ex){
		
				throw new RuntimeException ("TransformerConfigurationException");

		}catch (TransformerException ex){
			throw new RuntimeException ("TransformerException");
		}
		
	}
}
