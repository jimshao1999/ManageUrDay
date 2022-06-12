module ManageUrDay {
	requires java.sql;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
	requires javafx.base;
	opens application to javafx.graphics, javafx.fxml;
}