package application;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.UserDataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;

public class WelcomePageController implements Initializable
{
	
	@FXML
	private Label lblUsername;
	
	public void showFilmProfile (ActionEvent event) throws Exception
	{
		System.out.println("SHOW FILM PROFILE");
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainGUI.fxml"));
		Parent sceneFXML = loader.load();
		MainGUIController ctrl = (MainGUIController)(loader.getController());
		ctrl.setContentArea();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		// TODO Auto-generated method stub
		lblUsername.setText(UserDataManager.getCurrentUser().getName() + "!");
	}

	
}
