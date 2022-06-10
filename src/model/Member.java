package model;

import java.sql.Date;
import java.util.HashMap;

import sql.DBService;

public class Member {
	
	private String username;
	private String password;
	private String name;
	private HashMap<String, Float> time_use_analysis;
	
	public Member() {
		
	}
	
	public Member(String username, String password, String name) {
		this.username = username;
		this.password = password;
		this.name = name;
	}
	
	public void setToDB() {
		DBService dbService = new DBService();
		dbService.createMember(this);
	}
	
	public void getAnalysis(Date date) {
		Analysis analysis = new Analysis(this.getUsername());
		time_use_analysis = analysis.analysisDailyTimeUse(date);
	}
	
	public void showAnalysis() {
		for(String key: time_use_analysis.keySet()) {
			System.out.println("Activity: " + key + " average takes " + time_use_analysis.get(key) * 100+ "percentage per day.");
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
