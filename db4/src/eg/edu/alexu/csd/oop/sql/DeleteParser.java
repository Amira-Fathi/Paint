package eg.edu.alexu.csd.oop.sql;

import java.io.File;
import java.sql.SQLException;


import eg.edu.alexu.csd.oop.xml.XmlReader;
import eg.edu.alexu.csd.oop.xml.XmlWriter;

public class DeleteParser extends MyParser{
	private String query,dataBaseName, fileName ,attribute;
	private XmlReader read;
	private int[] deleteRow;
	public DeleteParser(String dataName) {
		this.dataBaseName = dataName;
	}
	@Override
	public Object parse(String query) throws SQLException {
		this.query = query;
		String check1 = "DELETE\\s*FROM\\s*\\w*\\s*";
		String check2 = "DELETE\\s*[*]\\s*FROM\\s*\\w*\\s*";
		String check3 = "DELETE\\s*FROM\\s*\\w*\\s*WHERE\\s*\\w*\\s*[<,>,=]\\s*.*\\s*";
		 this.regexChecker("[<,>,=]", this.query, this.query.length());
		 int noIndex = this.start;
		if(this.regexChecker(check1, this.query, this.query.length()) || 
				this.regexChecker(check2, this.query, this.query.length()))
		{
			String regex1 = "\\*";
			String tableName = null;
			String []content = this.query.split(" +");
			if(this.regexChecker(regex1, this.query, this.query.length()))
				tableName = content[3];
			else
				tableName = content[2];
		
			this.fileName = dataBaseName+File.separatorChar+tableName+".xml";
			try {
				 this.read =new XmlReader(this.fileName);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
			File file = new File(this.fileName);
			String[][] tablestr = new String[0][0];
			
			this.attribute =  this.read.getAtrr();
			 new XmlWriter( file, tablestr ,this.attribute,tableName);
		}
		else if(this.regexChecker(check3, this.query, this.query.length()))
		{
			String []content = this.query.substring(0, noIndex).split(" +");
			String tableName = content[2];
			String condition = content[4];
			String operator = String.valueOf(this.query.charAt(noIndex));
			this.fileName = dataBaseName+File.separatorChar+tableName+".xml";
			try {
				this.read =new XmlReader(this.fileName);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
			this.fileName = dataBaseName+File.separatorChar+tableName+".xml";
			File file = new File(this.fileName);
			String[][] tableStr = this.read.getEntries();
			this.attribute = this.read.getAtrr();
			
		    this.deleteRow = this.selectAfterOperator(this.attribute, this.query, condition,tableStr , operator);
			 String [][] updatedTable = null;
			       if(this.returnedVal == 0)
					 updatedTable = tableStr;
				else
				  updatedTable = new String[tableStr.length-this.returnedVal][tableStr[0].length];
				int index = 0 ;
				for(int i = 0 ; i < tableStr.length ; i++)
				{
					if(deleteRow[i] != 1){
						for(int j = 0 ; j <  tableStr[0].length ; j++)
						{
							updatedTable[index][j] = tableStr[i][j];
						}
						index++;
					}
				}
				new XmlWriter( file, updatedTable ,this.attribute, tableName);			
			 }		
		else
			throw  new SQLException("ERROR DELETE {"+query+"} hiiiiiii rewaaaaaaan");
		
		return this.returnedVal;
	}

}
