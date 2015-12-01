package eg.edu.alexu.csd.oop.ui;
import eg.edu.alexu.csd.oop.draw.ModelEngine;
public class Main{
	public static void main(String[]args){
		ModelEngine e = (ModelEngine)ModelEngine.getInstance();
		PaintGui paintGui = new PaintGui();
		paintGui.setVisible(true);
		new Controller(e,paintGui);
	}
}
