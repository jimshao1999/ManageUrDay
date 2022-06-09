package sql;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.UUID;

import model.Member;
import model.Activity;
import model.Analysis;


public class DBService {	
	
	public void createMember(Member member) {
		
		try {
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO members values(?,?,?)"); 

			stmt.setString(1, member.getUsername());
			stmt.setString(2, member.getPassword());
			stmt.setString(3, member.getName());
			
			int res = stmt.executeUpdate();
			
			if(res == 1)
				System.out.print("Member Created");
			else
				System.out.print("Member Creation Failed");
			conn.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	Note: I deleted role
	public boolean loginDB(String username, String password) {
		
		try {
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM members WHERE username=? AND password=?"); 
			
			stmt.setString(1, username);
			stmt.setString(2, password);
			
			ResultSet res = stmt.executeQuery();
			
			if(res.next()) {
				System.out.print("Login Success");
				conn.close();
				return true;
			}
			else {
				System.out.print("Login Failed, Cannot find Account");
				conn.close();
				return false;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Member getMember(String username, String password) {
		
		try {
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM members WHERE username=?"); 
			
			stmt.setString(1, username);
			
			ResultSet res = stmt.executeQuery();
			
			if(res.next()) {
				System.out.print("Find!");
				Member member = new Member(res.getString("username"), res.getString("password"), res.getString("name"));
				conn.close();
				return member;
			}
			else {
				System.out.print("Not Found!");
				conn.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Member getMember(String username) {
		
		try {
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM members WHERE username=?"); 
			
			stmt.setString(1, username);
			
			ResultSet res = stmt.executeQuery();
			
			if(res.next()) {
				System.out.print("Find!");
				Member member = new Member(res.getString("username"), res.getString("password"), res.getString("name"));
				conn.close();
				return member;
			}
			else {
				System.out.print("NotFind!");
				conn.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void createActivity(Activity activity) {
		
		try {
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO activities values(?,?,?,?,?)"); 
			
			stmt.setString(1, UUID.randomUUID().toString());
			stmt.setString(2, activity.getMember_id());
			stmt.setString(3, activity.getActivity_name());
			stmt.setObject(4, activity.getStart_time());
			stmt.setObject(5, activity.getEnd_time());
			
			int res = stmt.executeUpdate();
			
			if(res == 1) {
			
				System.out.println("Activity Created");
				conn.close();
			}
			else {
				System.out.println("Activity Creation Failed");
				conn.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public ArrayList<Activity> getActivity(String member_username) {
		
		ArrayList<Activity> activities = new ArrayList<>();
		
		try {
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM activities WHERE member_id=?"); 
			
			stmt.setString(1, member_username);
			ResultSet res = stmt.executeQuery();
			
			while(res.next()) {
			
				System.out.println("Activity found");
				LocalDateTime c =  (LocalDateTime) res.getObject("start_time");
				LocalDateTime dt =  (LocalDateTime) res.getObject("end_time");
				Timestamp start_time = Timestamp.valueOf(c);
				Timestamp end_time = Timestamp.valueOf(dt);
				Activity activity = new Activity(res.getString("member_id"), res.getString("activity"), start_time, end_time);
				
				activities.add(activity);
//				might add cookie or something else??
			}
			conn.close();
			return activities;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	public ArrayList<String> getOrdersMember(String member_id) throws SQLException {
//		
//		ArrayList<String> orders = new ArrayList<>();
//		
//		try {
//			Connection conn = DBConnection.getConnection();
//			
//			PreparedStatement query_orders = conn.prepareStatement("SELECT * from orders WHERE member_name=?"); 
//			query_orders.setString(1, member_id);
//			ResultSet res = query_orders.executeQuery();
//			while(res.next()) {
//				orders.add(res.getString("id"));
//			}
//			conn.close();
//			return orders;
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		return orders;
//	}
}