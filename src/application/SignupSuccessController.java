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

/*
 * Sign up Controller can control the sign up page.
 */
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
			confirmTf.setVisible(true);
			wrongLb.setVisible(false);
			nameTf.setVisible(true);
			confirmBtn.setVisible(true);

		} else { // sign up success
			usernameTf.setVisible(false);
			passwordTf.setVisible(false);
			confirmTf.setVisible(false);
			wrongLb.setVisible(true);
			nameTf.setVisible(false);
			confirmBtn.setVisible(false);
		}
	}

	/*
	 * Back to login page button.
	 */
	public void pressBackBtn(ActionEvent event) throws IOException {
		switchScene(ViewEnum.LOGIN, event);
	}

	/*
	 * Showing the error msg to user.
	 */
	private void setWrongLb(String s) {
		wrongLb.setText(s);
		wrongLb.setVisible(true);
	}

	/*
	 * Send the information of users to db.
	 */
	public void pressConfirmBtn(ActionEvent event) throws IOException {

		// Please remove the rest and deliveryman tag.
		usernameTf.getText();
		passwordTf.getText();
		confirmTf.getText();

		// TODO [FX](done) need add the Tf of name
		String name = nameTf.getText();

		String pattern = "[0-9a-zA-Z]+";

		System.out.println(passwordTf.getText());
		System.out.println(confirmTf.getText());

		// TODO (done) [FX] Need a <select> in css to check which type(member, rest,
		// deliver...) the user want to sign-up.
		// usertype = UserType.Member;

		while (true) {

			if (!Pattern.matches(pattern, usernameTf.getText()) || !Pattern.matches(pattern, passwordTf.getText())) {
				// TODO [Fx](done) The string in wrongLb can be change to the string mentioned
				// below.
				setWrongLb("Error pattern in username or password");
				wrongLb.setVisible(true);
				break;
			}

			if (name.length() == 0) {
				setWrongLb("Please input name");
				wrongLb.setVisible(true);
				break;
			}

			if (!passwordTf.getText().equals(confirmTf.getText())) {
				// TODO [Fx](done) The string in wrongLb can be change to the string mentioned
				// below.
				setWrongLb("Error password and confirm password");
				wrongLb.setVisible(true);
				break;
			}
			// Maybe add email?

			Member member = new Member(usernameTf.getText(), passwordTf.getText(), name);

			if (member.isRegister()) {
				setWrongLb("Fail to register member");
				wrongLb.setVisible(true);
			} else {
				member.setToDB();
				setWrongLb("Successfully sign up!, press Go back to login!");
				status = LoginView.Success;
				render();
			}
			break;
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		status = LoginView.SignUp;
		render();
	}
}
