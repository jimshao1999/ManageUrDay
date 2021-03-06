package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Member;
import view.LoginView;

/*
 * LoginController control the login page.
 */
public class LoginController extends Controller implements Initializable {

	@FXML
	private Button loginBtn;

	@FXML
	private Button signupBtn;

	@FXML
	private Label wrongLb;

	@FXML
	private TextField usernameTf;

	@FXML
	private TextField passwordTf;

	// To set the error message for wrongLb
	// Can be extend to input label.
	private void setWrongLb(String s) {
		wrongLb.setText(s);
	}

	// Helper function: render by status
	// There is only 1 status at login -> No need to render here
	@Override
	protected void render() {

	}

	public void pressLoginBtn(ActionEvent event) throws IOException {
		usernameTf.getText();
		passwordTf.getText();

		// Add check who want to login? or use if to handle
		String pattern = "[0-9a-zA-Z]+";

		while (true) {
			if (!Pattern.matches(pattern, passwordTf.getText())) {
				// TODO (done) change the errormsg
				setWrongLb("Error pattern in username or password");
				wrongLb.setVisible(true);
				// refresh
				break;
			}

			Member member = new Member(usernameTf.getText(), passwordTf.getText());

			if (member.checklogin()) {
				switchScene(ViewEnum.MEMBER, event, usernameTf.getText());
				System.out.println("Login success as member");
			} else {
				setWrongLb("Login Fail");
				wrongLb.setVisible(true);
			}

			break;
		}
	}

	public void pressSignupBtn(ActionEvent event) throws IOException {
		switchScene(ViewEnum.SIGNUPSUCCESS, event);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		setWrongLb(null);
	}
}
