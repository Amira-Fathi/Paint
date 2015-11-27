package eg.edu.alexu.csd.oop.calculator;
import java.io.FileNotFoundException;
import java.io.IOException;
import history.History;
public class Engine implements Calculator{
	private static Engine engine=null;
	private static MyCalculator myCalculator;
	private History history;
	private Memory memory;
	private double a,b;
	private char operation;
	private Engine(){
		myCalculator = MyCalculator.getCalculator();
		history=new History(5);
		memory=new Memory();
	}
	public static Engine getInstance(){
		if(engine==null)engine=new Engine();
		return engine;
	}
	@Override
	public void input(String s){
		String t=s;
		try{
			char c='e';
			operation = 'e';
			for (int i=0;i<t.length();i++){
				c=t.charAt(i);
				if(c=='+'||c=='-'||c=='/'||c=='*'){
					t = t.substring(0,i)+" "+t.substring(i+1,t.length());
					break;
				}
			}
			String[]part = t.split("\\s+");
			a = Double.parseDouble(part[0]);
			if (c!='+'&&c!='-'&&c!='/'&&c!='*'){
				if (part.length==1){
					operation='d';
					if (!t.equals(history.cur())) history.add(s);
					return;
				}
				else throw new RuntimeException("Not Valid Expression");
			}
			else if (part.length==2){
				b = Double.parseDouble(part[1]);
				operation=c;
				if (c=='/'&&b==0.0)throw new RuntimeException("Division By Zero");
				if (!t.equals(history.cur())) history.add(s);
			}
			else {
				throw new RuntimeException("Not Valid Expression");
			}
		}
		catch( NumberFormatException e){
			throw new RuntimeException("Not Valid Expression");
		}
	}
	@Override
	public String getResult(){
		switch(operation){
			case '+':
				return Double.toString(myCalculator.add(a,b));
			case '-':
				return Double.toString(myCalculator.subtract(a,b));
			case '*':
				return Double.toString(myCalculator.multiply(a,b));
			case '/':
				return Double.toString(myCalculator.divide(a,b));
			case 'd':
				return Double.toString(a);
			case 'e':
				throw new RuntimeException("Not Valid Expression");
		}
		return null;
	}

	@Override
	public String current(){return history.cur();}
	@Override
	public String prev(){return history.prev();}
	@Override
	public String next(){return history.next();}
	@Override
	public void save(){
		try {
			memory.save(history);
		}catch (IOException e) {
			throw new RuntimeException ("Can not Save The file");
		}
	}
	@Override
	public void load(){
		try {
			history=memory.load();
		}
		catch (FileNotFoundException e){
			throw new RuntimeException ("File Not Found:No Data is Saved Before");
		}
	}
}