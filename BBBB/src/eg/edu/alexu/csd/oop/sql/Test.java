package eg.edu.alexu.csd.oop.sql;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;

public class Test {
	public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException {
		Database db1 = new AAAEngine();
		db1.createDatabase("sample",true);
		System.out.println("1"+db1.executeStructureQuery("CREATE TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)"));
		System.out.println("2"+db1.executeStructureQuery("CREATE DATABASE TestDB"));
		System.out.println("3"+db1.executeStructureQuery("CREATE TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)"));
		Database db2 = new AAAEngine();
		System.out.println("4"+db2.executeStructureQuery("CREATE DATABASE TestDB"));
		System.out.println("5"+db2.executeStructureQuery("DROP DATABASE TestDB"));
		System.out.println(db2.executeStructureQuery("CREATE TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)"));
		System.out.println("6"+db2.executeStructureQuery("CREATE DATABASE TestDB"));
		System.out.println("7"+db2.executeStructureQuery("CREATE TABLE table_name2(column_name1 varchar, column_name2 int, column_name3 varchar)"));
	}
}
/*
true
true
false
//
true//
true//
true**
true//
true
*/
