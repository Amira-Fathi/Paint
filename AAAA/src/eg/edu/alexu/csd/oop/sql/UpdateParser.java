package eg.edu.alexu.csd.oop.sql;

import java.io.File;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;

import eg.edu.alexu.csd.oop.xml.XmlReader;
import eg.edu.alexu.csd.oop.xml.XmlWriter;

public class UpdateParser extends MyParser{
	private String curDb;
	private String table_name;
	private String[]cols,values,columns,vals;
	private Condition c;
	private String[] attr;
	private String[][]entries;
	public UpdateParser (String db){
		curDb=db;
	}
	/*
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
	private void check()throws SQLException {
		columns=new String[attr.length];
		vals=new String[attr.length];
		for (int i=0;i<cols.length;i++){
			boolean stringerror=true;
			char c1=values[i].charAt(0);
			char c2=values[i].charAt(values[i].length()-1);
			if (c1=='"'&&c2=='"' || c1=='\''&&c2=='\'' && values[i].length()>=3){
				stringerror=false;
				values[i]=values[i].substring(1,values[i].length()-1);
			}
			boolean found = false;
			for (int j=0;j<attr.length&&!found;j++){
				String[]p=attr[j].split("\\;");
				if (cols[i].equalsIgnoreCase(p[0])){
					if (p[1].equalsIgnoreCase("int")){
						try {
							Integer.parseInt(values[i]);
						}
						catch(NumberFormatException ex){
							throw new SQLException("Error: Not Valid INT Field(update)");
						}
					}else if(stringerror){
						throw new SQLException("Error: Not Valid VarChar Field(check2)");
					}
					found=true;
					columns[j]=cols[i];
					vals[j]=values[i];
				}
			}
			if (!found){
				throw new SQLException("Error: No Such Column(update) "+cols[i]);
			}
			
		}
	}
	@Override
	public Object parse(String query) throws SQLException {
		//log("U: "+query+"\n",false);
		String column="",value="",operator="";
		String reg1="(\\s*[Uu][Pp][Dd][Aa][Tt][Ee]\\s+)" // update 1
				+ "(.*\\S)" // table name 2 // check table name can has spaces
				+ "(\\s+[Ss][Ee][Tt]\\s+)" // set3
				+ "(.+[Ww])" // columns4
				+ "([Hh][Ee][Rr][Ee]\\s+)" //where5
				+ "(.+[=<>].+)"; // condition6
		String reg2="(\\s*[Uu][Pp][Dd][Aa][Tt][Ee]\\s+)" // update 1
				+ "(.*\\S)" // table name 2 // check table name can has spaces
				+ "(\\s+[Ss][Ee][Tt]\\s+)" // set
				+ "(.+)"; // columns
		if (regexChecker(reg1,query,query.length())){
			table_name=query.replaceAll(reg1,"$2");
			try {
				String s = query.replaceAll(reg1,"$4").replaceAll("[Ww]","").replaceAll("(\\s*)$","").
						replaceAll("\\s*\\,\\s*","\\,");
				String[]col = s.split("\\,");
				cols=new String[col.length];
				values=new String[col.length];
				for (int i=0;i<col.length;i++){
					col[i]=col[i].replaceAll("\\s*=\\s*","=");
					String[]p=col[i].split("=");
					cols[i]=p[0];
					values[i]=p[1];
				}
			}catch(ArrayIndexOutOfBoundsException ex){
				throw new SQLException("SQL Unvalid : update line91 " + query);
			}
			String condition = query.replaceAll(reg1,"$6");
			column = condition.replaceAll("(.+)([=<>])(.+)","$1").replaceAll("^(\\s*)|(\\s*)$","");
			value = condition.replaceAll("(.+)([=<>])(.+)","$3").replaceAll("^(\\s*)|(\\s*)$","");;
			operator = condition.replaceAll("(.+)([=<>])(.+)","$2");			
			c = new Condition(operator.charAt(0));
		}
		else if (regexChecker(reg2,query,query.length())){
			table_name=query.replaceAll(reg2,"$2");
			try {
				String[] col = query.replaceAll(reg2,"$4").replaceAll("(\\s*)$","").
						replaceAll("\\s*,\\s*",",").split("\\,");
				cols=new String[col.length];
				values=new String[col.length];
				for (int i=0;i<col.length;i++){
					col[i]=col[i].replaceAll("\\s*=\\s*","=");
					String[]p=col[i].split("=");
					cols[i]=p[0];
					values[i]=p[1];
				}
			}catch(ArrayIndexOutOfBoundsException ex){
				throw new SQLException("SQL Unvalid : update!!!! " + query);
			}
			operator="n";
			c = new Condition('n');
		}
		else {
			throw new SQLException("SQL Unvalid : update119 " + query);
		}
		// test file existence
		XmlReader xmlr=null;
		try{
			xmlr = new XmlReader(curDb+File.separator+table_name+".xml");
		}catch(RuntimeException ex){
			throw new SQLException("Error :Not Found Such Table(UpdateTable) "+table_name);
		}
		attr=xmlr.getAtrr().split("\\,");
		check();
		int index=-1;
		if (column.length()>0&&value.length()>0){	
			boolean found=false;
			char c1=value.charAt(0);
			char c2=value.charAt(value.length()-1);
			boolean stringError=true;	
			if (c1=='"'&&c2=='"' || c1=='\''&&c2=='\'' && value.length()>=3){
				stringError=false;
				value=value.substring(1,value.length()-1);
			}
			for (int i=0;i<attr.length&&!found;i++){
				String[]p=attr[i].split("\\;");
				if (p[0].equalsIgnoreCase(column)){
					found=true;
					if (p[1].toLowerCase().equals("int")){
						try{
							Integer.parseInt(value);
						}catch(NumberFormatException ex){
							throw new SQLException("Error: Not Valid INT Field(update)");
						}
					}
					else if (stringError ||operator.equals(">")||operator.equals("<")){
						throw new SQLException("Error: Not Valid Varchar Condition Field(update)");
					}
					index=i;
				}
			}if (!found)throw new SQLException("Error: No Such Column "+column);
		}
		int change=0;
		boolean update;
		entries = xmlr.getEntries();
		for (int i=0;i<entries.length;i++){
			update=false;
			if(index==-1)update=true;
			else if (c.compare(entries[i][index],value)){
				update=true; 
			}
			for (int j=0;j<attr.length&&update;j++){
				if (columns[j]!=null){
					entries[i][j]=vals[j];
				}
			}
			if (update)change++;
		}
        new XmlWriter(new File(curDb+File.separator+table_name+".xml"),entries,xmlr.getAtrr(),table_name);
        return change;
	}
}
