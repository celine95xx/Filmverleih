package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import controllers.FilmDataManager;
import controllers.UserDataManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
	private TableColumn<FilmData, String> fsk;
	@FXML
	private Label lblRecom1, lblRecom2, lblRecom3, lblRecom4;
	@FXML
	private Button btnRecom;

	private static List<Label> recomLabelList = new ArrayList<Label>();

	private final SimpleObjectProperty<FilteredList<FilmData>> dataListProperty = new SimpleObjectProperty<FilteredList<FilmData>>(
			new FilteredList<FilmData>(FilmDataManager.getFilmList()));

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		recomLabelList.clear();
		recomLabelList.add(lblRecom1);
		recomLabelList.add(lblRecom2);
		recomLabelList.add(lblRecom3);
		recomLabelList.add(lblRecom4);

		//
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		titel.setCellValueFactory(new PropertyValueFactory<>("titel"));
		genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
		fsk.setCellValueFactory(new PropertyValueFactory<>("fsk"));

		movies.itemsProperty().bind(dataListProperty);

		movies.refresh();

		// Add Listener for Search Text Changed
		search.textProperty().addListener((obs, old, newV) -> {
			onSearchTextChanged(newV);
		});

		showRecommendations();

	}

	public void addMovie(ActionEvent event) throws Exception {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Movies.fxml"));
		Scene scene = new Scene(root, 1000, 720);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void deleteMovie(ActionEvent event) throws Exception {

		// Get selected film
		int filmIndex = movies.getSelectionModel().getSelectedIndex(); // This index may be greater than the actual count of films in the list

		if(movies.getSelectionModel().getSelectedItem() != null)
		{
			if (filmIndex >= movies.getItems().size())
				return; // FilmIndex is out of bounds

			FilmData selectedFilm = movies.getItems().get(filmIndex);

			ImageView confirmIcon = new ImageView("/images/confirmation.PNG");
			confirmIcon.setFitHeight(36);
			confirmIcon.setFitWidth(36);

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Film l�schen");
			alert.setHeaderText("Soll der Film: " + selectedFilm.getTitel() + " unwiderruflich gel�scht werden?" );
			alert.setContentText("Damit ist dieser Film nicht mehr f�r Nutzer verf�gbar und alle Filmdaten gehen verloren.");
			alert.initStyle(StageStyle.UNDECORATED);

			DialogPane dialogPane = alert.getDialogPane();
			dialogPane.getStylesheets().add(getClass().getResource("/styles/Buttons.css").toExternalForm());
			dialogPane.getStyleClass().add("Buttons");
			dialogPane.setGraphic(confirmIcon);

			Optional<ButtonType> option = alert.showAndWait();
			if(option.get() == ButtonType.OK)
			{
				// Also delete the selected film from the underlying "oldFilmList" in FilmDataManager
				FilmDataManager.deleteMovie(selectedFilm);
				UserDataManager.checkUserFilmLists();
				FilmDataManager.deleteFromRecommendation(selectedFilm.getId());
				showRecommendations();
			}

			movies.refresh();
		}


	}

	public void onSearchTextChanged(String newV) {
		String lNewV = newV.toLowerCase();

		if (newV != null && newV != "") {
			dataListProperty.get().setPredicate(itm -> itm.getTitel().toLowerCase().contains(lNewV)
					|| itm.getGenre().toLowerCase().contains(lNewV));
		} else {
			dataListProperty.get().setPredicate(itm -> true);
		}
	}

	public void addToRecommendation(ActionEvent event) throws Exception {
		// Get selected film
		int filmIndex = movies.getSelectionModel().getSelectedIndex(); // This index may be greater than the actual count of films in the list

		if(movies.getSelectionModel().getSelectedItem() != null)
		{
			if (filmIndex >= movies.getItems().size())
				return; // FilmIndex is out of bounds

			int selectedFilmID = movies.getItems().get(filmIndex).getId();
			if (FilmDataManager.checkFilmInRecommendations(selectedFilmID)) {
				FilmDataManager.deleteFromRecommendation(selectedFilmID);
				btnRecom.setStyle("-fx-background-color: #121212; -fx-background-image: url('images/star_border.PNG')");
			} else {
				FilmDataManager.addRecommendedFilm(selectedFilmID);
			}
		}

		showRecommendations();

	}

	public void showRecommendations() 
	{
		int numberRecommendations = FilmDataManager.getRecommendedFilms().size();

		if (numberRecommendations <= 4) {
			for (int i = 0; i < numberRecommendations; i++) {
				recomLabelList.get(i)
				.setText(FilmDataManager.getFilmPerID(FilmDataManager.getRecommendedFilms().get(i)).getTitel());
			}

			for (int i = numberRecommendations; i < 4; i++) {
				recomLabelList.get(i).setText("-");
			}
		}
		if (numberRecommendations == 4) {
			btnRecom.setDisable(true);
			// btnRecom.setStyle("-fx-background-color: #121212; -fx-background-image:
			// url('images/star_white.PNG')");
		}
	}

	public void selectRecommendation(MouseEvent event) 
	{
		// Get selected film
		int filmIndex = movies.getSelectionModel().getSelectedIndex(); // This index may be greater than the actual count of films in the list

		if (filmIndex >= movies.getItems().size())
			return; // FilmIndex is out of bounds

		int selectedFilmID = movies.getItems().get(filmIndex).getId();

		if (FilmDataManager.checkFilmInRecommendations(selectedFilmID)) {
			btnRecom.setStyle("-fx-background-color: #121212; -fx-background-image: url('images/star_white.PNG')");
			btnRecom.setDisable(false);
		} else {
			btnRecom.setStyle("-fx-background-color: #121212; -fx-background-image: url('images/star_border.PNG')");
		}
	}

	public void mouseEnter(MouseEvent event) {
		lblRecom3.setTextFill(Color.BLUEVIOLET);
	}

	public void mouseExit(MouseEvent event) {
		lblRecom3.setTextFill(Color.WHITE);
	}

}
