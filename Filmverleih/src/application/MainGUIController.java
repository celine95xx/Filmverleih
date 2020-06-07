package application;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.UserDataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class MainGUIController implements Initializable
{
	
	@FXML
	private Button btnControlPanel;
	
	@FXML
	private Label lblUsername;
	
	@FXML
	private Pane contentArea;
	
	
	public void showAllMovies(ActionEvent event) throws Exception
	{
		System.out.println("SHOW ALL MOVIES");
	}
	
	public void showProfile(ActionEvent event) throws Exception
	{
		System.out.println("SHOW PROFILE");
		
		Parent fxml = FXMLLoader.load(getClass().getResource("UserProfile.fxml"));
		contentArea.getChildren().removeAll();
		contentArea.getChildren().setAll(fxml);
		
	}
	
	public void showControlPanel(ActionEvent event) throws Exception
	{
		System.out.println("SHOW PROFILE");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		if(!UserDataManager.getCurrentUser().isAdmin())	
		{
			btnControlPanel.setVisible(false);
		}
		
		lblUsername.setText(UserDataManager.getCurrentUser().getName() + "!");
		
		
	}
}
