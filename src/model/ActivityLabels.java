package model;

import java.util.ArrayList;

import sql.DBService;

public class ActivityLabels {
	String member_user_name;
	String activity_label;
	ArrayList<String> activity_labels;
	
	public ActivityLabels() {
		
	}
	
	public ActivityLabels(String member_username) {
		this.member_user_name = member_username;
	}
	
	public void setToDB() {
		DBService dbService = new DBService();
		dbService.createActivityLabels(this);
	}
	
	public void addSingleLabel(String activity_label) {
		this.setActivity_label(activity_label);
		this.setToDB();
	}
	
	public ArrayList<String> getLabels() {
		DBService dbService = new DBService();
		this.activity_labels = dbService.getActivityLabels(this.member_user_name);
		return this.activity_labels;
	}

	public String getMember_user_name() {
		return member_user_name;
	}

	public void setMember_user_name(String member_user_name) {
		this.member_user_name = member_user_name;
	}

	public String getActivity_label() {
		return activity_label;
	}

	public void setActivity_label(String activity_label) {
		this.activity_label = activity_label;
	}
	
	
}