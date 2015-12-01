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
	private Point pos;
	public void setpos (Point p){pos=p;}
	public Shape create (String shapeType) throws InstantiationException,IllegalAccessException,ClassNotFoundException{
		try {
			final Shape s = (Shape)(Class.forName(shapeType)).newInstance();
			s.setPosition(pos);
			Map<String,Double> properties= new HashMap<String,Double>();
			properties = s.getProperties();
			JComponent[] dialog = new JComponent[properties.size()*2+2];
			int cnt=0;
			for (Map.Entry<String,Double> entry : properties.entrySet())
			{
				dialog[cnt++]=new JLabel(entry.getKey());
				dialog[cnt++]=new JTextField();
			}
			dialog[cnt]=new JButton ("Color");
			s.setColor(Color.black);
			((JButton)dialog[cnt]).addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){
					Color c=null;
					c = JColorChooser.showDialog(null,"Choose the shape Color",c);
					if (c!=null)s.setColor(c);
				}
			});
			dialog[cnt+1]=new JButton ("FillColor");
			((JButton)dialog[cnt+1]).addActionListener(new ActionListener(){
				public void actionPerformed (ActionEvent e){
					Color c = null;
					c = JColorChooser.showDialog(null,"Choose the backGround color",c);
					s.setFillColor(c);
				}
			});
			JOptionPane.showMessageDialog(null, dialog, "Enter the "+s.getClass().getSimpleName()+" properties:", JOptionPane.PLAIN_MESSAGE);
			cnt = -1;
			for (Map.Entry<String,Double>entry : properties.entrySet())
			{
				cnt+=2;
				entry.setValue(Double.parseDouble((((JTextField)(dialog[cnt])).getText())));
			}
			s.setProperties(properties);
			return s;
			
		}catch(InstantiationException|IllegalAccessException|ClassNotFoundException ex){
			throw new RuntimeException("can not create new shape");
		}
	}
}
