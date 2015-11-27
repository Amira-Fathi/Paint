package history;
import java.util.Iterator;
import java.util.Stack;
public class History implements MyHistory{
	private Stack<String> cur,next; 
	private int cap;
	public History (int capacity) {
		cap=capacity;
		cur = new Stack<String> ();
		next = new Stack<String>();
	}
	@Override
	public void add(String exp){
		if (exp==null) throw new NullPointerException();
		cur.add(exp);
		if (cur.size()+next.size()>cap) cur.remove(0);
		if (next.size()!=0)next.clear();
	}
	@Override
	public String prev(){
		if (cur.size()<=1)return null; 
		next.push(cur.pop());
		return cur.peek();
	}
	@Override
	public String cur() {
		if (cur.size()==0)return null;
		return cur.peek();
	}
	@Override
	public String next(){
		if (next.size()==0)return null;
		cur.push(next.pop());
		return cur.peek();
	}
	@Override
	public Iterator<String> iterator() {
		return cur.iterator();
	}
	@Override
	public void clear() {
		cur.clear();
		next.clear();
	}
	public int size (){
		return cur.size();
	}
}