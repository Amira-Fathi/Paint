package eg.edu.alexu.csd.oop.sql;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.Parser;

public class AAAEngine implements Database{
	private String curdb=null;	
	private void deleteFile(File element) {
	    if (element.isDirectory()){
	        for (File sub : element.listFiles()) {
	            deleteFile(sub);
	        }
	    }
	    element.delete();
	}
	boolean validpath (String path){
		File f = new File(path);
		try {
			f.createNewFile();
			f.delete();
		}catch(IOException e){
			return false;// not valid
		}
		return true;
	}
	
	public AAAEngine(){
		curdb=null;
		log("",true);	
		if (new File("testdb").exists()&&(new File("testdb").isDirectory())){
			deleteFile(new File("testdb"));
			log("Exist at first & i delete it\n",false);
		}
	}
	private static final String FILE_NAME = "/debug/Amira361995.log";
	private static void log(String str, boolean delete) { 
		try { 
			if (delete) 
				new File(FILE_NAME).delete(); 
			java.nio.file.Files.write(java.nio.file.Paths.get(FILE_NAME), str.getBytes(), 
					new File(FILE_NAME).exists() ? StandardOpenOption.APPEND : StandardOpenOption.CREATE); 
		}catch (Throwable e1) { 
			e1.printStackTrace(); 
		} 
	}
	@Override
	// current data base must be set in the createDatabase
	public String createDatabase(String databaseName,boolean dropIfExists){
		if (!validpath(databaseName.toLowerCase())) return null;
		try{
			if (dropIfExists)executeStructureQuery("DROP DATABASE "+databaseName);
			executeStructureQuery("CREATE DATABASE "+databaseName);
			curdb=databaseName.toLowerCase();
			log ("current : "+curdb+"\n",false);
		}catch(SQLException ex){}
		return curdb;
	}
	@Override	
	public boolean executeStructureQuery(String query) throws SQLException{
		log ("SQ : "+query+"\n",false);
		if (query==null)
				throw new SQLException("Null Query "+query);
		StructureQueryParser p = new StructureQueryParser(curdb);
		boolean success = (boolean)p.parse(query);
		curdb=p.getDb();
		return success;
	}
	@Override
	public Object[][] executeQuery(String query)throws SQLException{
		if (query==null)
			throw new SQLException("Null Query");
		if (curdb==null) throw new SQLException("**executeQuery** null database");
		return (Object[][]) (new SelectParser(curdb)).parse(query);
	}
	@Override
	public int executeUpdateQuery(String query) throws SQLException {
		if (query==null)
			throw new SQLException("Null Query");
		if (curdb==null) throw new SQLException("**executeUpdateQuery** null database");
		try{
			String s = query.split("\\s+")[0].toLowerCase();
			s = (s.charAt(0)+"").toUpperCase()+s.substring(1,s.length());
			String classname = "eg.edu.alexu.csd.oop.sql."+s+"Parser";
				return (int)((Parser)(Class.forName(classname)).
						getConstructor(String.class).newInstance(curdb)).parse(query);
		}
		catch(ClassNotFoundException|InstantiationException|IllegalAccessException|StringIndexOutOfBoundsException
				|IllegalArgumentException|InvocationTargetException|NoSuchMethodException
				|SecurityException ex){
			throw new SQLException();
		}
	}
}
