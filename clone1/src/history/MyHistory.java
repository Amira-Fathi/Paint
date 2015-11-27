package history;

import java.util.Iterator;

public interface MyHistory {
	public void add(String exp);
	public String prev();
	public String cur();
	public String next();
	public Iterator<String> iterator();
	public void clear();
	public int size();
}
