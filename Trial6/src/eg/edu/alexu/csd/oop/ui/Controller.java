package eg.edu.alexu.csd.oop.ui;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import eg.edu.alexu.csd.oop.draw.ModelEngine;
import eg.edu.alexu.csd.oop.draw.Shape;


public class Controller{
	private boolean moveFlag=false;
	private ModelEngine engine;
	private PaintGui paintGui;
	private Point pos=null;
	private Shape selected=null;
	private java.util.List<Class<? extends eg.edu.alexu.csd.oop.draw.Shape>> list; 

	public Controller (ModelEngine e,PaintGui u){
		paintGui=u;
		engine=e;
		list = new LinkedList<Class<? extends eg.edu.alexu.csd.oop.draw.Shape>>();
		getShapes();
		
		paintGui.click(new Selector());
		 
		paintGui.saveView(new Saver());
		paintGui.loadView(new Loader());
		 
		paintGui.undoView (new Previous());
		paintGui.redoView(new Next());
		 
		paintGui.moveShape(new MoveMarker());
		paintGui.resizeShape (new SizeUpdater());
		paintGui.changeColor (new ColorUpdater());
		paintGui.changeFillColor (new FillColorUpdater());
		paintGui.deleteShape(new Remover());

		paintGui.selectShape(new ShapeAdder());
	}
	private void getShapes (){
		list = engine.getSupportedShapes();
		java.util.Iterator<Class<? extends Shape>> it = (java.util.Iterator<Class<? extends Shape>>) list.iterator();
		while (it.hasNext()){
			Class<? extends Shape> s = it.next();
			paintGui.addItem(s.getSimpleName());
		}
	}
	// check null pointer exception
	private class Saver implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File("C:\\"));
				fc.setDialogTitle("Save As");
				JButton save = new JButton ();
				FileNameExtensionFilter filterXML = new FileNameExtensionFilter(".xml", "xml");
				FileNameExtensionFilter filterJSON = new FileNameExtensionFilter(".json", "json");
				fc.setFileFilter(filterXML);
				fc.setFileFilter(filterJSON);
				fc.removeChoosableFileFilter(fc.getAcceptAllFileFilter());
				if(fc.showOpenDialog(save)==JFileChooser.APPROVE_OPTION){
					String path = fc.getSelectedFile().getAbsolutePath();
					if (!(path.endsWith(".xml")||path.endsWith(".json"))){
						path+=fc.getFileFilter().getDescription();
					}
					engine.save(path);
				}
			    JOptionPane.showMessageDialog(new JFrame(),"The Drawings Are Saved Successfully", "Confirmation",
			            JOptionPane.INFORMATION_MESSAGE);
			}catch(RuntimeException|ClassNotFoundException|InstantiationException|IllegalAccessException | UnsupportedLookAndFeelException ex){
			    JOptionPane.showMessageDialog(new JFrame(), "Nothing Is Saved", "Error",
			            JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	private class Loader implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File("C:\\"));
				fc.setDialogTitle("Save As");
				JButton open = new JButton ();
				FileNameExtensionFilter filterXML = new FileNameExtensionFilter(".xml", "xml");
				FileNameExtensionFilter filterJSON = new FileNameExtensionFilter(".json", "json");
				fc.setFileFilter(filterXML);
				fc.setFileFilter(filterJSON);
				fc.removeChoosableFileFilter(fc.getAcceptAllFileFilter());
				if(fc.showOpenDialog(open)==JFileChooser.APPROVE_OPTION){
					String path = fc.getSelectedFile().getAbsolutePath();
					engine.load(path);
				}
				paintGui.getcanvas().update(paintGui.getcanvas().getGraphics());
				engine.refresh(paintGui.getcanvas().getGraphics());
			    JOptionPane.showMessageDialog(new JFrame(), "The Drawings Are loaded Successfully", "Confirmation",
			            JOptionPane.INFORMATION_MESSAGE);
			}catch(Exception ex){
			    JOptionPane.showMessageDialog(new JFrame(), "Nothing Is loaded", "Error",
			            JOptionPane.ERROR_MESSAGE);
			}
		}
			
		}
	private class Previous implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				engine.undo();
				paintGui.getcanvas().update(paintGui.getcanvas().getGraphics());
				engine.refresh(paintGui.getcanvas().getGraphics());
			}catch(Exception ex){
			    JOptionPane.showMessageDialog(new JFrame(), "Nothing To Be Undone", "Error",
			            JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	private class Next implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				engine.redo();
				paintGui.getcanvas().update(paintGui.getcanvas().getGraphics());
				engine.refresh(paintGui.getcanvas().getGraphics());
			}catch(Exception ex){
			    JOptionPane.showMessageDialog(new JFrame(), "Nothing To Be Redone", "Error",
			            JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}
	
	private class SizeUpdater implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				if (selected!=null){
					Shape New = (Shape) selected.clone();
					Map<String,Double>properties = New.getProperties();
					JComponent[] dialog = new JComponent[properties.size()+2];
					int cnt=0;
					for (Map.Entry<String,Double> entry : properties.entrySet())
					{
						dialog[cnt++]=new JLabel(entry.getKey());
						dialog[cnt++]=new JTextField();
					}
					JOptionPane.showMessageDialog(null, dialog,null, JOptionPane.PLAIN_MESSAGE);
					cnt = -1;
					for (Map.Entry<String,Double>entry : properties.entrySet())
					{
						cnt+=2;
						entry.setValue(Double.parseDouble((((JTextField)(dialog[cnt])).getText())));
					}
					New.setProperties(properties);
					engine.updateShape(selected,New);
					paintGui.getcanvas().update(paintGui.getcanvas().getGraphics());
					engine.refresh(paintGui.getcanvas().getGraphics());
					selected=null;
					pos=null;
				}
			}catch(Exception ex){}
		}
	}
	private class ColorUpdater implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				if (selected!=null){
					Shape New = (Shape)selected.clone();
					JButton b=new JButton ("Color");
					b.addActionListener(new ActionListener(){
						public void actionPerformed (ActionEvent e){
							Color c = Color.black;
							c = JColorChooser.showDialog(null,"Choose the shape Color",c);
							New.setColor(c);
						}
					});
					JOptionPane.showMessageDialog(null,b,null, JOptionPane.PLAIN_MESSAGE);
					engine.updateShape(selected,New);
					paintGui.getcanvas().update(paintGui.getcanvas().getGraphics());
					engine.refresh(paintGui.getcanvas().getGraphics());
					selected=null;
					pos=null;
				}
			}catch(Exception ex){}
			
		}
	}
	private class FillColorUpdater implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				if (selected!=null){
					Shape New = (Shape)selected.clone();
					JButton b=new JButton ("Color");
					b.addActionListener(new ActionListener(){
						public void actionPerformed (ActionEvent e){
							Color c = Color.black;
							c = JColorChooser.showDialog(null,"Choose the shape Color",c);
							New.setFillColor(c);
						}
					});
					JOptionPane.showMessageDialog(null,b,null, JOptionPane.PLAIN_MESSAGE);
					engine.updateShape(selected,New);
					paintGui.getcanvas().update(paintGui.getcanvas().getGraphics());
					engine.refresh(paintGui.getcanvas().getGraphics());
					selected=null;
					pos=null;
				}
			}catch(Exception ex){}
			
		}
		}
	
	private class Remover implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if (selected!=null){
			try{
				engine.removeShape(selected);
				paintGui.getcanvas().update(paintGui.getcanvas().getGraphics());
				engine.refresh(paintGui.getcanvas().getGraphics());
				pos=null;
				selected=null;
			}catch(Exception ex){}
			}
		}
	}
	private class ShapeAdder implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ShapeFactory sf = new ShapeFactory();
			JComboBox<String> c = (JComboBox<String>)e.getSource();
			 if (pos!=null){
				 try {
					 sf.setpos(pos);
					 Shape s = (Shape)sf.create(list.get(c.getSelectedIndex()).getName()); 
					 engine.addShape(s);
					 s.draw(paintGui.getcanvas().getGraphics());
					 pos=null;
				 }catch(Exception ex){
					 
				 }
			 }
			
		}
	}
	private class Selector implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (moveFlag){
				try{
					Shape New = (Shape) selected.clone();
					New.setPosition(e.getPoint());
					engine.updateShape(selected,New);
					paintGui.getcanvas().update(paintGui.getcanvas().getGraphics());
					engine.refresh(paintGui.getcanvas().getGraphics());
					moveFlag=false;
					pos=null;
					selected=null;
				}catch(Exception ex){}
			}
			else {
				pos=e.getPoint();
				double mini=10000000000.0,dist;
				for (Shape s:engine.getShapes()){
					dist = Math.sqrt(Math.pow(pos.getX()-s.getPosition().getX(),2)+Math.pow(pos.getY()-s.getPosition().getY(),2));
					if (dist<mini){
						selected = s;
						mini=dist;
					}
				}
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent arg0) {}
	}
	private class MoveMarker implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			moveFlag=true;
		}
	}
	
}
