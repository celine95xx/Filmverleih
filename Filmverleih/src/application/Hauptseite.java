package application;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.FilmDataManager;
import controllers.UserDataManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import models.FilmData;

public class Hauptseite implements Initializable {
	@FXML
	private ListView<FilmData> listView;

	@FXML
	private StackPane spStackPane;

	@FXML
	private ChoiceBox<String> ChoiceBox;

	public void addMovie(ActionEvent event) throws Exception {

		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Movies.fxml"));
		Scene scene = new Scene(root, 1280, 720);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// ObservableList:
	// https://stackoverflow.com/questions/36629522/convert-arraylist-to-observable-list-for-javafx-program
	// listView.setItems:
	// http://www.java2s.com/Tutorials/Java/JavaFX/0640__JavaFX_ListView.htm
	public void showAllMovies(ActionEvent event) throws Exception 
	{
		spStackPane.setVisible(true);
//		listView.getItems().addAll("Harry Potter", "Ich hab Hunger", "Wowi");
		listView.setItems(FXCollections.observableArrayList(FilmDataManager.getFilmList()));
		listView.setOrientation(Orientation.HORIZONTAL);
	}

	public void rentFilm(ActionEvent event) throws Exception {
		FilmData selectedFilm = (FilmData) listView.getSelectionModel().getSelectedItem();
		int id = selectedFilm.getId();
		UserDataManager.rentFilm("celine", id);

	}

	// Is called by JavaFX when this Scene is loaded
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Set Items for the Genre ChoiceBox
		ChoiceBox.setItems(FXCollections.observableArrayList("Alphabetisch", "Fantasy", "Action", "Horror"));

		// Add Listener for ChoiceBox - Listens for ItemChanged Events
		ChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String oldvalue, String newValue) {

				ObservableList<FilmData> newList;
				if (newValue.equals("Alphabetisch")) {
					// Sort alphabetically ascending
					FilmDataManager.sortFilmListByName();
					newList = FXCollections.observableArrayList(FilmDataManager.getFilmList());
				} else {
					// Sort by Genre
					newList = FXCollections.observableArrayList(FilmDataManager.filterFilmListGenre(newValue));

				}
				listView.setItems(newList);
			}
		});
	}

	public void deleteMovie(ActionEvent event) throws Exception {

		// Get selected film
		int filmIndex = listView.getSelectionModel().getSelectedIndex(); // This index may be greater than the actual
																			// count of films in the list
		if (filmIndex >= listView.getItems().size())
			return; // FilmIndex is out of bounds

		FilmData selectedFilm = listView.getItems().get(filmIndex);

		// Delete the selected film from the ListView
		listView.getItems().remove(selectedFilm);

		// Also delete the selected film from the underlying "oldFilmList" in
		// FilmDataManager
		FilmDataManager.deleteMovie(selectedFilm);

	}

}
