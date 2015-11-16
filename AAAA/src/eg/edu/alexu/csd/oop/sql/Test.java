package eg.edu.alexu.csd.oop.sql;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;

public class Test {

	public static void main(String[] args) throws SQLException {
		System.out.println("hello");
		Database e = new AAAEngine ();
		e.createDatabase("sample", true);
		e.executeStructureQuery("CREATE TABLE table_name11(column_name1 varchar, column_name2 int, column_name3 varchar)");	}

}
