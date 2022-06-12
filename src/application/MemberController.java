package application;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
	private Button labelBtn;
	
	@FXML
	private Button addBtn;

	@FXML
	private Button vBtn;

	@FXML
	private Button activityBtn;

	@FXML
	private Button analysisBtn;

	@FXML
	private VBox displayVb;

	@FXML
	private HBox labelHb;
	
	@FXML
	private TextField labelTf;

	@FXML
	private TextField fromTf;

	@FXML
	private TextField toTf;

	@FXML
	private ComboBox<String> activityCombo;

	@FXML
	private HBox activityHb;
	
	private String username;
	private MemberView status;
	private String tmp = "";
	
	public void logout(ActionEvent event) throws IOException {
		switchScene(ViewEnum.LOGIN, event);
	}

	@Override
	protected void setUsernameLb(String s) {
		username = s;
		String tmp = "Hello, Member " + s + "\nWelcome to Time Diary!";
		welcomeLb.setText(tmp);
	}

	@Override
	protected void render() {
		switch(status) {
		case Label:
			labelHb.setDisable(false);
			activityHb.setDisable(true);
			break;
		case Activity:
			labelHb.setDisable(true);
			activityHb.setDisable(false);
			break;
		case Analysis:
			labelHb.setDisable(true);
			activityHb.setDisable(true);
			break;
		case Default:
			labelHb.setDisable(false);
			activityHb.setDisable(true);
			pressLabelBtn();
			break;
		}
		
	}
		
	public void pressAddBtn() {
		// [MING] Add new Labels to DB, then show it on displayVb (you should replace Array existLabels here)
		ArrayList<String> existLabels = new ArrayList<String>();
		existLabels.add("123");
		if ((labelTf.getText() != null) && !labelTf.getText().equals(""))
			existLabels.add(labelTf.getText());
		
		displayVb.getChildren().clear();
		
		// [MING] this part of code should be replace to pressLabelBtn() when finished add label to DB
		Label t = null;
		t  = new Label("Existed Labels: ");
		t.setFont(new Font("Yu Gothic UI Semibold", 18));
		displayVb.getChildren().add(t);
		for(String i : existLabels)
			t  = new Label(i);
			t.setFont(new Font("Yu Gothic UI Semibold", 18));
			displayVb.getChildren().add(t);
	}
	
	public void pressVBtn() throws ParseException {
		// [MING] Please add record to DB
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
		    Date date = new Date();  
		    System.out.println(formatter.format(date).toString());  
		    
		    String From = formatter.format(date).toString() + " " + fromTf.getText();
		    String To = formatter.format(date).toString() + " " + toTf.getText();
		    
		    System.out.println("From = " + From);
		    System.out.println("To = " + To);

		    
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			Date parsedDate = dateFormat.parse(From);
			Timestamp fromTimestamp = new java.sql.Timestamp(parsedDate.getTime());
			System.out.println("From Timestamp = " + fromTimestamp);
	//a
			Date parsedDate2 = dateFormat.parse(To);
			Timestamp toTimestamp = new java.sql.Timestamp(parsedDate2.getTime());
			System.out.println("To Timestamp = " + toTimestamp);

			String l = activityCombo.getValue();
			System.out.println("Label = " + l);

			
			
			showAllActivity();
			
		} catch(Exception e) {
			displayVb.getChildren().clear();
			Label t  = new Label("Invalid Time Format!");
			t.setFont(new Font("Yu Gothic UI Semibold", 18));
			displayVb.getChildren().add(t);
			
		}
	}
	
	private void showAllActivity() {
		displayVb.getChildren().clear();
		// [MING, Gting] show all activity on displaVb
	}

	public void pressLabelBtn() {
		status = MemberView.Label;
		render();		
		activityCombo.getItems().clear();
		activityCombo.setValue("Default");
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
		
		// [MING] Get all exist Labels from DB, then show it on displayVb (you should replace Array existLabels here)
		String[] existLabels = {"123", "456"};
		activityCombo.setValue("Default");
		for(int i = 0; i < 2; i++)
			activityCombo.getItems().add(existLabels[i]);
		displayVb.getChildren().clear();
		Label t = new Label("Existed Labels:");
		t.setFont(new Font("Yu Gothic UI Semibold", 18));
		displayVb.getChildren().add(t);
		for (int i = 0; i < existLabels.length; i++) {
			t = new Label(existLabels[i]);
			t.setFont(new Font("Yu Gothic UI Semibold", 18));			
			displayVb.getChildren().add(t);
		}
		
	}

	
	public void pressActivityBtn() throws ParseException {
		status = MemberView.Activity;
		render();		
		// [MING] Get all exist Labels from DB, (you should replace Array existLabels here)
		String[] existLabels = {"123", "456"};
		activityCombo.getItems().clear();
		activityCombo.setValue("Default");
		for(int i = 0; i < 2; i++)
			activityCombo.getItems().add(existLabels[i]);
		
		
//		activityCombo.getItems().set(0, "123");
//		activityCombo.getItems().set(1, "456");
		
		
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
		status = MemberView.Analysis;
		render();
		activityCombo.getItems().clear();
		activityCombo.setValue("Default");
		// use analysis function
		Member member = new Member(this.username);
		
		// Maybe you need to set time
		
//	    String str = "2022-06-11";
//	    Date dateOld = Date.valueOf(str); //converting string into sql date  
//	    System.out.println(dateOld);
//		
//		member.getAnalysis(dateOld);
//		member.showAnalysis();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		status = MemberView.Default;
		displayVb.setSpacing(5);
		render();
	}

}
