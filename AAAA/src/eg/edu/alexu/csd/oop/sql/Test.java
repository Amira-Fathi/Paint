package eg.edu.alexu.csd.oop.sql;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;

public class Test {

	public static void main(String[] args) throws SQLException {
		Database e = new AAAEngine ();
		/*
CD: CREATE DATABASE sample
CT: CREATE TABLE table_name7(column_name1 varchar, column_name2 int, column_name3 varchar)
I: INSERT INTO table_name7(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)
I: INSERT INTO table_name7(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)
I: INSERT INTO table_name7(column_name1, COLUMN_NAME3, column_NAME2) VALUES ('value2', 'value4', 5)
U: UPDATE table_name7 SET column_name1='1111111111', COLUMN_NAME2=2222222, column_name3='333333333'*/
		e.createDatabase("sample", false);

		int y = e.executeUpdateQuery("UPDATE table_namE1 SET column_name1='11111111', COLUMN_NAME2=22222222, column_name3='333333333' WHERE coLUmn_NAME3='VALUE3'");
		System.out.println("change "+y);
	}
}
