package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Deleter implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		String s = Main.screen.getText();
		if (s.length()>=4&&s.charAt(s.length()-1)==' '){
			s=s.substring(0,s.length()-3);
		}
		else if (s.length()>=1){
			s=s.substring(0,s.length()-1);
		}
		if (s!=null)Main.screen.setText(s);
		
	}

}
