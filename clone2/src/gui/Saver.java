package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import eg.edu.alexu.csd.oop.calculator.Engine;
public class Saver implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent a) {
		Engine e = Engine.getInstance();
		try{
			e.save();
			JOptionPane.showMessageDialog(new JFrame(), "The Expressions Are Saved Successfully","Confirmation",
	            JOptionPane.INFORMATION_MESSAGE);
		}catch (Exception ex){
		    JOptionPane.showMessageDialog(new JFrame(), "Error : Nothing is saved","Error",
		            JOptionPane.ERROR_MESSAGE);
		}
	}
}
