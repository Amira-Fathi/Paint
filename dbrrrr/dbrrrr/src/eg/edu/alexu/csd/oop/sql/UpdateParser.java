package eg.edu.alexu.csd.oop.sql;

import java.sql.SQLException;

public class UpdateParser extends MyParser{
	private String curDb , query;
	
	public UpdateParser (String db){
		this.curDb=db;
	}
	@Override
	public Object parse(String query) throws SQLException {
		this.query = query;
		String check1 = "\\s*UPDATE\\s+\\w+\\s+SET\\s+\\w+\\s*[=]\\s*\\."
				+ "+(\\s*\\,\\s*\\w+\\s*[=]\\s*\\.+\\s*)*?\\s*";
		
		return null;
	}

}
