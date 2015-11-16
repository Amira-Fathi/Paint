package eg.edu.alexu.csd.oop.sql;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;

public class Test {

	public static void main(String[] args) throws SQLException {
		Database db = new AAAEngine ();
		db.createDatabase("TestDB_1", true);
		System.out.println(db.executeStructureQuery("CREATE DATABASE TestDB_2"));
		System.out.println(db.executeStructureQuery("CREATE DATABASE TestDB_3"));
		System.out.println(db.executeStructureQuery("CREATE DATABASE TestDB_4"));
	}
}
