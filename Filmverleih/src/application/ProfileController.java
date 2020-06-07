package application;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.UserDataManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ProfileController implements Initializable
{
	@FXML
	private Label lblUsernameTitle;
	
	@FXML
	private TextField txtUsername;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		System.out.println("Profile ist hier!!!");
		lblUsernameTitle.setText(UserDataManager.getCurrentUser().getName());
		txtUsername.setPromptText(UserDataManager.getCurrentUser().getName());
		
		
	}

}
