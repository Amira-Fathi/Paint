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
			if (!Main.entered){
			    JOptionPane.showMessageDialog(new JFrame(), "Click Enter At First To Get Result !", "Warning",
			            JOptionPane.ERROR_MESSAGE);
			    return;
			}
			else {		
				String s = e.getResult();
				Main.screen.setText(s);
				Main.entered=false;
			}
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
		try {
			if (!Main.entered){
			    JOptionPane.showMessageDialog(new JFrame(), "Click Enter At First To Get Result !", "Warning",
			            JOptionPane.ERROR_MESSAGE);
			    return;
			}
			else {		
				String s = e.getResult();
				Main.screen.setText(s);
				Main.entered=false;
			}
		}catch(Exception ex){
		    JOptionPane.showMessageDialog(new JFrame(), "Not Valid Expression", "Error",
		            JOptionPane.ERROR_MESSAGE);
		}
		
	}


}