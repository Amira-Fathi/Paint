package eg.edu.alexu.csd.oop.sql;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;

public class Test {

	public static void main(String[] args) throws SQLException {
		Database db = new AAAEngine ();
		System.out.println(db.executeStructureQuery("CREATE DATABASE TestDB"));
		System.out.println(db.executeStructureQuery("CREATE DATABASE TestDB"));
	}
}
