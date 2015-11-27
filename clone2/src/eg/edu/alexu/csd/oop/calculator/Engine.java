package eg.edu.alexu.csd.oop.calculator;
import java.io.FileNotFoundException;
import java.io.IOException;

import history.Expression;
import history.History;
public class Engine implements Calculator{
	private static Engine engine=null;
	private static MyCalculator myCalculator;
	private History history;
	private Memory memory;
	private boolean error;
	private Engine(){
		myCalculator = MyCalculator.getCalculator();
		history=new History(5);
		memory=new Memory();
	}
	// check before that s isn't the current expression.
	public static Engine getInstance(){
		if(engine==null)engine=new Engine();
		return engine;
	}
	public static void destoryInstance(){
		engine = null;
   }
	@Override
	public void input(String s){
		char c='#';	
		double a=0,b=0;
		error=false;
		try{
			for (int i=0;i<s.length();i++){
				c=s.charAt(i);
				if (c=='+'||c=='-'||c=='*'||c=='/'){
					String s1=s.substring(0,i);
					String s2=s.substring(i+1,s.length());
					a=Double.parseDouble(s1);
					b=Double.parseDouble(s2);
					break;
				}
			}
			if (c=='#'){
				a=Double.parseDouble(s);
				history.add(new Expression(a,0,'+'));
			}
			else {
				history.add(new Expression(a,b,c));
			}
		}catch(Exception ex){
			error=true;
		}
	}
	@Override
	public String getResult(){
		if (error){
			throw new RuntimeException();
		}
		Expression cur = history.cur();
		switch (cur.operation){
		case '+':
			return Double.toString(myCalculator.add(cur.first,cur.second));
		case '-':
			return Double.toString(myCalculator.subtract(cur.first,cur.second));
		case '*':
			return Double.toString(myCalculator.multiply(cur.first,cur.second));
		case '/':
			return Double.toString(myCalculator.divide(cur.first,cur.second));
		}
		return null;
		
	}

	@Override
	public String current(){
		Expression cur = history.cur();
		if (cur==null){
			error=true;
			return null;
		}
		else{
			String s="";
			double a=cur.first;
			double b=cur.second;
			error=false;
			if(a==(double)((int)a)){s+=(int)a;}
			else s+=Double.toString(a);
			s+=cur.operation;
			if(b==(double)((int)b)){s+=(int)b;}
			else s+=Double.toString(a);
			return s;
		}
	}
	@Override
	public String prev(){
		Expression pre = history.prev();
		if (pre==null){
			if (history.cur()==null)error=true;
			else error=false;
			return null;
		}
		else{ 
			String s="";
			error=false;
			double a=pre.first;
			double b=pre.second;
			if(a==(double)((int)a)){s+=(int)a;}
			else s+=Double.toString(a);
			s+=pre.operation;
			if(b==(double)((int)b)){s+=(int)b;}
			else s+=Double.toString(a);
			return s;
		}
	}
	@Override
	public String next(){
		Expression next = history.next();
		if (next==null){
			if (history.cur()==null)error=true;
			else error=false;
			return null;
		}
		else{
			String s="";
			error=false;
			double a=next.first;
			double b=next.second;
			if(a==(double)((int)a)){s+=(int)a;}
			else s+=Double.toString(a);
			s+=next.operation;
			if(b==(double)((int)b)){s+=(int)b;}
			else s+=Double.toString(a);
			return s;
		}
	}
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
