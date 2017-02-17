package eg.edu.alexu.csd.oop.ui;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import eg.edu.alexu.csd.oop.draw.Shape;

public class ShapeFactory{
	
	private Point position;
	
	public void setPosition (Point position){
		this.position=position;
	}
	
	public Shape create (String shapeType) throws InstantiationException,IllegalAccessException,ClassNotFoundException{
		try {
			final Shape shape = (Shape)(Class.forName(shapeType)).newInstance();
			shape.setPosition(position);
			Map<String,Double> properties= new HashMap<String,Double>();
			properties = shape.getProperties();
			JComponent[] dialogComponents = new JComponent[properties.size()*2+2];
			int cnt=0;
			for (Map.Entry<String,Double> entry : properties.entrySet())
			{
				dialogComponents[cnt++]=new JLabel(entry.getKey());
				dialogComponents[cnt++]=new JTextField();
			}
			dialogComponents[cnt]=new JButton ("Color");
			shape.setColor(Color.black);
			((JButton)dialogComponents[cnt]).addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){
					Color color=null;
					color = JColorChooser.showDialog(null,"Choose the shape Color",color);
					if (color!=null)shape.setColor(color);
				}
			});
			dialogComponents[cnt+1]=new JButton ("FillColor");
			((JButton)dialogComponents[cnt+1]).addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){
					Color color = null;
					color = JColorChooser.showDialog(null,"Choose the backGround color",color);
					shape.setFillColor(color);
				}
			});
			JOptionPane.showMessageDialog(null, dialogComponents, "Enter the "+shape.getClass().getSimpleName()+" properties:", JOptionPane.PLAIN_MESSAGE);
			cnt = -1;
			for (Map.Entry<String,Double>entry : properties.entrySet())
			{
				cnt+=2;
				entry.setValue(Double.parseDouble((((JTextField)(dialogComponents[cnt])).getText())));
			}
			shape.setProperties(properties);
			return shape;
			
		}catch(InstantiationException|IllegalAccessException|ClassNotFoundException ex){
			throw new RuntimeException("can not create new shape");
		}
	}
}
