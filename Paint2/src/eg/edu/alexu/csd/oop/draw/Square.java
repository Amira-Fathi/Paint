package eg.edu.alexu.csd.oop.draw;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
public class Square extends ShapeEngine implements Cloneable{
	private Map<String,Double> sq;
	public Square (){ 
		sq= new HashMap<String,Double> ();
		sq.put("side_length",null);
		setProperties(sq);
	}	
	@Override
	public void draw(Graphics canvas) {
		sq=getProperties();
		Double l;
		l=sq.get("side_length");
		Point p = getPosition();
		if (getColor()!=null) canvas.setColor(getColor());
		canvas.drawRect(p.x-l.intValue()/2, p.y-l.intValue()/2,l.intValue(),l.intValue());
		if (getFillColor()!=null){
			canvas.setColor(getFillColor());
			canvas.fillRect(p.x-l.intValue()/2, p.y-l.intValue()/2,l.intValue(),l.intValue());
		}
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		Shape New=null;
		try {
			New = (Shape) (Class.forName(this.getClass().getName())).newInstance();
			New.setPosition(this.getPosition());
			Color c1,c2;
			if (getColor()==null)c1=null;
			else c1 = new Color(getColor().getRGB());
			if (getFillColor()==null) c2=null;
			else c2 = new Color(getFillColor().getRGB());
			New.setColor(c1);
			New.setFillColor(c2);
			Double[] values = new Double[this.getProperties().size()];
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
