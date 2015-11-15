package eg.edu.alexu.csd.oop.sql;

public class Condition {
	char o;
	public Condition (char o){
		this.o=o;
	}
	public boolean compare (String v1,String v2){
		switch (o) {
		case '>':
			if (Integer.parseInt(v1)>Integer.parseInt(v2)) return true;
			else return false;
		case '<':
			if (Integer.parseInt(v1)<Integer.parseInt(v2)) return true;
			else return false;
		case '=':
			if (v1.equals(v2))return true;
			else return false;
		case 'n':return true;	
		}
		return true;
	}
}
