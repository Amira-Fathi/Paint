package eg.edu.alexu.csd.oop.sql;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;

public class Test {

	public static void main(String[] args) throws SQLException {
		Database e = new AAAEngine ();
		System.out.println(e.createDatabase("sam@p*le",true));
	/*	e.executeStructureQuery("CREATE TABLE Persons"
				+"(PersonID int,LastName varchar,FirstName varchar,Address varchar,City varchar)");*/
		
		
	}

}
