package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import eg.edu.alexu.csd.oop.calculator.Engine;

public class Previous implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent a) {
		Engine e = Engine.getInstance();
		String s = e.prev();
		if (s==null){
		    JOptionPane.showMessageDialog(new JFrame(), "No Previous Expression", "Error",
		            JOptionPane.ERROR_MESSAGE);
		}
		else {
			Main.screen.setText(s);
		}
		
	}
}
