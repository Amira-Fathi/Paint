package eg.edu.alexu.csd.oop.sql;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;

public class Test {

	public static void main(String[] args) throws SQLException {
		Database e = new AAAEngine ();
		/*
 CD: CREATE DATABASE sample
CT: CREATE TABLE table_name8(column_name1 varchar, column_name2 int, column_name3 varchar)
I: INSERT INTO table_name8(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)
I: INSERT INTO table_name8(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)
I: INSERT INTO table_name8(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value2', 'value4', 5)
U: UPDATE table_name8 SET column_name1='11111111', COLUMN_NAME2=22222222, column_name3='333333333' WHERE coLUmn_NAME3='VALUE3'*/
	/*	e.createDatabase("sample", true);
		e.executeStructureQuery("CREATE TABLE table_name8(column_name1 varchar, column_name2 int, column_name3 varchar)");
		e.executeUpdateQuery("INSERT INTO table_name8(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
		e.executeUpdateQuery("INSERT INTO table_name8(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)");
		e.executeUpdateQuery("INSERT INTO table_name8(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value2', 'value4', 5)");
		System.out.println(e.executeUpdateQuery("UPDATE table_name8 SET column_name1='11111111', COLUMN_NAME2=22222222,"
				+ " column_name3='333333333' WHERE coLUmn_NAME3='VALUE3'"));
		*/
		/*CD: CREATE DATABASE sample
		CT: CREATE TABLE table_name9(column_name1 varchar, column_name2 int, column_name3 varchar)
		U: UPDATE table_name9 SET column_name1='value1', column_name2=15, column_name3='value2'*/
		e.createDatabase("sample", true);
		e.executeStructureQuery("CREATE TABLE table_name9(column_name1 varchar, column_name2 int, column_name3 varchar)");
		System.out.println(e.executeUpdateQuery("UPDATE table_name9 SET column_name1='value1', column_name2=15, column_name3='value2'"));
		
	}
}
