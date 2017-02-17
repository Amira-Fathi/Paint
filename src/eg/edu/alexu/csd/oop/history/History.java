package eg.edu.alexu.csd.oop.history;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import eg.edu.alexu.csd.oop.draw.Shape;

public class History implements MyHistory{
	private Stack<Shape[]> cur,next;
	private int cap;
	public History (int cap){
		this.cap=cap;
		cur = new Stack<Shape[]>();
		next = new Stack<Shape[]>();
	}
	@Override
	public void add(ArrayList<Shape>a){
		Shape[] s = new Shape[a.size()];
		s = a.toArray(s);
		if (s!=null) cur.push(s);
		if (!next.empty()) next.clear();
		if (cur.size()>cap) cur.remove(0);
	}
	@Override
	public void undo(){
		if (cur.size()==0){
			throw new RuntimeException("Empty History");
		}
		next.push(cur.pop());
	}
	@Override
	public void redo(){
		if (next.size()==0) {
			throw new RuntimeException("Empty History");
		}
		cur.push(next.pop());
	}
	@Override
	public void clear(){
		cur.clear();
		next.clear();
	}
	@Override
	public ArrayList<Shape> cur(){
		if (cur.isEmpty()){
			throw new RuntimeException("Emty History");
		}
		ArrayList<Shape> r = new ArrayList<Shape>(Arrays.asList(cur.peek()));
		return r;
	}
	@Override
	public int size() {
		return cur.size();
	}
}
