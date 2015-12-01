package eg.edu.alexu.csd.oop.draw;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
public class Ellipse extends ShapeEngine implements Cloneable{
	private Map<String,Double> ellipse;
	public Ellipse(){
		ellipse = new HashMap<String,Double> ();
		ellipse.put("width",null);
		ellipse.put("height",null);
		setProperties(ellipse);
	}
	@Override
	public void draw(Graphics canvas){
		ellipse=getProperties();
		Double w,h;	
		w=ellipse.get("width");
		h=ellipse.get("height");
		Point p = getPosition(); 
		if (getColor()!=null) canvas.setColor(getColor());
		canvas.drawOval(p.x-w.intValue()/2, p.y-h.intValue()/2, w.intValue(), h.intValue());
		if (getFillColor()!=null){
			canvas.setColor(getFillColor());
			canvas.fillOval(p.x-w.intValue()/2, p.y-h.intValue()/2, w.intValue(), h.intValue());
		}
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		Shape New=null;
		try {
			New = (Shape)(Class.forName(this.getClass().getName())).newInstance();
			New.setPosition(this.getPosition());
			Color c1,c2;
			if (getColor()==null)c1=null;
			else c1 = new Color(getColor().getRGB());
			if (getFillColor()==null) c2=null;
			else c2 = new Color(getFillColor().getRGB());
			New.setColor(c1);
			New.setFillColor(c2);
			Double[]values = new Double[this.getProperties().size()];
			int temp=0;
			for (Map.Entry<String,Double>entry : this.getProperties().entrySet()){
				values[temp++]=entry.getValue();
			}
			Map<String,Double> to = New.getProperties();
			temp=0;
			for (Map.Entry<String,Double>entry : to.entrySet()){
				entry.setValue(values[temp++]);
			}
		}catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return New;
	}
}
