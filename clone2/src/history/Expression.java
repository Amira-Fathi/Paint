package history;

public class Expression {
	public double first,second;
	public char operation;
	public Expression(){
		first=second=0.0;
		operation='#';
	}
	public Expression(double f,double s,char c){
		first=f;
		second=s;
		operation=c;
	}
}
