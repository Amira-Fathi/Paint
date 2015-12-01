package eg.edu.alexu.csd.oop.ui;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PaintGui extends JFrame{
	private static final long serialVersionUID = 1L;
	private JButton save,load,undo,redo;
	private JButton resize,move,delete,color,fillColor;
	private Canvas canvas;
	private JComboBox<String>comboBox;
	public PaintGui(){
		this.getContentPane().setBackground(SystemColor.scrollbar);
		this.setForeground(Color.YELLOW);
		this.setBackground(Color.RED);
		this.setResizable(false);
		this.setBounds(100, 100, 687, 597);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		canvas = new Canvas();
		canvas.setBackground(Color.WHITE);
		canvas.setBounds(10, 66, 661, 456);
		this.getContentPane().add(canvas);
		
		resize = new JButton("Resize");
		resize.setForeground(new Color(0, 0, 255));
		resize.setBackground(SystemColor.inactiveCaptionBorder);
		resize.setBounds(170, 25, 89, 23);
		this.getContentPane().add(resize);
		
		move = new JButton("Move");
		move.setForeground(new Color(0, 0, 255));
		move.setBackground(SystemColor.inactiveCaptionBorder);
		move.setBounds(272, 25, 89, 23);
		this.getContentPane().add(move);
		
		delete = new JButton("Delete");
		delete.setForeground(new Color(0, 0, 255));
		delete.setBackground(SystemColor.inactiveCaptionBorder);
		delete.setBounds(371, 25, 89, 23);
		this.getContentPane().add(delete);
		
		color = new JButton("Color");
		color.setForeground(new Color(0, 0, 255));
		color.setBackground(SystemColor.inactiveCaptionBorder);
		color.setBounds(470, 25, 89, 23);
		this.getContentPane().add(color);
		
		fillColor = new JButton("fillColor");
		fillColor.setForeground(new Color(0, 0, 255));
		fillColor.setBackground(SystemColor.inactiveCaptionBorder);
		fillColor.setBounds(569, 25, 89, 23);
		this.getContentPane().add(fillColor);
		
		save = new JButton("Save");
		save.setForeground(new Color(0, 0, 255));
		save.setBackground(SystemColor.inactiveCaptionBorder);
		save.setBounds(68, 534, 113, 23);
		this.getContentPane().add(save);
		
		load = new JButton("Load");
		load.setForeground(new Color(0, 0, 255));
		load.setBackground(SystemColor.inactiveCaptionBorder);
		load.setBounds(210, 534, 113, 23);
		this.getContentPane().add(load);
		
		undo = new JButton("Undo");
		undo.setForeground(new Color(0, 0, 255));
		undo.setBackground(SystemColor.inactiveCaptionBorder);
		undo.setBounds(354, 534, 113, 23);
		this.getContentPane().add(undo);
		
		redo = new JButton("Redo");
		redo.setForeground(new Color(0, 0, 255));
		redo.setBackground(SystemColor.inactiveCaptionBorder);
		redo.setBounds(496, 534, 113, 23);
		this.getContentPane().add(redo);
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(59, 26, 101, 20);
		this.getContentPane().add(comboBox);
		JLabel lblShapes = new JLabel("Shapes");
		lblShapes.setBounds(10, 29, 46, 14);
		this.getContentPane().add(lblShapes);
	}
	public void addItem (String s){
		comboBox.addItem(s);
	}
	public int getSelected (){
		return comboBox.getSelectedIndex();
	}
	Canvas getcanvas(){return canvas;}
	public void click (MouseListener m){canvas.addMouseListener(m);}
	
	public void saveView(ActionListener a){save.addActionListener(a);}
	public void loadView(ActionListener a){load.addActionListener(a);}
	public void undoView(ActionListener a){undo.addActionListener(a);}
	public void redoView(ActionListener a){redo.addActionListener(a);}
	
	public void selectShape (ActionListener a){comboBox.addActionListener(a);}
	
	public void moveShape(ActionListener a){move.addActionListener(a);}
	public void deleteShape(ActionListener a){delete.addActionListener(a);}
	public void resizeShape(ActionListener a){resize.addActionListener(a);}
	public void changeColor(ActionListener a){color.addActionListener(a);}
	public void changeFillColor(ActionListener a){fillColor.addActionListener(a);}
}
