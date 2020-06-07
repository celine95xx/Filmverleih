package application;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.UserDataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

public class ProfileController implements Initializable
{
	@FXML
	private Label lblUsernameTitle;
	
	@FXML
	private TextField txtUsername;
	
	@FXML
	private TextField txtBirthday;
	
	@FXML
	private PasswordField txtPassword;
	
	@FXML
	private Button btnEdit;
	
	@FXML
	private Button btnSave;
	
	@FXML
	private HBox boxNewPassword;
	
	@FXML
	private PasswordField txtNewPassword;
	
	@FXML
	private PasswordField txtNewPasswordConfirmed;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		System.out.println("Profile ist hier!!!");
		lblUsernameTitle.setText(UserDataManager.getCurrentUser().getName());
		txtUsername.setPromptText(UserDataManager.getCurrentUser().getName());
		txtBirthday.setPromptText(UserDataManager.getCurrentUser().getDateOfBirth());
		txtPassword.setText(UserDataManager.getCurrentUser().getPasswort());
		
	}
	
	public void editUserData(ActionEvent event) throws Exception
	{
		btnEdit.setStyle("-fx-background-color: #30c9c4");
		btnEdit.setOpacity(1);
		
		if(!UserDataManager.getCurrentUser().isAdmin())
		{
			txtUsername.setDisable(false);
		}
		
		boxNewPassword.setVisible(true);
		
		
		btnEdit.setVisible(false);
		btnSave.setVisible(true);
	}
	
	public void saveUserData(ActionEvent event) throws Exception
	{
		String changedUsername = txtUsername.getText();
		String changedPassword = txtNewPassword.getText();

		if(!UserDataManager.validateUserName(changedUsername))
		{
			System.out.println("Changed Username invalid!");
			changedUsername = UserDataManager.getCurrentUser().getName();
			txtUsername.setText(changedUsername);
		}
		//Send Data to UserDataManager
		else if(txtNewPassword.getText().equals(txtNewPasswordConfirmed.getText()))
		{
			if(!UserDataManager.saveUserDataChanges(txtUsername.getText(), txtNewPassword.getText()))
			{
				System.out.println("Username already taken");
				txtUsername.setText(UserDataManager.getCurrentUser().getName());
			}
		}
		else
		{
			System.out.println("Neue Passwörter stimmen nicht überein");
		}
		
		//Change GUI elements
		txtUsername.setDisable(true);
		btnSave.setVisible(false);
		boxNewPassword.setVisible(false);
		
		btnEdit.setStyle("-fx-background-color: #FFFFFF");
		btnEdit.setOpacity(0.07);
		btnEdit.setVisible(true);
	}
}
