package eg.edu.alexu.csd.oop.db;

public interface Parser {
	public boolean regexChecker(String theRegex , String query , int queryLength);
	public Object parse(String query)throws java.sql.SQLException;
	public int[] selectAfterOperator(String attribute, String query ,String condition, String[][] tableStr , String operator)throws java.sql.SQLException;
	public int[] selectBeforeOperator(String attribute, String query ,String begine, String end, String[][] tableStr )throws java.sql.SQLException;
	
}