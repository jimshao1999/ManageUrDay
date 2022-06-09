package sql;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.sql.SQLException;
//import model.Order;
import model.Member;
import model.Analysis;
import model.Activity;

public class TestDBService {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		DBService dbService = new DBService();
			
		Member member = new Member("username","password","member1");
		member.setToDB();
		
		for(int i = 0; i <10; i ++) {
			Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
			long l = now.getTime();
			l = l + i * 1000*60*60;
			long m = (i+10)*60*1000;
			Timestamp start_time = new Timestamp(l+m);
			Timestamp end_time = new Timestamp(l+2*m);
			
			String act_name = "activityname" + (i%5);
			System.out.println(act_name);
			Activity activity = new Activity("username", act_name, start_time, end_time);
			activity.setToDB();
		}
		
		member.getAnalysis();
		member.showAnalysis();

	}

}
