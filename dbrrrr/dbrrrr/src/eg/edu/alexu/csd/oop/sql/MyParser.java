package eg.edu.alexu.csd.oop.sql;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.db.Parser;

public abstract class MyParser implements Parser{
private  String query = null ;
 int start =0 ,  end = 0 , returnedVal = 0;
	@Override
	public abstract Object parse(String query) throws SQLException ;
	@Override
	public boolean regexChecker(String theRegex, String query, int queryLength) {
		int matchNumber = 0, lengthOfMatcher = 0 ;
		this.query = query;
		 Pattern checkRegex = Pattern.compile(theRegex,Pattern.CASE_INSENSITIVE);
		 Matcher regexMatcher = checkRegex.matcher(this.query);
		  while ( regexMatcher.find()){
			  lengthOfMatcher = regexMatcher.group().length();
			  if ( lengthOfMatcher != 0){
				  matchNumber++;  
				  //System.out.println( regexMatcher.group().trim() );
				  this. start = regexMatcher.start();
				  this.end = regexMatcher.end();
				 // System.out.println( "Start Index: " + regexMatcher.start());
				  //System.out.println( "Start Index: " + regexMatcher.end());
				  //match = this.query.substring(regexMatcher.start(), regexMatcher.end());
			  }
		  }
		  if(matchNumber == 1)
		  {
			  //System.out.println(this.query + "mayar" + lengthOfMatcher + " m" + queryLength);
			  if(lengthOfMatcher == queryLength)
			  return true;
				  
		  }
		  //System.out.println(queryLength + "ddddd"+ lengthOfMatcher);
		return false;
	}
	@Override
	public int[] selectAfterOperator(String attribute, String query,
			String condition, String[][] tableStr, String operator)
			throws SQLException {
		int []selected = new int [100000];
		String[]attributeVal = attribute.split(",");
		 this.regexChecker("[>,<,=]", query, query.length());
		 int indexOperator = this.start;
		int indexCondition = -1;
		for(int i = 0 ; i < attributeVal.length ; i++)
		{
			String []attrName = attributeVal[i].split(";"); 
			if(attrName[0].equalsIgnoreCase(condition))
			{
				indexCondition = i;
			}
			
		}
		if(indexCondition != -1)
		{
			//String regex1 = "\\s*\\d*\\s*";
			//String regex3 = "\\s*[']\\s*.*\\s*[']\\s*";
			String operandPart = this.query.substring(indexOperator+1, query.length());
			String operandVal = null;
			String dataType = attributeVal[indexCondition].substring(attributeVal[indexCondition].indexOf(";")+1, attributeVal[indexCondition].length());
			/*if(this.regexChecker(regex1, operandPart, operandPart.length()))
			{
				operandVal = operandPart.replaceAll("[\\s*]", "");
			}
			else if(this.regexChecker(regex3, operandPart, operandPart.length()))
			{
				int comaStart = query.indexOf("'");
				int comaEnd = query.lastIndexOf("'");
				operandVal = operandPart.substring(comaStart+1, comaEnd);
			}*/
			if(dataType.equalsIgnoreCase("int"))
			{ 
				
				/*if(this.regexChecker(regex1, operandPart, operandPart.length()) )
				{
					operandVal = operandPart.replaceAll("[\\s*]", "");
					
				}*/
				operandVal = operandPart.replaceAll("[\\s*]", "");
			
			}
			else if(dataType.equalsIgnoreCase("varchar"))
			{
				
				/*if(this.regexChecker(regex3, operandPart, operandPart.length()) )
				{
					int comaStart = this.query.indexOf("'");
					int comaEnd = this.query.lastIndexOf("'");
					operandVal = operandPart.substring(comaStart+1, comaEnd);
				}*/
				int comaStart = query.indexOf("'");
				int comaEnd = query.lastIndexOf("'");
				operandVal = operandPart.substring(comaStart+1, comaEnd);
					
			}
			
				
			if(operandVal == null)
			{
				throw  new SQLException(query + "in my parser" +dataType +" "+operandPart  + condition + "rewan" + attribute);
			}
								
		  else
		 {
				
			for(int m = 0 ; m < tableStr.length;m++)
			{
				if(operator.equals("="))
				{
					if(tableStr[m][indexCondition].equals(operandVal))
					 {	selected[m] = 1;
					    this.returnedVal++;
					 }
				}
				else if(operator.equals("<"))
				{
					int val1 = Integer.parseInt(tableStr[m][indexCondition]);
					int val2 = Integer.parseInt(operandVal);
					if(val1  < val2)
						{
						selected[m] = 1;
						this.returnedVal++;
						}
					
				}
				else if(operator.equals(">"))
				{
					int val1 = Integer.parseInt(tableStr[m][indexCondition]);
					int val2 = Integer.parseInt(operandVal);
					if(val1 > val2)
						{
						selected[m] = 1;
						this.returnedVal++;
						}    
				}
				
			}
		
        	}
		}
		else
			throw  new SQLException(query + "in parser selected after operator");
		
		return selected;
	}


	@Override
	public int[] selectBeforeOperator(String attribute, String query,
			String begin, String last, String[][] tableStr) throws SQLException {
		System.out.println(query);
		String []selectedRow = null;
		int [] selectedRowIndex = new int [100000];
		int index = 0;
		this.regexChecker(begin  , this.query,this.query.length());
		int startStr = this.end;
		this.regexChecker(last , this.query,this.query.length());
		int endStr = this.start;
		String part = query.substring(startStr, endStr);
		if(part.contains("*"))
		{
			 for(int i = 0 ; i < tableStr[0].length ; i++)
			    {
			    	selectedRowIndex[i] = i;
			    	index++;	
			    }
			    return Arrays.copyOfRange(selectedRowIndex, 0, index);
		}
		part = query.substring(startStr, endStr).replaceAll("[\\s*]", "");
		if(part.contains(","))
		{
			selectedRow = part.split(",");
		}
		else
		{
			selectedRow = new String[1];
			selectedRow[0] = part;
		}
		
		
		String[]attributeVal = attribute.split(",");
		for(int j = 0 ; j < selectedRow.length ; j++)
		{
			for(int i = 0 ; i < attributeVal.length ; i++)
			{
				String []attrName = attributeVal[i].split(";");
				if(attrName[0].equalsIgnoreCase(selectedRow[j]))
				{
					selectedRowIndex[index++] = i;
					//System.out.println(selectedRowIndex[index-1] + "DDDDDDDDD");
				}
				
			}
		}
		if(index != selectedRow.length)
			throw  new SQLException(query + " " + part.contains("*") + " "+part + "  fofa  "+ " " +attribute);

		return Arrays.copyOfRange(selectedRowIndex, 0, index);
	}
}