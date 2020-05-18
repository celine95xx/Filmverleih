package application;

import controllers.FilmDataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Movies 
{
	@FXML
	private TextField txtID;
	
	@FXML
	private TextField txtTitle;
	
	@FXML
	private TextField txtGenre;
	
	@FXML
	private TextField txtPrice;
	
	@FXML
	private CheckBox cbFSK;
	
	
	
	public void registrateFilm(ActionEvent event) throws Exception 
	{
		
		// Registrate Film
		FilmDataManager.manageFilmRegistration(Integer.parseInt(txtID.getText()), txtTitle.getText(), txtGenre.getText(), Integer.parseInt(txtPrice.getText()), cbFSK.isSelected());

		
		// Close windows afterwards
		Stage stg = (Stage)cbFSK.getScene().getWindow();
		stg.close();
	}
}
