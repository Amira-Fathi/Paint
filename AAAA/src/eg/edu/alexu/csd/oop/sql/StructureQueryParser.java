package eg.edu.alexu.csd.oop.sql;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;

import eg.edu.alexu.csd.oop.xml.XmlWriter;

public class StructureQueryParser extends MyParser{
	// CREATE DATABASE database_name
	// DROP DATABASE database_name
	// DROP TABLE table_name
	// CREATE TABLE table_name()
	// not check on columns names yet
	private String curDb;
	public StructureQueryParser(String db){
		curDb=db;
	}
	public String getDb(){
		return curDb;
	}
	
	private void deleteFile(File element) {
	    if (element.isDirectory()){
	        for (File sub : element.listFiles()) {
	            deleteFile(sub);
	        }
	    }
	    element.delete();
	}
	//*
	private static final String FILE_NAME = "/debug/Amira361995.log";
	private static void log(String str, boolean delete){ 
		try { 
			if (delete) 
				new File(FILE_NAME).delete(); 
			java.nio.file.Files.write(java.nio.file.Paths.get(FILE_NAME), str.getBytes(), 
					new File(FILE_NAME).exists() ? StandardOpenOption.APPEND : StandardOpenOption.CREATE); 
		}catch (Throwable e1) { 
			e1.printStackTrace(); 
		} 
	}//*/

	@Override
	public Object parse(String query) throws SQLException {
		String reg1 = "(\\s*[Cc][Rr][Ee][Aa][Tt][Ee]\\s+"+
				"[Dd][Aa][Tt][Aa][Bb][Aa][Ss][Ee]\\s+)"+
				"(.*\\S)(\\s*)";
		String reg2 = "(\\s*[Dd][Rr][Oo][Pp]\\s+"+
				"[Dd][Aa][Tt][Aa][Bb][Aa][Ss][Ee]\\s+)"+
				"(.*\\S)(\\s*)";
		String reg3 = "(\\s*[Cc][Rr][Ee][Aa][Tt][Ee]\\s+"+
				"[Tt][Aa][Bb][Ll][Ee]\\s+)"+
				"(.*\\S)(\\s*)(\\(.+\\))(\\s*)";
		String reg4 = "(\\s*[Dd][Rr][Oo][Pp]\\s+"+
				"[Tt][Aa][Bb][Ll][Ee]\\s+)"+
				"(.*\\S)(\\s*)";
		
		if (regexChecker(reg1,query,query.length())){
			log("CD: "+query+"\n",false);
			return createDb(query.replaceAll(reg1,"$2"));
		}
		else if (regexChecker(reg2,query,query.length())){
			return dropDb(query.replaceAll(reg2,"$2"));
		}
		
		// create table
		else if (regexChecker(reg3,query,query.length())){
			log("CT: "+query+"\n",false);
			if (curDb==null)throw new RuntimeException("create table without database !!!!!!!!!!");
			String tableName = query.replaceAll(reg3,"$2");
			String col = query.replaceAll(reg3,"$4").replaceAll("^(\\()|(\\))$","").replaceAll("^(\\s*)|(\\s*)$","").
					replaceAll("\\s*,\\s*",",").replaceAll("\\s+[Ii][Nn][Tt]",";int").replaceAll("\\s+[Vv][Aa][Rr][Cc][Hh][Aa][Rr]",";varchar");
			return createTable(tableName,curDb+File.separator+tableName+".xml",col);
		}
		// create table
		
		else if (regexChecker(reg4,query,query.length())){
			if (curDb==null)throw new RuntimeException("drop table without database !!!!!!!!!!");
			return dropTable(curDb+File.separator+query.replaceAll(reg4,"$2")+".xml");
		}
		else { 
			throw new SQLException("SQL Syntax Error(Creation)");
		}
	}
	private boolean createDb (String db){
		
		File f = new File(db);
		// also if not valid name exist will return false
		// exist but not directory --> file not folder
		if(f.exists()&& f.isDirectory()){
			 return false;
		}
		else{
			if(f.mkdir()){
				return true;
			}
			return false;
		}
	}
	private boolean dropDb(String db){
		File f = new File(db);
		if (f.exists() && f.isDirectory()){
			deleteFile(f);
			if (curDb!=null){
				if(curDb.equals(db))curDb=null;
			}
			return true;
		}
		return false; // not found folder
	}
	private boolean dropTable(String table_name){
		File f = new File(table_name);
		if (f.exists()&&!f.isDirectory()){
			deleteFile(f);
			return true;
		}
		return false;
	}
	private boolean createTable(String table_name,String path,String attr){
		File f = new File(path);
		if (f.exists()&&!f.isDirectory()){
			return false;
		}
		else {
			try {
				f.createNewFile();
			} catch (IOException e){
				return false;
			}
			// if success in creation the file 
			if (f.exists()){
				//log(attr,true);
				new XmlWriter(f,new String[][]{},attr,table_name);
				return true;
			}
			return false;
		}
	}
}
