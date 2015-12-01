package eg.edu.alexu.csd.oop.draw;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import eg.edu.alexu.csd.oop.history.History;
import eg.edu.alexu.csd.oop.parsers.Parser;
public class ModelEngine implements DrawingEngine{
	private ArrayList<Shape> shapes;
	private History h;
	private Parser parser;
	private static ModelEngine modelEngine = null;
	private ModelEngine(){
		h = new History(20);
		shapes = new ArrayList<Shape>();
	}
	public static DrawingEngine getInstance(){
		if(modelEngine==null)modelEngine=new ModelEngine();
		return modelEngine;
	}
	public static void destoryInstance(){
		modelEngine = null;
   }
	@Override
	public void refresh(Graphics canvas){
		Iterator<Shape> it = shapes.iterator();
		while (it.hasNext()){
			Shape next = it.next();
			next.draw(canvas);
		}
	}
	@Override
	public void addShape(Shape shape){
		if (shape==null) throw new NullPointerException("null pointer exception in add_shape !!!");
		shapes.add(shape);
		h.add(shapes);
	}
	@Override
	public void removeShape(Shape shape){
		if (!shapes.contains(shape)) throw new RuntimeException("array doesn't contain the shape in remove_shape");
		if (shape==null) throw new NullPointerException("the shape to be removed is null in remove_Shape");
		shapes.remove(shape);
		h.add(shapes);
	}
	@Override
	public void updateShape(Shape oldShape,Shape newShape){
		String s1= oldShape.getClass().getSimpleName();
		String s2= newShape.getClass().getSimpleName();
		if (!s1.equals(s2)) throw new RuntimeException("the two shapes haven't the same type in update_shape");
		if (oldShape==null||newShape==null) throw new NullPointerException("one of the two shapes is null in update_shape");
		int ind = shapes.indexOf(oldShape);
		shapes.remove(ind);
		shapes.add(ind,newShape);
		h.add(shapes);
	}
	@Override
	public Shape[] getShapes(){
		Shape[] d = new Shape[shapes.size()];
		d = shapes.toArray(d);
		return d;
	}
	@Override
	public List<Class<? extends Shape>> getSupportedShapes(){
		java.util.List<Class<? extends Shape>> list = new LinkedList<Class<? extends Shape>>();
		list.add(Circle.class);
		list.add(Ellipse.class);
		list.add(Line.class);
		list.add(Square.class);
		list.add(Rectangle.class);
		list.add(Triangle.class);
		
		String[] path = System.getProperty("java.class.path").split(""+File.pathSeparatorChar);
		try{
		for (int i=0;i<path.length;i++){
			if (!path[i].endsWith(".jar")) continue;
			JarInputStream jarFile = new JarInputStream(new FileInputStream(path[i]));
			JarEntry jarEntry;
			while ((jarEntry=jarFile.getNextJarEntry())!= null){
				if ((jarEntry.getName().endsWith(".class"))){
					String className = jarEntry.getName().replace('/','.').replaceAll(".class", "");
					Class<?> myclass=null;
					try{
						myclass = Class.forName(className);
					}catch(Throwable e){
						continue;
					}
					if (className.indexOf("$")!= -1) continue;
					int modifer = myclass.getModifiers();
					if (Modifier.isAbstract(modifer) ||
					Modifier.isInterface(modifer)) continue;
					Class<?>[] interfaces = myclass.getInterfaces();
					for (int k = 0; k < interfaces.length; k++) {
						if (interfaces[k].getName().equals("eg.edu.alexu.csd.oop.draw.Shape")){
							list.add((Class<? extends Shape>)myclass);
					        break;   	
					    }
					}
				}
			}
			jarFile.close();
		}
		}catch(Exception t){
			throw new RuntimeException ("Can not find plugins");
		}
		return list;
	}
	@Override
	public void undo(){
		try {
			h.undo();
		}catch (RuntimeException e){
			throw new RuntimeException();
		}
		if (h.size()==0){
			shapes.clear();
		}
		else {
			shapes = h.cur();
		}
	}
	@Override
	public void redo(){
		try {
			h.redo();
		}catch (RuntimeException e){
			throw new RuntimeException();
		}
		shapes = h.cur();
	}
	@Override
	public void save(String path){
		try {
			String[]parts = path.split("\\.");
			String exten = (parts[parts.length-1].charAt(0)+"").toUpperCase()+
			parts[parts.length-1].substring(1,parts[parts.length-1].length()).toLowerCase();
			parser = (Parser)(Class.forName("eg.edu.alexu.csd.oop.parsers."+exten+"Parser")).newInstance();
			parser.getPath(path);
			parser.write(shapes);
		} catch (ClassNotFoundException|InstantiationException|IllegalAccessException ex){
			ex.printStackTrace();
		}
	}
	@Override
	public void load(String path){
		try {
			String[]parts = path.split("\\.");
			String exten = (parts[parts.length-1].charAt(0)+"").toUpperCase()+
			parts[parts.length-1].substring(1,parts[parts.length-1].length()).toLowerCase();
			parser = (Parser)(Class.forName("eg.edu.alexu.csd.oop.parsers."+exten+"Parser")).newInstance();
			parser.getPath(path);
			shapes = parser.read();
			h.clear();
		} catch (ClassNotFoundException|InstantiationException|IllegalAccessException ex){
			ex.printStackTrace();
		}
	}
}
