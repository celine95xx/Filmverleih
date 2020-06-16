package application;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
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
	private TextField txtThumbnail;

	@FXML
	private TextField txtBanner;

	@FXML
	private TextArea txtDescription;

	@FXML
	private CheckBox cbFSK;

	@FXML
	private AnchorPane anchorPane;


	public void registrateFilm(ActionEvent event) throws Exception 
	{
		if(txtTitle.getText() == null || txtTitle.getText().equals("")) 
		{
			txtTitle.setStyle("-fx-border-color: #DC1378; -fx-background-color: #121212");
			return;
		}

		// Registrate Film
		FilmDataManager.manageFilmRegistration(Integer.parseInt(txtID.getText()), txtTitle.getText(), txtGenre.getText(), Integer.parseInt(txtPrice.getText()), cbFSK.isSelected(), txtThumbnail.getText(), txtBanner.getText(), txtDescription.getText());
		
		// Close windows afterwards
		Stage stg = (Stage) cbFSK.getScene().getWindow();
		stg.close();	

	}

	public void searchThumbnail(ActionEvent event) throws Exception
	{
		FileChooser fileChooser = new FileChooser();

		Stage stage = (Stage) anchorPane.getScene().getWindow(); //JavaFX FileChooser: https://www.youtube.com/watch?v=hNz8Xf4tMI4
		File sourcefile = fileChooser.showOpenDialog(stage);
		String filename = sourcefile.getName();	
		Path sourcepath = Paths.get(sourcefile.getAbsolutePath()); //https://stackoverflow.com/questions/27931444/how-can-i-move-files-to-another-folder-with-java
		Path targetDirectory = Paths.get("./src/images/" + filename);

		if(sourcefile != null)
		{
			System.out.println("Path : " + sourcefile.getAbsolutePath());
		}

		Files.copy(sourcepath, targetDirectory); //https://www.java67.com/2016/09/how-to-copy-file-from-one-location-to-another-in-java.html
		txtThumbnail.setText(filename);
	}

	public void searchBanner(ActionEvent event) throws Exception
	{
		FileChooser fileChooser = new FileChooser();

		Stage stage = (Stage) anchorPane.getScene().getWindow(); //JavaFX FileChooser: https://www.youtube.com/watch?v=hNz8Xf4tMI4
		File sourcefile = fileChooser.showOpenDialog(stage);
		String filename = sourcefile.getName();	
		Path sourcepath = Paths.get(sourcefile.getAbsolutePath()); //https://stackoverflow.com/questions/27931444/how-can-i-move-files-to-another-folder-with-java
		Path targetDirectory = Paths.get("./src/images/" + filename);

		if(sourcefile != null)
		{
			System.out.println("Path : " + sourcefile.getAbsolutePath());
		}

		Files.copy(sourcepath, targetDirectory); //https://www.java67.com/2016/09/how-to-copy-file-from-one-location-to-another-in-java.html
		txtBanner.setText(filename);
	}
}
