package eg.edu.alexu.csd.oop.sql;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;

public class Test {

	public static void main(String[] args) throws SQLException {
		System.out.println("hello");
		Database e = new AAAEngine ();
		e.createDatabase("sample", true);
		e.executeUpdateQuery("INSERT INTO Students VALUES ('15','Lamia Farid',20)");
	}

}
