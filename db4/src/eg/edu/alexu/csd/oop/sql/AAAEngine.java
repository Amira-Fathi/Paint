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
	boolean validpath (String path){
		File f = new File(path);
		try {
			f.createNewFile();
			f.delete();
		} catch(IOException e){
			return false;// not valid
		}
		return true;
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
	// open a data base
	// current data base must be set in the createDatabase
	public String createDatabase(String databaseName,boolean dropIfExists) {
		if (!validpath(databaseName)) return null;
		try{
			if (dropIfExists)executeStructureQuery("DROP DATABASE "+databaseName);
			executeStructureQuery("CREATE DATABASE "+databaseName);
			curdb=databaseName;
			log(curdb+" {from createDatabase}",true);
		}catch(SQLException ex){}
		return curdb;
	}
	@Override	
	public boolean executeStructureQuery(String query) throws SQLException{
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
		return (String[][]) (new SelectParser(curdb)).parse(query);
	}
	@Override
	// insert 
	// delete 
	// update 
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
		catch(ClassNotFoundException ex){
			throw new SQLException("ClassNotFoundException");
		}catch(InstantiationException ex){
			throw new SQLException("InstantiationException");
		}
		catch(IllegalAccessException e){
			throw new SQLException("IllegalAccessException");
		}
		catch( StringIndexOutOfBoundsException e){
			throw new SQLException("StringIndexOutOfBoundsException");
		}
		catch(IllegalArgumentException e){
			throw new SQLException("IllegalArgumentException");
		}
		catch(InvocationTargetException e){
			throw new SQLException("InvocationTargetException");
		}
		catch(NoSuchMethodException e){
			throw new SQLException("NoSuchMethodException");
		}
		catch(SecurityException e){
			throw new SQLException("SecurityException");
		}
	}
}
