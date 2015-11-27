package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Deleter implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		String s = Main.screen.getText();
		if (s.length()>=1){
			s=s.substring(0,s.length()-1);
			Main.screen.setText(s);
		}
	}
}
