package eg.edu.alexu.csd.oop.sql;

import java.io.File;
import java.sql.SQLException;

import eg.edu.alexu.csd.oop.xml.XmlReader;
public class SelectParser extends MyParser{
	private String query , dataBaseName , attribute;
	private String[][] selectedItem, tableArray = null ,updatedTable  = null ;
	private int [] selectedColIndex ,selected ;
	private  XmlReader read;
	/*public static void main(String[] args) {
		new SelectParser("rewan");
	}*/

	public SelectParser(String dataBase) {
		this.dataBaseName = dataBase;
		/* try {
			parse("SELECT * FROM table_name1 WHERE coluMN_NAME2 < 6");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	@Override
	public Object parse(String query) throws SQLException {
		this.query = query;
		String check1 = "\\s*SELECT\\s+[*]\\s+FROM\\s+\\w+\\s*";
		String check2 = "\\s*SELECT\\s+\\w+(\\s*\\,\\s*\\w+)*?\\s+FROM\\s+\\w+\\s*";
		String check3 = "\\s*SELECT\\s+((\\w+(\\s*\\,\\s*\\w+)*?)|\\*)\\s+FROM\\s+\\w+\\s+WHERE\\s+\\w+\\s*[<,>,=]\\s*.*\\s*";
		
		
		if(this.regexChecker(check1 , this.query,this.query.length())) 
				
		{
			this.regexChecker("from", this.query, this.query.length());
			String tableName = this.query.substring(this.end+1,this.query.length()).replaceAll("[\\s*]", "");
		   System.out.println(tableName);
		   String fileName = this.dataBaseName+File.separatorChar+tableName+".xml";
		   //File file = new File(fileName);
		    try {
				this.read = new XmlReader(fileName);
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		    this.updatedTable = this.read.getEntries(); 
		  //new XmlWriter( file, this.tableArray ,this.attribute,tableName);
		}
		 /////////////////////////////////////////////////////////////////
		
		///////////////////////////////////////////////////////////////////////////
		else if(this.regexChecker(check2 , this.query,this.query.length()))
		{ 
			this.regexChecker("from", this.query, this.query.length());
			String tableName = this.query.substring(this.end+1,this.query.length()).replaceAll("[\\s*]", "");
			intialize(tableName);
			  //System.out.println(this.selectedColIndex.length + "dkdkdkdkkdkdkdkdkdkdk" +this.tableArray.length );
			
			 selectedItem = new String[this.tableArray.length][this.selectedColIndex.length];
			for(int i = 0 ; i < this.tableArray.length ; i++)
			{
				for(int j = 0 ; j < this.selectedColIndex.length ; j++)
				{
					selectedItem[i][j] = this.tableArray[i][selectedColIndex[j]];
					//System.out.print(selectedItem[i][j] + "  ");
					
				}
				//System.out.println();
			}
			this.updatedTable = selectedItem ;
		   	
		}
		
		else if(this.regexChecker(check3 , this.query,this.query.length()))  
				 
		{
			//System.out.println( part +" dododofofofo");
			
			
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
			intialize(tableName); 
			this.selected = this.selectAfterOperator(this.attribute, this.query, condition, this.tableArray, operator);
			
		       if(this.returnedVal == 0)
				 this.updatedTable = new String[0][0];
			else
				this.updatedTable = new String[this.returnedVal][this.selectedColIndex.length];
			int index = 0 ;
			 if(this.tableArray == null)
			       throw new NullPointerException(this.query + " in this 3333333" );
			for(int i = 0 ; i < this.tableArray.length && (this.returnedVal != 0); i++)
			{
				if(this.selected[i] == 1){
					for(int j = 0 ; j < this.selectedColIndex.length ; j++)
					{
						updatedTable[index][j] = this.tableArray[i][selectedColIndex[j]];
						//System.out.print(updatedTable[index][j] + " ");
					}
					//System.out.println();
					index++;
				}
			}
			this.end = this.start = this.returnedVal = 0 ;
		}
		
		else{
			throw  new SQLException(this.query + "inside");
		}
		return this.updatedTable;
	}
	
	
	private void intialize(String tableName) throws SQLException
	{
		String fileName = this.dataBaseName+File.separatorChar+tableName+".xml";
		
	    try {
			this.read = new XmlReader(fileName);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	   this.tableArray = this.read.getEntries();
	  this.attribute = this.read.getAtrr();
	    this.selectedColIndex = this.selectBeforeOperator(this.attribute, this.query, "SELECT", "from", this.tableArray);
	}
		
}
