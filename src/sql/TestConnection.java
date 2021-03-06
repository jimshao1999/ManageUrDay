package sql;

import java.sql.*;

/*
 * This class can test the connection to db.
 */
public class TestConnection {

	public static void main(String[] args) throws SQLException {
		Connection conn = DBConnection.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("show databases;");
	    while(rs.next()) {
	    	System.out.print(rs.getString(1));
	    	System.out.println();
	    }
	}

}
