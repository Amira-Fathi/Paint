package eg.edu.alexu.csd.oop.sql;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;

public class Test {

	public static void main(String[] args) throws SQLException {
		Database db = new AAAEngine ();
		boolean created = db.executeStructureQuery("CREATE DATABASE TestDB");
		System.out.println(created);
	}
}
