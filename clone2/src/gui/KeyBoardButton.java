package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;

public class KeyBoardButton implements ActionListener,KeyListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton)e.getSource();
		String s1 = b.getText();
		String s2 = Main.screen.getText();
		Main.screen.setText(s2+s1);
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
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if ((c<'0'||c>'9')&& (c!='.')){
			e.consume();
		}
		if (c=='+'||c=='-'||c=='/'||c=='*'){
			String s1 = Main.screen.getText();
			Main.screen.setText(s1+c);
		}
	}

}
