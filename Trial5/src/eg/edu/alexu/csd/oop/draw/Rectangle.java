package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

// position --> most upper left point

public class Rectangle extends ShapeEngine implements Cloneable{	
	private Map<String,Double> rect;
	public Rectangle(){
		rect=  new HashMap<String,Double> ();
		rect.put("width",null);
		rect.put("height",null);
		setProperties(rect);
	}
	
	@Override
	public void draw(Graphics canvas) {	
		rect=getProperties();
		Double w,h;
		w=rect.get("width");
		h=rect.get("height");
		Point p = getPosition();
		if (getColor()!=null) canvas.setColor(getColor());
		canvas.drawRect(p.x-w.intValue()/2, p.y-h.intValue()/2, w.intValue(), h.intValue());
		if (getFillColor()!=null){
			canvas.setColor(getFillColor());
			canvas.fillRect(p.x-w.intValue()/2, p.y-h.intValue()/2, w.intValue(), h.intValue());
		}
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		Shape New=null;
		try {
			New = (Shape) (Class.forName(this.getClass().getName())).newInstance();
			New.setColor(this.getColor());
			New.setFillColor(this.getFillColor());
			New.setPosition(this.getPosition());
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

		/*try {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(this);
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bais);
		return (Circle) ois.readObject();
	} catch (IOException e) {
		return null;
	} catch (ClassNotFoundException e) {
		return null;
	}*/
		return New;
	}
}
