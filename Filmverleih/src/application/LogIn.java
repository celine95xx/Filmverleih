package application;



import java.io.IOException;

import controllers.UserDataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LogIn {

	
	@FXML
	private AnchorPane pane;
	
	@FXML
	private Pane contentArea;
	
	@FXML
	private TextField txtVorname;

	@FXML
	private TextField txtNachname;

	@FXML
	private TextField txtNewUserName;

	@FXML
	private TextField txtAge;
	
	@FXML
	private TextField txtNewUserPassword;

	@FXML
	private TextField txtNewPasswordConfirmed;

	@FXML
	private Label lblAnmeldestatus;

	@FXML
	private TextField txtUserName;

	@FXML
	private TextField txtPassword;
	
	@FXML
	private Label lblLogInInformation;
	
	@FXML
	private Label lblRegistrationInfo;

	
	
	public void logIn(ActionEvent event) throws Exception
	{
//		if(PruefeAnmeldedaten(txtBenutzername.getText(), txPasswort.getText())) 
		if(UserDataManager.manageLogin(txtUserName.getText(), txtPassword.getText()))
		{
		//if(txtBenutzername.getText().equals("Admin") && txPasswort.getText().equals("123456")) {
			//lblAnmeldestatus.setText("Anmeldung erfolgreich");
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/application/Hauptseite.fxml"));
			Scene scene = new Scene(root,1400,900);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED); //https://stackoverflow.com/questions/35250783/stage-without-bar-javafx
			primaryStage.show();
		}
		else if(!UserDataManager.checkUserInList(txtUserName.getText()))
		{
			txtUserName.setStyle("-fx-border-color: #DC1378; -fx-background-color: #121212");
//			lblLogInInformation.setText("Benutzer nicht registriert.");
//			lblLogInInformation.setVisible(true);

		}
		else if(!UserDataManager.checkLoginDataCombination(txtUserName.getText(), txtPassword.getText()))
		{
			txtPassword.setStyle("-fx-border-color: #DC1378; -fx-background-color: #121212");
//			lblLogInInformation.setText("Passwort nicht korrekt.");
//			lblLogInInformation.setVisible(true);
		}
		else 
		{
			lblAnmeldestatus.setText("Anmeldung fehlgeschlagen");
		}
	}
	
	
		public void openRegistrationScene(ActionEvent event) throws Exception
		{
			Parent fxml = FXMLLoader.load(getClass().getResource("Registration.fxml"));
			contentArea.getChildren().removeAll();
			contentArea.getChildren().setAll(fxml);
			
//			Stage primaryStage1 = new Stage();
//			Parent root1 = FXMLLoader.load(getClass().getResource("/application/Registrieren.fxml"));
//			Scene scene1 = new Scene(root1,1280, 720);
//			scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage1.setScene(scene1);
//			primaryStage1.show();
		}
		
		public void openLogInScene(ActionEvent event) throws Exception
		{
			
		}
		
		public void registrateUser(ActionEvent event) throws Exception
		{
			if(UserDataManager.manageUserRegistration(txtNewUserName.getText(), txtNewUserPassword.getText(), txtNewPasswordConfirmed.getText(), 0))
			{
				Stage primaryStage2 = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/application/Hauptseite.fxml"));
				Scene scene = new Scene(root,1280, 720);
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
			else
			{
				System.out.println("Registrierung nicht möglich.");
			}
		}
		
		
		//Löschen??
//		public boolean PruefeAnmeldedaten(String name,String passwort) 
//		{
//			boolean richtigeKombination;
//			//hier kommt code hin, der "richtigeKombination" = true macht, wenn die kombi stimmt, und false, wenn sie nicht stimmt
//			if(name.equals("Admin") && passwort.equals("admin")) 
//			{
//				richtigeKombination = true;
//			}
//			else 
//			{
//				richtigeKombination = false;
//			}
//			
//			return richtigeKombination;
//			
//		}
	}


