package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * This class try to create the tables in db.
 */
public class DBCreation {
	
	private static Connection conn = null;
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    private static String DB_URL = "jdbc:mysql://localhost:3306";
    private static final String USER = "root";
    private static final String PASS = "123456";
	
	/*
	 * DB Creation code, create the table in db.
	 */
	public static Connection createDB() {
		
		try {
			Class.forName(JDBC_DRIVER);
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();
			
			String create_database = "CREATE DATABASE manageurtime";
			stmt.executeUpdate(create_database);
			stmt.close();
			conn.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		DB_URL = "jdbc:mysql://localhost:3306/manageurtime?"
	    		+ "useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		
		try {	
			
			String create_table_member = "CREATE TABLE members " + 
	                   "(username varchar(255) primary key, " +
	                   " password varchar(255), " + 
	                   " name varchar(255)" +
	                   ")";
			
			String create_table_activity = "CREATE TABLE activities " 
					+ "(id varchar(255) primary key,"
					+ "member_id varchar(255),"
					+ "foreign key(member_id) references members(username),"
					+ "activity varchar(255),"
					+ "start_time datetime, " 
	                + "end_time datetime"
					+ ")";
			
			String create_table_activity_labels = "CREATE TABLE activitylabels " 
					+ "(id varchar(255) primary key,"
					+ "member_id varchar(255),"
					+ "foreign key(member_id) references members(username),"
					+ "activity varchar(255)"
					+ ")";

			
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(create_table_member);
			stmt.executeUpdate(create_table_activity);
			stmt.executeUpdate(create_table_activity_labels);
//			stmt.executeUpdate(create_table_products);
//			stmt.executeUpdate(create_table_order);
//			stmt.executeUpdate(create_table_type_restaurants);
//			stmt.executeUpdate(create_table_business_times);
//			stmt.executeUpdate(create_table_order_items);
			stmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
}
