package model;

import java.sql.Timestamp;

import sql.DBService;

public class Activity {

	private String member_id;
	private String activity_name;
	private Timestamp start_time;
	private Timestamp end_time;
	
	public Activity() {
		
	}
	
	public Activity(String member_id, String activity_name, Timestamp start_time, Timestamp end_time) {
		this.member_id = member_id;
		this.activity_name = activity_name;
		this.start_time = start_time;
		this.end_time = end_time;
	}
	
	public void setToDB() {
		DBService dbService = new DBService();
		dbService.createActivity(this);
	}
	
	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getActivity_name() {
		return activity_name;
	}

	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
	}

	public Timestamp getStart_time() {
		return start_time;
	}

	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}

	public Timestamp getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}
}
