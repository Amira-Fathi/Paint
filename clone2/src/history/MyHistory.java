package history;

import java.util.Iterator;

public interface MyHistory {
	public void add(Expression exp);
	public Expression prev();
	public Expression cur();
	public Expression next();
	public Iterator<Expression> iterator();
	public void clear();
	public int size();
}
