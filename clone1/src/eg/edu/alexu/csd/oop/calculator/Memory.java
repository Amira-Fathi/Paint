package eg.edu.alexu.csd.oop.calculator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Scanner;

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
		Iterator<String> it = h.iterator();
		output.write(Integer.toString(h.size()));
		output.newLine();
		while (it.hasNext()){
			output.write(it.next());
			output.newLine();
		}
		output.close();
	}
	public History load() throws FileNotFoundException{
		Scanner skan = new Scanner(data);
		History h = new History(5);
		int sz = skan.nextInt();
		skan.nextLine();
		for (int i=0;i<sz;i++){
			h.add(skan.nextLine());
		}
		return h;
	}
	
}