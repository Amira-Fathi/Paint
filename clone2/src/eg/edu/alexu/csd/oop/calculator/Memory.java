package eg.edu.alexu.csd.oop.calculator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Scanner;

import history.Expression;
import history.History;

public class Memory {
	private File data;
	public Memory (){
		data=new File("data.txt");
	}
	public void save (History h) throws IOException{
		PrintWriter writer = new PrintWriter(data);
		writer.print("");
		FileWriter fw = new FileWriter(data.getAbsoluteFile());
		BufferedWriter output = new BufferedWriter(fw);
		Iterator<Expression> it = h.iterator();
		output.write(Integer.toString(h.size()));
		output.newLine();
		while (it.hasNext()){
			Expression e=it.next();
			String s = Double.toString(e.first)+" "+e.operation+" "+Double.toString(e.second);
			output.write(s);
			output.newLine();
		}
		output.close();
	}
	public History load()throws FileNotFoundException{
		Scanner skan = new Scanner(data);
		History h = new History(5);
		int sz = skan.nextInt();
		for (int i=0;i<sz;i++){
			Expression e = new Expression();
			e.first=skan.nextDouble();
			e.operation=skan.next().charAt(0);
			e.second=skan.nextDouble();
			h.add(e);
		}
		return h;
	}
	
}
