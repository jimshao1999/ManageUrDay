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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
	private DatePicker datePicker;

	
	@FXML
	private ComboBox<String> activityCombo;

	@FXML
	private HBox activityHb;
	
	@FXML
	private HBox activityHb2;

	private String username;
	private String selectedDate = "";
	private MemberView status;
	
	
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
			activityHb2.setDisable(true);
			selectedDate = "2022-06-11";
			activityCombo.getItems().clear();
			activityCombo.setValue("Default");
			break;
		case Activity:
			labelHb.setDisable(true);
			activityHb.setDisable(false);
			activityHb2.setDisable(false);
			selectedDate = "2022-06-11";
			activityCombo.getItems().clear();
			activityCombo.setValue("Default");
			break;
		case Analysis:
			labelHb.setDisable(true);
			activityHb.setDisable(true);
			activityHb2.setDisable(true);
			activityCombo.getItems().clear();
			activityCombo.setValue("Default");
			break;
		case Default:
			labelHb.setDisable(false);
			activityHb.setDisable(true);
			activityHb2.setDisable(true);
			selectedDate = "2022-06-11";
			pressLabelBtn();
			activityCombo.getItems().clear();
			activityCombo.setValue("Default");
			break;
		}
		
	}
		
	public void pressAddBtn() {
		
		ActivityLabels labels = new ActivityLabels(this.username);		
		
		ArrayList<String> existLabels = labels.getLabels();
		
		// [MING], [Done] Add new Labels to DB, then show it on displayVb (you should replace Array existLabels here)
		if ((labelTf.getText() != null) && !labelTf.getText().equals("")) {
			if (existLabels.contains(labelTf.getText())) {
				// TODO [Gting] Need to show already exist related message.
				System.out.println("Label already exist!");
			}else {
				labels.addSingleLabel(labelTf.getText());
			}
		}
		displayVb.getChildren().clear();
		
		// [MING], [Done] this part of code should be replace to pressLabelBtn() when finished add label to DB
		pressLabelBtn();
	}
	
	public void pressVBtn() throws ParseException {
		// [MING] Please add record to DB
		try {
			String str = datePicker.getValue().toString();
		    Date date = java.sql.Date.valueOf(str); //converting string into sql date  
		    System.out.println("WW"+date);
		    
		    String From = date.toString() + " " + fromTf.getText();
		    String To = date.toString() + " " + toTf.getText();
			System.out.println(From);
			System.out.println(To);



			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			Date parsedDate = dateFormat.parse(From);
			Timestamp start_time = new java.sql.Timestamp(parsedDate.getTime());
			System.out.println("From Timestamp = " + start_time);

			Date parsedDate2 = dateFormat.parse(To);
			Timestamp end_time = new java.sql.Timestamp(parsedDate2.getTime());
			System.out.println("To Timestamp = " + end_time);	
			
			if (toTf.getText().startsWith("12")) {
				end_time = new Timestamp(end_time.getTime() + 12 * 3600 * 1000);
				System.out.println("To Timestamp = " + end_time);	
			}
			
		    if (!start_time.before(end_time)) {
		    	System.out.println("Time error");
		    	pressActivityBtn();
		    }
		    else {
		    	String choicedLabel = activityCombo.getValue();
		    	
				Activity activity = new Activity(this.username, choicedLabel, start_time, end_time);
				activity.setToDB();				
//				}
		    }
			displayVb.getChildren().clear();
			Label t  = new Label("Successfully add activity!"+activityCombo.getValue());
			t.setFont(new Font("Yu Gothic UI Semibold", 18));
			displayVb.getChildren().add(t);

		} catch(Exception e) {
			displayVb.getChildren().clear();
			Label t  = new Label("Invalid Time Format or Not Select Date!");
			t.setFont(new Font("Yu Gothic UI Semibold", 18));
			displayVb.getChildren().add(t);
			
		}
	}
	
	private void showAllActivity() {
		displayVb.getChildren().clear();
		// [MING, Gting] show all activity on displaVb
		displayVb.setPrefHeight(300);
		displayVb.setPrefWidth(360);
		DatePicker d = new DatePicker();
		d.setPromptText(selectedDate);
		d.setOnAction((e) -> {
			selectedDate = d.getValue().toString();
			System.out.println(selectedDate);
			showAllActivity();
		});
		displayVb.getChildren().add(d);

		// choose a day
		String str = selectedDate;
	    Date date = java.sql.Date.valueOf(str); //converting string into sql date  
	    System.out.println(date);
	    ArrayList<Activity> activities = Activity.getActivityFromDateInDB(this.username, (java.sql.Date) date);
	    
		TableView<Activity> tableView = new TableView<Activity>();

		TableColumn<Activity, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("activity_name"));
		tableView.getColumns().add(nameColumn);
		
		TableColumn<Activity, String> startColumn = new TableColumn<>("Start Time");
		startColumn.setCellValueFactory(new PropertyValueFactory<>("start_time"));
		tableView.getColumns().add(startColumn);

		TableColumn<Activity, String> endColumn = new TableColumn<>("End Time");
		endColumn.setCellValueFactory(new PropertyValueFactory<>("end_time"));
		tableView.getColumns().add(endColumn);
		
	    for (Activity activity: activities) {
	    	tableView.getItems().add(activity);
//	    	displayVb.getChildren().add(new Label("Name: " + activity.getActivity_name() + ", Start: " + activity.getStart_time_fix() + ", End: " + activity.getEnd_time_fix()));
//	    	System.out.println("Name: " + activity.getActivity_name() + ", Start: " + activity.getStart_time_fix() + ", End: " + activity.getEnd_time_fix());
	    }
	    displayVb.getChildren().add(tableView);

	}

	public void pressLabelBtn() {
		// Maybe can auto press label when login?
		
		status = MemberView.Label;
		render();		
		activityCombo.getItems().clear();
		activityCombo.setValue("Default");
		// Setting labels
		ActivityLabels labels = new ActivityLabels(this.username);
		
//		usage:
//		// Can show all labels, maybe sorted?
//		labels.getLabels();
//		
//		// maybe can input some name
//		
//		String labelName = "Reading";
//		
//		// Press the add label, can add the label by input label name
//		labels.addSingleLabel(labelName);
//		
//		// Can Update automatically
//		// pressLabelsBtn();
		
		// [MING] Get all exist Labels from DB, then show it on displayVb (you should replace Array existLabels here)
		ArrayList<String> existLabels = labels.getLabels();
//		String[] existLabels = {"123", "456"};
		activityCombo.setValue("Default");
		for(int i = 0; i < existLabels.size(); i++)
			activityCombo.getItems().add(existLabels.get(i));
		displayVb.getChildren().clear();
		Label t = new Label("Existed Labels:");
		t.setFont(new Font("Yu Gothic UI Semibold", 18));
		displayVb.getChildren().add(t);
		for (int i = 0; i < existLabels.size(); i++) {
			t = new Label(existLabels.get(i));
			t.setFont(new Font("Yu Gothic UI Semibold", 18));			
			displayVb.getChildren().add(t);
		}
		
	}

	
	public void pressActivityBtn() throws ParseException {
		status = MemberView.Activity;
		render();		
		// [MING] Get all exist Labels from DB, (you should replace Array existLabels here)
		ActivityLabels labels = new ActivityLabels(this.username);
		ArrayList<String> existLabels = labels.getLabels();
		
//		String[] existLabels = {"123", "456"};
		activityCombo.getItems().clear();
		activityCombo.setValue("Default");
		for(int i = 0; i < existLabels.size(); i++)
			activityCombo.getItems().add(existLabels.get(i));
		
		
		this.showAllActivity();
		
//		activityCombo.getItems().set(0, "123");
//		activityCombo.getItems().set(1, "456");
		
		
//		// setting activity
//		
//		// TODO Need to send member_id, Activity, start time, end time
//		
//		// TODO Can show all activity, and can choice one
//		
//		ActivityLabels labels = new ActivityLabels();
//		labels.getLabels();
//		
//		String choicedLabel = "Reading";
//		
//		// TODO [Discussion need] Choose the start time, maybe think how to set
//		Timestamp start_time = new Timestamp(0);
//		
//		// TODO [Discussion need] Choose the end time
//		Timestamp end_time = new Timestamp(1);
//		
//		// TODO check the timestamp and show error in fx
//		if (!start_time.before(end_time)) {
//			System.out.println("Time error");
//			pressActivityBtn();
//		}
//		else {
//			// TODO show it or send it
//			Activity activity = new Activity(this.username, choicedLabel, start_time, end_time);
//			// TODO [Optional] if check this is ok, then send it to db
//			activity.setToDB();
//			
//			// TODO Show the status success
//			
//			// TODO [Optional] Maybe can show all activity there later?
//		}
		
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
	    java.sql.Date dateOld = java.sql.Date.valueOf(selectedDate); //converting string into sql date  
	    System.out.println(dateOld);
		
		member.getAnalysis(dateOld);
		member.showAnalysis();
		
		HashMap<String, Float> AnalysisData = member.getAnalysisData();
		System.out.println("------------");
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();		
		for(String key: AnalysisData.keySet()) {
			System.out.println("Activity: " + key + " average takes " + AnalysisData.get(key) * 100+ "percentage per day.");
			pieChartData.add(new PieChart.Data(key, AnalysisData.get(key) * 100));
		}
		
		displayVb.getChildren().clear();
		
		final PieChart chart = new PieChart(pieChartData);	
		chart.setLegendVisible(false);
		chart.setLabelLineLength(10);
		if (AnalysisData.size() == 0) {
			chart.setTitle("No Data !");
		} else {
			chart.setTitle("Time Analysis of " + selectedDate);			
		}
		chart.setStyle("-fx-font: 12 arial;");
		final Label caption = new Label("");
		caption.setTextFill(Color.BLUE);
		caption.setStyle("-fx-font: 12 arial;");
		for (final PieChart.Data data : chart.getData()) {
		    data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
		        new EventHandler<MouseEvent>() {
		            @Override public void handle(MouseEvent e) {
//		                caption.setTranslateX(e.getSceneX());
//		                caption.setTranslateY(e.getSceneY());
		                caption.setText(String.valueOf("Label " + data.getName()+ ": "+data.getPieValue()) + "%");
		             }
		        });
		}
		displayVb.setPrefHeight(300);
		displayVb.setPrefWidth(360);
		DatePicker d = new DatePicker();
		d.setPromptText(selectedDate);
		d.setOnAction((e) -> {
			selectedDate = d.getValue().toString();
			System.out.println(selectedDate);
			pressAnalysisBtn();
		});
		displayVb.getChildren().add(d);
		displayVb.getChildren().add(caption);
		displayVb.getChildren().add(chart);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		status = MemberView.Default;
		displayVb.setSpacing(5);
		render();
	}

}
