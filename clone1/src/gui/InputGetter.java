package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import eg.edu.alexu.csd.oop.calculator.Engine;

public class InputGetter implements ActionListener,KeyListener{
	Engine e ;
	public InputGetter(){e=Engine.getInstance();}
	@Override
	public void actionPerformed(ActionEvent a) {
		
		try {
			// wrong input will still in the textfiled & won't be deleted 
			String s = Main.screen.getText();
			e.input(s);
			Main.entered=true;
		}catch(Exception ex){
		    JOptionPane.showMessageDialog(new JFrame(), "Not Valid Expression", "Error",
		            JOptionPane.ERROR_MESSAGE);
		}	
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent k) {
		if (k.getKeyChar()=='\n'){
			try {
				// wrong input will still in the textfiled & won't be deleted 
				String s = Main.screen.getText();
				e.input(s);
				Main.entered=true;
			}catch(Exception ex){
				JOptionPane.showMessageDialog(new JFrame(), "Not Valid Expression", "Error",
		            JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}