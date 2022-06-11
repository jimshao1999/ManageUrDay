package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Member;
import view.LoginView;

public class SignupSuccessController extends Controller implements Initializable {
	
	@FXML
	private TextField usernameTf;
	
	@FXML
	private TextField passwordTf;

	@FXML
	private TextField confirmTf;

	@FXML
	private TextField nameTf;

	@FXML
	private TextField phoneTf;

	@FXML
	private TextField emailTf;

	@FXML
	private TextField addressTf;

	@FXML
	private ComboBox<String> typeCombo;

	@FXML
	private Label wrongLb;

	@FXML
	private Button backBtn;

	@FXML
	private Button confirmBtn;

	private LoginView status;
//	private UserType usertype;

	
	@Override
	protected void render() {
		if (status == LoginView.SignUp) {
			usernameTf.setVisible(true);
			passwordTf.setVisible(true);
			addressTf.setVisible(true);
			confirmTf.setVisible(true);
			wrongLb.setVisible(false);
			emailTf.setVisible(true);
			phoneTf.setVisible(true);
			nameTf.setVisible(true);
			typeCombo.setVisible(true);	
			confirmBtn.setVisible(true);

		} else { // sign up success
			usernameTf.setVisible(false);
			passwordTf.setVisible(false);
			addressTf.setVisible(false);
			confirmTf.setVisible(false);
			wrongLb.setVisible(true);
			emailTf.setVisible(false);
			phoneTf.setVisible(false);
			nameTf.setVisible(false);
			typeCombo.setVisible(false);	
			confirmBtn.setVisible(false);
		}
	}
	
	public void pressBackBtn(ActionEvent event) throws IOException {
		switchScene(ViewEnum.LOGIN, event);
	}
	
	private void setWrongLb(String s) {
		wrongLb.setText(s);
		wrongLb.setVisible(true);
	}

	public void pressConfirmBtn(ActionEvent event) throws IOException {
		switchScene(ViewEnum.MEMBER, event, usernameTf.getText());
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		status = LoginView.SignUp;
		typeCombo.getItems().setAll("Member", "Deliver", "Restaurant"); // set the options
		typeCombo.setValue("Member");
		render();
	}
}
