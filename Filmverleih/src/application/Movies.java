package application;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

import controllers.FilmDataManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Movies implements Initializable {
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
	private ChoiceBox<String> cbGenre;

	@FXML
	private ChoiceBox<Integer> cbFsk;

	@FXML
	private AnchorPane anchorPane;
	
	@FXML
	private Line lineID;
	
	@FXML
	private Line lineTitle;
	
	@FXML
	private Line lineThumbnail;
	
	@FXML
	private Line lineBanner;
	
	@FXML
	private Line linePrice;

	public void initialize(URL arg0, ResourceBundle arg1) {
		cbGenre.setItems(FXCollections.observableArrayList("Action", "Animation", "Drama", "Fantasy", "Horror", "Krimi",
				"Komödie"));
		cbGenre.getSelectionModel().selectFirst();

		cbFsk.setItems(FXCollections.observableArrayList(0, 6, 12, 16, 18));
		cbFsk.getSelectionModel().selectFirst();
		
	}

	public void registrateFilm(ActionEvent event) throws Exception 
	{
		if (txtTitle.getText() == null || txtTitle.getText().equals("")) {
			lineTitle.setStroke(Color.web("#DC1378"));
			//txtTitle.setStyle("-fx-border-color: #DC1378; -fx-background-color: #121212");
			return;
		}

		if (txtID.getText() == null || txtID.getText().equals("") || !FilmDataManager.checkFilmId(txtID.getText()) || FilmDataManager.getFilmPerID(Integer.parseInt(txtID.getText())) != null) {
			//txtID.setStyle("-fx-border-color: #DC1378; -fx-background-color: #121212");
			lineID.setStroke(Color.web("#DC1378"));
			return;
		}
		if (txtPrice.getText() == null || txtPrice.getText().equals("")) {
			//txtPrice.setStyle("-fx-border-color: #DC1378; -fx-background-color: #121212");
			linePrice.setStroke(Color.web("#DC1378"));
			return;
		}
//		if (txtBanner.getText() == null || txtBanner.getText().equals("")) {
//			txtBanner.setStyle("-fx-border-color: #DC1378; -fx-background-color: #121212");
//			lineBanner.setStroke(Color.web("#DC1378"));
//			return;
//		}
//		if (txtThumbnail.getText() == null || txtThumbnail.getText().equals("")) {
//			//txtThumbnail.setStyle("-fx-border-color: #DC1378; -fx-background-color: #121212");
//			lineThumbnail.setStroke(Color.web("#DC1378"));
//			
//			return;
//		}
		if (txtDescription.getText() == null || txtDescription.getText().equals("")) {
			txtDescription.setStyle("-fx-border-color: #DC1378; -fx-background-color: #121212");
			return;
		}
		// Registrate Film
		FilmDataManager.manageFilmRegistration(Integer.parseInt(txtID.getText()), txtTitle.getText(),
				cbGenre.getSelectionModel().getSelectedItem(), Double.parseDouble(txtPrice.getText()),
				cbFsk.getSelectionModel().getSelectedItem(), txtThumbnail.getText(), txtBanner.getText(),
				txtDescription.getText());

		// Close windows afterwards
		Stage stg = (Stage) cbFsk.getScene().getWindow();
		stg.close();

	}

	public void handleNewWindow() {
		System.out.println("Movies: New Window shown!");
	}

	public void searchThumbnail(ActionEvent event) throws Exception {
		FileChooser fileChooser = new FileChooser();

		Stage stage = (Stage) anchorPane.getScene().getWindow(); // JavaFX FileChooser:
																	// https://www.youtube.com/watch?v=hNz8Xf4tMI4
		File sourcefile = fileChooser.showOpenDialog(stage);
		String filename = sourcefile.getName();
		Path sourcepath = Paths.get(sourcefile.getAbsolutePath()); // https://stackoverflow.com/questions/27931444/how-can-i-move-files-to-another-folder-with-java
		Path targetDirectory = Paths.get("src/filmimages/" + filename);

		if (sourcefile != null) {
			System.out.println("Absolute Pathname : " + sourcefile.getAbsolutePath());
		}

		Files.copy(sourcepath, targetDirectory, StandardCopyOption.REPLACE_EXISTING); // https://www.java67.com/2016/09/how-to-copy-file-from-one-location-to-another-in-java.html
		txtThumbnail.setText(filename);

	}

	public void searchBanner(ActionEvent event) throws Exception {
		FileChooser fileChooser = new FileChooser();

		Stage stage = (Stage) anchorPane.getScene().getWindow(); // JavaFX FileChooser:
																	// https://www.youtube.com/watch?v=hNz8Xf4tMI4
		File sourcefile = fileChooser.showOpenDialog(stage);
		String filename = sourcefile.getName();
		Path sourcepath = Paths.get(sourcefile.getAbsolutePath()); // https://stackoverflow.com/questions/27931444/how-can-i-move-files-to-another-folder-with-java
		Path targetDirectory = Paths.get("src/filmimages/" + filename);

		if (sourcefile != null) {
			System.out.println("Path : " + sourcefile.getAbsolutePath());
		}

		Files.copy(sourcepath, targetDirectory, StandardCopyOption.REPLACE_EXISTING); // https://www.java67.com/2016/09/how-to-copy-file-from-one-location-to-another-in-java.html
		txtBanner.setText(filename);
	}
}
