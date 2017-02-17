package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.HashMap;
import java.util.Map;

public class Triangle extends ShapeEngine implements Cloneable{
	private Map<String,Double> triangle;
	public Triangle (){
		triangle = new HashMap<String,Double> ();
	  	triangle.put("side1", null);
	  	triangle.put("side2", null);
	  	triangle.put("included_angle", null);
		setProperties(triangle);	
	}
	private int i (Double v){
		if (v-0.5 >= Math.floor(v)){
			return (int)Math.ceil(v);
		}
		else{
			return (int)Math.floor(v);
		}
	}
	@Override
	public void draw(Graphics canvas) {
		triangle=getProperties();
		Double a,b,includedA;
		a=triangle.get("side1");
		b=triangle.get("side2");
		includedA=triangle.get("included_angle");
		Point p = getPosition(); 
		Point[] vertex = new Point[3];
		vertex[0] = new Point(i(p.getX()), i(p.getY()));
		vertex[1] = new Point(i(p.getX()+a), i(p.getY()));
		vertex[2] = new Point(i(p.getX()+(b*Math.cos((includedA*Math.PI)/180.0))),i(p.getY()-(b*Math.sin((includedA*Math.PI)/180.0))));
		if (getColor()!=null) canvas.setColor(getColor());
		Polygon poly = new Polygon ();
		poly.addPoint(vertex[0].x,vertex[0].y);
		poly.addPoint(vertex[1].x,vertex[1].y);
		poly.addPoint(vertex[2].x,vertex[2].y);
		canvas.drawPolygon(poly);
		if (getFillColor()!=null){
			canvas.setColor(getColor());
			canvas.fillPolygon(poly);
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
