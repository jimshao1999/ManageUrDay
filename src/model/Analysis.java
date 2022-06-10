package model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import sql.DBService;

public class Analysis {
	
	private ArrayList<Activity> activities;
	private String member_name; 
	
	public Analysis() {
		activities = new ArrayList<>();
	}
	
	public Analysis(String member_name) {
		activities = new ArrayList<>();
		this.member_name = member_name;
	}
	
	public void getActivitiesFromDB() {
	}
	
//	public HashMap<String, Integer> analysisTimeUse(Date date) {
//		DBService dbService = new DBService();
//		this.activities = dbService.getDailyActivity(this.member_name, date);
//		HashMap<String, Integer> sep_act_time = new HashMap<>();
//		HashMap<String, Integer> sep_act_quan = new HashMap<>();
//		HashMap<String, Integer> sep_act_analysis = new HashMap<>();
//		for(Activity act: activities) {
//			long diff = act.getEnd_time().getTime() - act.getStart_time().getTime();
////			Timestamp ts = new Timestamp(diff);
//			diff = diff/1000/60; // millisecondes to minutes
//			if(sep_act_time.containsKey(act.getActivity_name())) {
//				sep_act_time.put(act.getActivity_name(), (int) (sep_act_time.get(act.getActivity_name())+diff));
//				sep_act_quan.put(act.getActivity_name(), sep_act_quan.get(act.getActivity_name())+1);
//
//			}
//			else {
//				sep_act_time.put(act.getActivity_name(), (int) diff);
//				sep_act_quan.put(act.getActivity_name(), 1);
//			}	
//		}
//		for(String key: sep_act_time.keySet()) {
//			sep_act_analysis.put(key, sep_act_time.get(key) / sep_act_quan.get(key));
//		}
//		return sep_act_analysis;
//	}
	
	public HashMap<String, Float> analysisDailyTimeUse(Date date) {
		DBService dbService = new DBService();
		this.activities = dbService.getDailyActivity(this.member_name, date);
		HashMap<String, Integer> sep_act_time = new HashMap<>();
//		HashMap<String, Integer> sep_act_quan = new HashMap<>();
		int total_time = 0;
		HashMap<String, Float> sep_act_analysis = new HashMap<>();
		for(Activity act: activities) {
			long diff = act.getEnd_time().getTime() - act.getStart_time().getTime();
//			Timestamp ts = new Timestamp(diff);
			diff = diff/1000/60; // millisecondes to minutes
			total_time += diff;
			if(sep_act_time.containsKey(act.getActivity_name())) {
				sep_act_time.put(act.getActivity_name(), (int) (sep_act_time.get(act.getActivity_name())+diff));
			}
			else {
				sep_act_time.put(act.getActivity_name(), (int) diff);
			}	
		}
		for(String key: sep_act_time.keySet()) {
			sep_act_analysis.put(key, (float) sep_act_time.get(key) / total_time);
		}
		return sep_act_analysis;
	}
}
