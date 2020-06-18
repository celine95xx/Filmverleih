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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
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
	private Line lineUserName;

	@FXML
	private Line linePassword;

	@FXML
	private Line lineNewUserName;

	@FXML
	private Line lineNewPassword;

	@FXML
	private Line lineNewPasswordConfirmed;

	@FXML
	private AnchorPane paneLogIn;

	@FXML
	private AnchorPane paneRegistration;

	@FXML 
	private DatePicker datePicker;

	@FXML
	private Button errorNewUserName;
	
	@FXML
	private Button errorNewPassword;
	
	@FXML
	private Button errorUserName;
	
	@FXML
	private Button errorPassword;



	public void logIn(ActionEvent event) throws Exception
	{		
		//Reset warnings
		lineUserName.setStroke(Color.web("#30C9C4"));
		linePassword.setStroke(Color.web("#30C9C4"));
		errorUserName.setVisible(false);
		errorPassword.setVisible(false);
		
		
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
			lineUserName.setStroke(Color.web("#DC1378"));
			errorUserName.setVisible(true);
			errorUserName.setTooltip(new Tooltip("Benutzer nicht gefunden."));

		}
		else if(!UserDataManager.checkLoginDataCombination(txtUserName.getText(), txtPassword.getText()))
		{
			linePassword.setStroke(Color.web("#DC1378"));
			errorPassword.setVisible(true);
			errorPassword.setTooltip(new Tooltip("Passwort nicht korrekt."));
		}
		else 
		{
			System.out.println("Anmeldung nicht m�glich. !!!Pr�fen");
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
		//Reset warnings
		errorNewUserName.setVisible(false);
		lineNewUserName.setStroke(Color.web("#30C9C4"));
		errorNewUserName.setVisible(false);
		errorNewPassword.setVisible(false);

		String date = datePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); //https://stackoverflow.com/questions/33789307/how-to-convert-datepicker-value-to-string
		if(!UserDataManager.validateUserName(txtNewUserName.getText())) //Username invalid (because of number or type of characters)
		{
			lineNewUserName.setStroke(Color.web("#DC1378"));
			errorNewUserName.setVisible(true);
			errorNewUserName.setTooltip(new Tooltip("Benutzername ung�ltig. \nBitte w�hle einen Namen mit 3 - 14 Buchstaben. \nG�ltige Zeichen: a-z, A-Z, 0-9, _-"));
		}
		else if(UserDataManager.checkUserInList(txtNewUserName.getText())) //Username already taken
		{
			lineNewUserName.setStroke(Color.web("#DC1378"));
			errorNewUserName.setVisible(true);
			errorNewUserName.setTooltip(new Tooltip("Benutzername bereits vergeben."));
		}
		else if(date == null)
		{
			System.out.println("Login - registrateUser: No Birthday input");
		}
		else if(!txtNewUserPassword.getText().contentEquals(txtNewPasswordConfirmed.getText()))
		{
			lineNewPassword.setStroke(Color.web("#DC1378"));
			lineNewPasswordConfirmed.setStroke(Color.web("#DC1378"));
			errorNewPassword.setVisible(true);
			errorNewPassword.setTooltip(new Tooltip("Passw�rter stimmen nicht �berein."));
		}
		else if(txtNewUserPassword.getText().equals("") && txtNewPasswordConfirmed.getText().equals(""))
		{
			lineNewPassword.setStroke(Color.web("#DC1378"));
			lineNewPasswordConfirmed.setStroke(Color.web("#DC1378"));
			errorNewPassword.setVisible(true);
			errorNewPassword.setTooltip(new Tooltip("Ung�ltiges Passwort."));
		}
		else if(UserDataManager.calculateAge(date) < 16)
		{
			System.out.println("Zu jung zur Registrierung");
		}
		else if(UserDataManager.manageUserRegistration(txtNewUserName.getText(), txtNewUserPassword.getText(), txtNewPasswordConfirmed.getText(), date))
		{
			Stage primaryStage2 = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/application/MainGUI.fxml"));
			Scene scene = new Scene(root,1400, 1000);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage2.setScene(scene);
			primaryStage2.show();
		}
	}

}


