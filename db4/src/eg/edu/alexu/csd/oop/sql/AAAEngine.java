package eg.edu.alexu.csd.oop.sql;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.Parser;

public class AAAEngine implements Database{
	private String curdb=null;
	private boolean createdb=false;
	@Override
	// open a data base
	public String createDatabase(String databaseName, boolean dropIfExists) {
		createdb=true;
		try {
			if (dropIfExists)
				executeStructureQuery("DROP DATABASE "+databaseName);
			if (executeStructureQuery("CREATE DATABASE "+databaseName)){
				createdb=false;
				curdb=databaseName;
				File f = new File (databaseName);
				f.mkdir();
				return f.getAbsolutePath();
			}
		}
		catch(SQLException ex){
			createdb=false;
		}
		return null;
	}
	@Override
	
	
	public boolean executeStructureQuery(String query) throws SQLException {
		if (query==null)
				throw new SQLException("Null Query "+query);
		if (curdb==null&&!createdb) throw new SQLException("**executeStructureQuery** null database");
		
		return (boolean)(new StructureQueryParser(curdb)).parse(query);
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
