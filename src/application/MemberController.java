package application;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Activity;
import model.ActivityLabels;
import model.Analysis;
import model.Member;
import view.MemberView;

public class MemberController extends Controller implements Initializable {
	@FXML
	private Button logoutBtn;

	@FXML
	private Label welcomeLb;

	@FXML
	private Button labelsBtn;

	@FXML
	private Button activityBtn;

	@FXML
	private Button analysisBtn;

	@FXML
	private VBox displayVb;

	private String username;
	private MemberView status;
	private String tmp = "";
	
	public void logout(ActionEvent event) throws IOException {
		switchScene(ViewEnum.LOGIN, event);
	}

	@Override
	protected void setUsernameLb(String s) {
		username = s;
		String tmp = "Hello, Member " + s + "\nWelcome to Footopia!";
		welcomeLb.setText(tmp);
	}

	@Override
	protected void render() {

	}
		
	public void pressLabelsBtn() {
		// Setting labels
		ActivityLabels labels = new ActivityLabels(this.username);
		
		// Can show all labels, maybe sorted?
		labels.getLabels();
		
		// maybe can input some name
		
		String labelName = "Reading";
		
		// Press the add label, can add the label by input label name
		labels.addSingleLabel(labelName);
		
		// Can Update automatically
		// pressLabelsBtn();
		
		
	}

	
	public void pressActivityBtn() {
		// setting activity
		
		// TODO Need to send member_id, Activity, start time, end time
		
		// TODO Can show all activity, and can choice one
		
		ActivityLabels labels = new ActivityLabels();
		labels.getLabels();
		
		String choicedLabel = "Reading";
		
		// TODO [Discussion need] Choose the start time, maybe think how to set
		Timestamp start_time = new Timestamp(0);
		
		// TODO [Discussion need] Choose the end time
		Timestamp end_time = new Timestamp(1);
		
		// TODO check the timestamp and show error in fx
		if (!start_time.before(end_time)) {
			System.out.println("Time error");
			pressActivityBtn();
		}
		else {
			// TODO show it or send it
			Activity activity = new Activity(this.username, choicedLabel, start_time, end_time);
			// TODO [Optional] if check this is ok, then send it to db
			activity.setToDB();
			
			// TODO Show the status success
			
			// TODO [Optional] Maybe can show all activity there later?
		}
		
//		for(int i = 0; i <10; i ++) {
//			Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
//			long l = now.getTime();
//			l = l + i * 1000*60*60;
//			long m = (i+10)*60*1000;
//			Timestamp start_time = new Timestamp(l+m);
//			Timestamp end_time = new Timestamp(l+2*m);
//			
//			String act_name = "activityname" + (i%5);
//			System.out.println(act_name);
//			Activity activity = new Activity("username", act_name, start_time, end_time);
////			activity.setToDB();
//		}
//		Date date = new java.sql.Date(Calendar.getInstance().getTimeInMillis()); 
////		DateFormat df = DateFormat.getDateInstance();
////		Date date = df.parse("2022/06/10");
//		System.out.println("date today is " + date);
//		member.getAnalysis(date);
//		member.showAnalysis();
	}

	public void pressAnalysisBtn() {
		// use analysis function
		Member member = new Member(this.username);
		Date date = new java.sql.Date(Calendar.getInstance().getTimeInMillis()); 
		DateFormat df = DateFormat.getDateInstance();
		
		// TODO [WIP]
		// what
//		try {
//			java.util.Date date1 = df.parse("2022/06/10");
//			
//			Calendar calender = Calendar.getInstance();
//			calender.setTime(date1);
//			
//			Date dateNew = new java.sql.Date(Calendar.getInstance().getTimeInMillis()); 
//
//			java.sql.Date(Calendar.getInstance().setTime(date1));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		System.out.println("date today is " + date);
		member.getAnalysis(date);
		member.showAnalysis();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		status = MemberView.Default;
		displayVb.setSpacing(5);
		render();
	}

}
