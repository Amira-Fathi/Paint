package gui;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.UIManager;

public class Main {
	private JFrame frame;
	public static JTextField screen;
	public static boolean entered=false;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public Main() {
		initialize();
	}
	private void initialize() {
		frame = new JFrame("Calculator");
		frame.setResizable(false);
		frame.setForeground(Color.RED);
		frame.setBounds(100, 100, 245, 423);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// Equal & Enter 
		JButton enter = new JButton("ENT");
		enter.setBounds(158, 102, 71, 23);
		frame.getContentPane().add(enter);
		JButton equal = new JButton("=");
		equal.setFont(new Font("Tahoma", Font.PLAIN, 15));
		// End equal & Enter
		screen = new JTextField();
		screen.setBackground(UIManager.getColor("Button.light"));
		screen.setFont(new Font("Tahoma", Font.BOLD, 11));
		screen.setBounds(10, 11, 219, 26);
		screen.setColumns(10);
		frame.getContentPane().add(screen);
		// InterAction With Real Keyboard
		screen.addKeyListener(new InputGetter());
		screen.addKeyListener(new OutputGetter());
		screen.addKeyListener(new KeyBoardButton());
		// New Designed Keyboard
		// keyboard
		{
			JPanel panel = new JPanel();
			panel.setBounds(10, 170, 219, 203);
			panel.setLayout(new GridLayout(4,4,2,5));
			JButton[] b = new JButton[15];
			String s="";
			for (int i=0;i<15;i++){
				if (i<10)s=Integer.toString(i);
				else {
					switch(i){
					case 10:s="+";break;
					case 11:s="-";break;
					case 12:s="*";break;
					case 13:s="/";break;
					case 14:s=".";break;
					}
				}
				b[i]=new JButton(s);
				b[i].setFont(new Font("Tahoma",Font.PLAIN, 15));
				b[i].addActionListener(new KeyBoardButton());
			}
			panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			panel.add(b[10]);panel.add(b[7]);panel.add(b[8]);panel.add(b[9]);
			panel.add(b[11]);panel.add(b[4]);panel.add(b[5]);panel.add(b[6]);
			panel.add(b[12]);panel.add(b[1]);panel.add(b[2]);panel.add(b[3]);
			panel.add(b[13]);panel.add(b[0]);panel.add(b[14]);panel.add(equal);
			frame.getContentPane().add(panel);
		}
		// history
		JLabel prevN = new JLabel("previous");
		prevN.setBounds(65, 77, 62, 14);
		frame.getContentPane().add(prevN);
		JLabel nextN = new JLabel("next");
		nextN.setHorizontalAlignment(SwingConstants.CENTER);
		nextN.setBounds(183, 77, 46, 14);
		frame.getContentPane().add(nextN);
		JLabel history = new JLabel("History");
		history.setFont(new Font("Tahoma", Font.BOLD, 12));
		history.setBounds(10, 48, 53, 20);
		frame.getContentPane().add(history);
		JButton prev = new JButton("<");
		prev.setBounds(65, 48, 41, 23);
		frame.getContentPane().add(prev);
		JButton cur = new JButton("current");
		cur.setFont(new Font("Tahoma", Font.PLAIN, 9));
		cur.setBounds(116, 48, 62, 23);
		frame.getContentPane().add(cur);
		JButton next = new JButton(">");
		next.setBounds(188, 48, 41, 23);
		frame.getContentPane().add(next);
		// End History
		
		// Save & Load
		JButton save = new JButton("svae");
		save.setBounds(10, 136, 102, 23);
		frame.getContentPane().add(save);
		JButton load = new JButton("load");
		load.setBounds(127, 136, 102, 23);
		frame.getContentPane().add(load);
		// ENd Save & LOad
		
		// Delete & Clear
		JButton delete = new JButton("DEL");
		delete.setBounds(10, 102, 71, 23);
		frame.getContentPane().add(delete);
		JButton clear = new JButton("Clear");
		clear.setFont(new Font("Tahoma", Font.BOLD, 10));
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText("");
			}
		});
		clear.setBounds(89, 102, 62, 23);
		frame.getContentPane().add(clear);
		// Add Commands
		delete.addActionListener(new Deleter());
		enter.addActionListener(new InputGetter());
		equal.addActionListener(new OutputGetter());
		save.addActionListener(new Saver());
		load.addActionListener(new Loader());
		next.addActionListener(new Next());
		cur.addActionListener(new Current());
		prev.addActionListener(new Previous());
	}
}