package application;



import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import controllers.UserDataManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LogIn
{

	@FXML
	private AnchorPane pane;	
	
	@FXML
	private Pane contentArea;

	@FXML
	private TextField txtNewUserName;

	@FXML
	private TextField txtAge;
	
	@FXML
	private TextField txtNewUserPassword;

	@FXML
	private TextField txtNewPasswordConfirmed;

	@FXML
	private TextField txtUserName;

	@FXML
	private TextField txtPassword;
	
	@FXML
	private Label lblLogInInformation;
	
	@FXML
	private Label lblRegistrationInfo;
	
	@FXML
	private AnchorPane paneLogIn;
	
	@FXML
	private AnchorPane paneRegistration;
	
	@FXML 
	private DatePicker datePicker;


	
	public void logIn(ActionEvent event) throws Exception
	{		
		if(UserDataManager.manageLogin(txtUserName.getText(), txtPassword.getText()))
		{
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/application/MainGUI.fxml"));
			Scene scene = new Scene(root,1400,1000);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			//primaryStage.initStyle(StageStyle.UNDECORATED); //https://stackoverflow.com/questions/35250783/stage-without-bar-javafx
			primaryStage.show();
			
			
		}
		else if(!UserDataManager.checkUserInList(txtUserName.getText()))
		{
			txtUserName.setStyle("-fx-border-color: #DC1378; -fx-background-color: #121212");

		}
		else if(!UserDataManager.checkLoginDataCombination(txtUserName.getText(), txtPassword.getText()))
		{
			txtPassword.setStyle("-fx-border-color: #DC1378; -fx-background-color: #121212");
		}
		else 
		{
			System.out.println("Anmeldung nicht möglich. !!!Prüfen");
		}
	}
	
		public void openRegistrationScene(ActionEvent event) throws Exception
		{
			paneLogIn.setVisible(false);
			paneRegistration.setVisible(true);
		}
		
		public void openLogInScene(ActionEvent event) throws Exception
		{
			paneRegistration.setVisible(false);
			paneLogIn.setVisible(true);
		}
		
		public void registrateUser(ActionEvent event) throws Exception
		{
			String date = datePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); //https://stackoverflow.com/questions/33789307/how-to-convert-datepicker-value-to-string
			if(UserDataManager.manageUserRegistration(txtNewUserName.getText(), txtNewUserPassword.getText(), txtNewPasswordConfirmed.getText(), date))
			{
				Stage primaryStage2 = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/application/MainGUI.fxml"));
				Scene scene = new Scene(root,1400, 1000);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage2.setScene(scene);
				primaryStage2.show();
			}
			else if(!UserDataManager.validateUserName(txtNewUserName.getText()))
			{
				lblRegistrationInfo.setText("Benutzername ungültig.");
				lblRegistrationInfo.setVisible(true);
			}
			else if(UserDataManager.checkUserInList(txtNewUserName.getText()))
			{
				lblRegistrationInfo.setText("Benutzername schon vergeben.");
				lblRegistrationInfo.setVisible(true);
			}
			else if(!txtNewUserPassword.getText().contentEquals(txtNewPasswordConfirmed.getText()))
			{
				lblRegistrationInfo.setText("Passwörter stimmen nicht überein.");
				lblRegistrationInfo.setVisible(true);
			}
			else if(UserDataManager.calculateAge(date) < 16)
			{
				System.out.println("Zu jung zur Registrierung");
			}
			else
			{
				System.out.println("Registrierung nicht möglich.");
			}
		}

	}


