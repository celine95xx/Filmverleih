package application;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.FilmDataManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.FilmData;

public class Movies {
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

	public void registrateFilm(ActionEvent event) throws Exception {

		
		
		
		if(txtTitle.getText() == null || txtTitle.getText().equals("")) {
			txtTitle.setStyle("-fx-border-color: #DC1378; -fx-background-color: #121212");
			return;
		}
		

		// Registrate Film
		FilmDataManager.manageFilmRegistration(Integer.parseInt(txtID.getText()), txtTitle.getText(),
				txtGenre.getText(), Integer.parseInt(txtPrice.getText()), cbFSK.isSelected());
		// Close windows afterwards
				Stage stg = (Stage) cbFSK.getScene().getWindow();
				stg.close();	
		
	}
	
}
