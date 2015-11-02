package history;

import java.util.ArrayList;

import eg.edu.alexu.csd.oop.draw.Shape;

public interface MyHistory {
	public void add (ArrayList<Shape> a);
	public ArrayList<Shape> cur();
	public void undo ();
	public void redo ();
	public void clear();
	public int size();
}
