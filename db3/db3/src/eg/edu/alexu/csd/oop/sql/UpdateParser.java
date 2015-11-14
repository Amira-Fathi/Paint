package eg.edu.alexu.csd.oop.sql;

import java.sql.SQLException;

public class UpdateParser extends MyParser{
	private String curdb;
	public UpdateParser (String db){
		curdb=db;
	}
	@Override
	public Object parse(String query) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
