package eg.edu.alexu.csd.oop.parsers;

import java.util.ArrayList;
import eg.edu.alexu.csd.oop.draw.Shape;

public interface Parser {
	void getPath(String path);
	ArrayList<Shape> read ();
	void write(ArrayList<Shape> shapes);
}
