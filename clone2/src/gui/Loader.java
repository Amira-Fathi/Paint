package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import eg.edu.alexu.csd.oop.calculator.Engine;

public class Loader implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent a){
		Engine e = Engine.getInstance();
		try{
			e.load();
		    JOptionPane.showMessageDialog(new JFrame(), "The Expressions Are Loaded Successfully","Confirmation",
		            JOptionPane.INFORMATION_MESSAGE);
		    Main.screen.setText(e.current());
		}
		catch (RuntimeException ex){
		    JOptionPane.showMessageDialog(new JFrame(), "Error : No Data Are Loaded","Error",
		            JOptionPane.ERROR_MESSAGE);
		}	
	}
}
