package model;

import java.sql.Date;
import java.util.HashMap;

import sql.DBService;

/*
 * This class provide the member related code, can get or update data from db.
 */
public class Member {

	private String username;
	private String password;
	private String name;
	private HashMap<String, Float> time_use_analysis;

	/*
	 * The constructors of member when giving information.
	 */
	public Member() {

	}

	public Member(String username, String password, String name) {
		this.username = username;
		this.password = password;
		this.name = name;
	}

	public Member(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Member(String username) {
		this.username = username;
	}

	/*
	 * Checking whether the user exist in db.
	 */
	public boolean isRegister() {
		DBService dbService = new DBService();
		return dbService.checkMember(username);
	}

	/*
	 * Checking user login in db.
	 */
	public boolean checklogin() {
		DBService dbService = new DBService();
		return dbService.loginDB(username, password);
		// TODO Auto-generated method stub
	}

	/*
	 * Send the information to db.
	 */
	public void setToDB() {
		DBService dbService = new DBService();
		dbService.createMember(this);
	}

	/*
	 * Run the analysis.
	 */
	public void getAnalysis(Date date) {
		Analysis analysis = new Analysis(this.getUsername());
		time_use_analysis = analysis.analysisDailyTimeUse(date);
	}

	/*
	 * Showing the analysis to the screen.
	 */
	public void showAnalysis() {
		for (String key : time_use_analysis.keySet()) {
			System.out.println(
					"Activity: " + key + " average takes " + time_use_analysis.get(key) * 100 + "percentage per day.");
		}
	}

	/*
	 * Getting the analysis data.
	 */
	public HashMap<String, Float> getAnalysisData() {
		return time_use_analysis;
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
