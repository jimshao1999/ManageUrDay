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
import model.ActivityLabels;
import model.Analysis;

/*
 * This class provide the db service to model, very important.
 */
public class DBService {	
	
	/*
	 * Create member in db.
	 */
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
	
	/*
	 * Can check login status in db.
	 */
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
	
	/*
	 * Can get informations of member in db by giving the username and password.
	 */
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
	
	/*
	 * Can Get member information by giving the username.
	 */
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
	
	/*
	 * Can check the member by giving the username.
	 */
	public boolean checkMember(String username) {
		
		try {
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM members WHERE username=?"); 
			
			stmt.setString(1, username);
			
			ResultSet res = stmt.executeQuery();
			
			if(res.next()) {
				System.out.print("Find!");
				Member member = new Member(res.getString("username"), res.getString("password"), res.getString("name"));
				conn.close();
				return true;
			}
			else {
				System.out.print("NotFind!");
				conn.close();
				return false;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/*
	 * Can create a activity in db.
	 */
	public void createActivity(Activity activity) {
		
		try {
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO activities values(?,?,?,?,?)"); 
			
			stmt.setString(1, UUID.randomUUID().toString());
			stmt.setString(2, activity.getMember_id());
			stmt.setString(3, activity.getActivity_name());
			stmt.setObject(4, new Timestamp(activity.getStart_time().getTime() + 8 * 3600 * 1000));
//			stmt.setObject(4, activity.getStart_time());
			stmt.setObject(5, new Timestamp(activity.getEnd_time().getTime() + 8 * 3600 * 1000));
//			stmt.setObject(5, activity.getEnd_time());

			
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
	
	/*
	 * When giving date and user, can get daily activity from db.
	 */
	public ArrayList<Activity> getDailyActivity(String member_username, Date date) {
		
		ArrayList<Activity> activities = new ArrayList<>();
		
		try {
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM activities WHERE member_id=? and Date(start_time)=? order by start_time"); 
			
			stmt.setString(1, member_username);
			stmt.setDate(2, date);
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
	
	/*
	 * Can create activity labels when giving label.
	 */
	public void createActivityLabels(ActivityLabels label) {
		
		try {
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO activitylabels values(?,?,?)"); 
			
			stmt.setString(1, UUID.randomUUID().toString());
			stmt.setString(2, label.getMember_user_name());
			stmt.setString(3, label.getActivity_label());
			
			int res = stmt.executeUpdate();
			
			if(res == 1) {
			
				System.out.println("Activity Label Created");
				conn.close();
			}
			else {
				System.out.println("Activity Label Creation Failed");
				conn.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	/*
	 * Can get activity labels of member.
	 */
	public ArrayList<String> getActivityLabels(String member_username) {
		
		ArrayList<String> activity_labels = new ArrayList<>();
		
		try {
			Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM activitylabels WHERE member_id=?"); 
			
			stmt.setString(1, member_username);
			ResultSet res = stmt.executeQuery();
			
			while(res.next()) {
			
				System.out.println("Activity Label found");
				String activity_label = res.getString("activity");
				activity_labels.add(activity_label);
//				might add cookie or something else??
			}
			conn.close();
			return activity_labels;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

}