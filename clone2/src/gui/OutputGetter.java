package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import eg.edu.alexu.csd.oop.calculator.Engine;

public class OutputGetter implements ActionListener,KeyListener{
	private Engine e;
	public OutputGetter (){e = Engine.getInstance();}
	@Override
	public void actionPerformed(ActionEvent a) {
		try {	
				String s = e.getResult();
				Main.screen.setText(s);
		}catch(Exception ex){
		    JOptionPane.showMessageDialog(new JFrame(), "Not Valid Expression", "Error",
		            JOptionPane.ERROR_MESSAGE);
		}
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent k) {
		if (k.getKeyChar()!='=')return;
		try{		
			String s = e.getResult();
			Main.screen.setText(s);
		}catch(Exception ex){
		    JOptionPane.showMessageDialog(new JFrame(), "Not Valid Expression", "Error",
		            JOptionPane.ERROR_MESSAGE);
		}
		
	}


}
