package eg.edu.alexu.csd.oop.sql;

import java.io.File;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;




import eg.edu.alexu.csd.oop.xml.XmlReader;
import eg.edu.alexu.csd.oop.xml.XmlWriter;

public class DeleteParser extends MyParser{
	private String query,dataBaseName, fileName ,attribute;
	private XmlReader read;
	private int no = 0 ;
	private int[] deleteRow;
	/*public static void main(String[] args) {
		String [][] r = { {"1","rewan" , "bitash","www","egypt"} ,{ "2","kfkf", "bitash","www","egypt"}};
		
		String fileName = "rewan"+File.separatorChar+"products"+".xml";
		File file = new File(fileName);
		String attribute = "PersonID;int,LastName;varchar,FirstName;varchar,Address;varchar,City;varchar";
		 //new XmlWriter( file, r ,attribute,"products");
		 //"delete  from   products where FirstName = 'l' "
		new  DeleteParser("sample");
		
	
	}*/
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
	public DeleteParser(String dataName) {
		this.dataBaseName = dataName;
		/*try {
			parse(" DELETE From table_name11 WHERE coLUmn_NAME3='VAluE3'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	@Override
	public Object parse(String query) throws SQLException {
		this.query = query;
		String check1 = "\\s*DELETE\\s+FROM\\s+\\w+\\s*";
		String check2 = "\\s*DELETE\\s+[*]\\s+FROM\\s+\\w+\\s*";
		String check3 = "\\s*DELETE\\s+FROM\\s+\\w+\\s+WHERE\\s+\\w+\\s*[<,>,=]\\s*.+\\s*";
		 this.regexChecker("[<,>,=]", this.query, this.query.length());
		// int noIndex = this.start;
	    /////////////////////////////////////////////////////////////////////////
		if(this.regexChecker(check1, this.query, this.query.length()) || 
				this.regexChecker(check2, this.query, this.query.length()))
		{
			//System.out.println("1ddddd");
			this.regexChecker("from", this.query, this.query.length());
			int st = this.end+1;
			String tableName = this.query.substring(st,this.query.length()).replaceAll("[\\s*]", "");
			this.fileName = dataBaseName+File.separatorChar+tableName+".xml";
			try {
				 this.read =new XmlReader(this.fileName);
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			File file = new File(this.fileName);
			String[][] tablestr = new String[0][0];
			String[][] get = this.read.getEntries();
			this.no = get.length;
			this.attribute =  this.read.getAtrr();
			 new XmlWriter( file, tablestr ,this.attribute,tableName);
			 this.end = this.start = this.returnedVal = 0;
			
		}
		//////////////////////////////////////////////////////////////////////////
		else if(this.regexChecker(check3, this.query, this.query.length()))
		{
			//System.out.println("2dddddd");
			this.regexChecker("from", this.query, this.query.length());
			int st = this.end+1;
			this.regexChecker("where", this.query, this.query.length());
			int whereEnd = this.end+1;
			int en = this.start;
			this.regexChecker("[<,>,=]", this.query, this.query.length());
			int indexOperator = this.start;
			String operator = String.valueOf(this.query.charAt(indexOperator));
			String condition = this.query.substring(whereEnd,indexOperator).replaceAll("[\\s*]", "");
			String tableName = this.query.substring(st,en).replaceAll("[\\s*]", "");

			
			
			/*String []content = this.query.substring(0, noIndex).split(" +");
			String tableName = content[2];
			String condition = content[4];
			String operator = String.valueOf(this.query.charAt(noIndex));*/
			this.fileName = dataBaseName+File.separatorChar+tableName+".xml";
			try {
				this.read =new XmlReader(this.fileName);
				
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.fileName = dataBaseName+File.separatorChar+tableName+".xml";
			File file = new File(this.fileName);
			String[][] tableStr = this.read.getEntries();
			log("",true);
			for (int i=0;i<tableStr.length;i++){
				for (int j=0;j<tableStr[0].length;j++){
					log(tableStr[i][j]+" ",false);
				}
				log("\n",false);
			}
			this.attribute = this.read.getAtrr();
			
		    this.deleteRow = this.selectAfterOperator(this.attribute, this.query, condition,tableStr , operator);
			 String [][] updatedTable = null;
			       if(this.returnedVal == 0)
			       {
			    	   updatedTable = tableStr;
			    	   
			       }
					 
				else
				  updatedTable = new String[tableStr.length-this.returnedVal][tableStr[0].length];
				int index = 0 ;
				no = this.returnedVal;
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
				this.end= this.start = this.returnedVal = 0;
			 }
		
		else
			throw  new SQLException(this.query + "inside");
		
		return no;
	}

}
