package eg.edu.alexu.csd.oop.calculator;
public class MyCalculator{
	// only one object
	private static MyCalculator myCalculator=null;
	private MyCalculator(){}
	public static MyCalculator getCalculator (){
		if (myCalculator==null){
			myCalculator=new MyCalculator();
		}
		return myCalculator;
	}
	public double add(double a, double b) {
		return a+b;
	}
	public double subtract(double a, double b) {
		return a-b;
	}
	public double multiply(double a, double b) {
		return a*b;
	}
	public double divide(double a, double b) {
		if (b==0.0) throw new RuntimeException("Division By Zero!!");
		return a/b;
	}

}
