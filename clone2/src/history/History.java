package history;
import java.util.Iterator;
import java.util.Stack;
public class History implements MyHistory{
	private Stack<Expression> cur,next; 
	private int cap;
	public History (int capacity) {
		cap=capacity;
		cur = new Stack<Expression> ();
		next = new Stack<Expression>();
	}
	@Override
	public void add(Expression exp) {
		if (exp==null) throw new NullPointerException();
		cur.add(exp);
		if (next.size()!=0)next.clear();
		if (cur.size()>cap) cur.remove(0);
	}
	@Override
	public Expression prev() {
		if (cur.size()<=1)return null; 
		next.push(cur.pop());
		return cur.peek();
	}
	@Override
	public Expression cur() {
		if (cur.size()==0)return null;
		return cur.peek();
	}
	@Override
	public Expression next() {
		if (next.size()==0)return null;
		cur.push(next.pop());
		return cur.peek();
	}
	@Override
	public Iterator<Expression> iterator() {
		return cur.iterator();
	}
	@Override
	public void clear() {
		cur.clear();
		next.clear();
	}
	@Override
	public int size() {
		return cur.size();
	}
}
