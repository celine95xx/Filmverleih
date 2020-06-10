package application;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import controllers.FilmDataManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import models.FilmData;

public class Control implements Initializable {

	@FXML
	private Label label;
	@FXML
	private TextField search;
	@FXML
	private TableView<FilmData> movies;
	@FXML
	private TableColumn<FilmData, String> id;
	@FXML
	private TableColumn<FilmData, String> titel;
	@FXML
	private TableColumn<FilmData, String> genre;
	@FXML
	private TableColumn<FilmData, String> alter;

	private final SimpleObjectProperty<ObservableList<FilmData>> dataListProperty = new SimpleObjectProperty<ObservableList<FilmData>>(
			FilmDataManager.getFilmList());

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// 
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		titel.setCellValueFactory(new PropertyValueFactory<>("titel"));
		genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
		alter.setCellValueFactory(new PropertyValueFactory<>("alter"));

		movies.itemsProperty().bind(dataListProperty);


		movies.refresh();
		

	}
	public void addMovie(ActionEvent event) throws Exception 
	{
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Movies.fxml"));
		Scene scene = new Scene(root, 1280, 720);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public void deleteMovie(ActionEvent event) throws Exception {

		// Get selected film
		int filmIndex = movies.getSelectionModel().getSelectedIndex(); // This index may be greater than the actual
																			// count of films in the list
		if (filmIndex >= movies.getItems().size())
			return; // FilmIndex is out of bounds

		FilmData selectedFilm = movies.getItems().get(filmIndex);

		// Delete the selected film from the ListView
		movies.getItems().remove(selectedFilm);

		// Also delete the selected film from the underlying "oldFilmList" in
		// FilmDataManager
		FilmDataManager.deleteMovie(selectedFilm);
	}
	

}
