module ManageUrDay {
	requires java.sql;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
	opens application to javafx.graphics, javafx.fxml;
}