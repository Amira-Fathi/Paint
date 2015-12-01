package eg.edu.alexu.csd.oop.draw;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Map;
public abstract class ShapeEngine implements Shape{
	private Map<String,Double> p;
	private Color color,fillColor;
	private Point pos;
	public ShapeEngine(){
		pos=null;
		fillColor=null;
		color=Color.black;
	}
	@Override
	public void setPosition(Point position){
		if (position == null) return;
		pos = position;
	}
	@Override
	public Point getPosition() {
		return pos;
	}
	@Override
	public Map<String, Double> getProperties() {
		return p;
	}
	@Override
	public void setProperties(Map<String, Double> properties){
		p=properties;
	}
	@Override
	public void setColor(Color color) {
		this.color=color;
	}
	
	@Override
	public Color getColor() {
		return color;
	}
	@Override
	public void setFillColor(Color color){
		fillColor=color;	
	}
	@Override
	public Color getFillColor() {
		return fillColor;
	}
	@Override
	public abstract Object clone()throws CloneNotSupportedException ;
	@Override
	public abstract void draw(Graphics canvas);
}
