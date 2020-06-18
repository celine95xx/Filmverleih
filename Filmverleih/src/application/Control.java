package application;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import com.sun.glass.events.WindowEvent;

import controllers.FilmDataManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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
	@FXML
	private Label lblRecom1, lblRecom2, lblRecom3, lblRecom4;
	@FXML
	private Button btnRecom;

	private static List<Label> recomLabelList = new ArrayList<Label>();

	private final SimpleObjectProperty<FilteredList<FilmData>> dataListProperty = new SimpleObjectProperty<FilteredList<FilmData>>(new FilteredList<FilmData>(FilmDataManager.getFilmList()));



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{	
		recomLabelList.clear();
		recomLabelList.add(lblRecom1);
		recomLabelList.add(lblRecom2);
		recomLabelList.add(lblRecom3);
		recomLabelList.add(lblRecom4);


		//
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		titel.setCellValueFactory(new PropertyValueFactory<>("titel"));
		genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
		alter.setCellValueFactory(new PropertyValueFactory<>("alter"));

		movies.itemsProperty().bind(dataListProperty);

		movies.refresh();

		// Add Listener for Search Text Changed
		search.textProperty().addListener((obs, old, newV) -> {
			onSearchTextChanged(newV);
		});

		showRecommendations();

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

	public void deleteMovie(ActionEvent event) throws Exception 
	{
		// Get selected film
		int filmIndex = movies.getSelectionModel().getSelectedIndex(); // This index may be greater than the actual
		// count of films in the list
		if (filmIndex >= movies.getItems().size())
			return; // FilmIndex is out of bounds

		FilmData selectedFilm = movies.getItems().get(filmIndex);


		// Also delete the selected film from the underlying "oldFilmList" in
		// FilmDataManager
		FilmDataManager.deleteMovie(selectedFilm);

		movies.refresh();

	}



	public void onSearchTextChanged(String newV) 
	{
		String lNewV = newV.toLowerCase();

		if (newV != null && newV != "") {
			dataListProperty.get().setPredicate(itm -> itm.getTitel().toLowerCase().contains(lNewV) || itm.getGenre().toLowerCase().contains(lNewV));
		} else
		{
			dataListProperty.get().setPredicate(itm -> true);
		}
	}

	public void addToRecommendation(ActionEvent event) throws Exception
	{
		// Get selected film
		int filmIndex = movies.getSelectionModel().getSelectedIndex(); // This index may be greater than the actual
		// count of films in the list
		if (filmIndex >= movies.getItems().size())
			return; // FilmIndex is out of bounds

		int selectedFilmID = movies.getItems().get(filmIndex).getId();
		if(FilmDataManager.checkFilmInRecommendations(selectedFilmID))
		{
			FilmDataManager.deleteFromRecommendation(selectedFilmID);
			btnRecom.setStyle("-fx-background-color: #121212; -fx-background-image: url('images/star_border.PNG')");
		}
		else
		{
			FilmDataManager.addRecommendedFilm(selectedFilmID);
		}
		showRecommendations();

	}

	public void showRecommendations()
	{
		int numberRecommendations = FilmDataManager.getRecommendedFilms().size();

		if(numberRecommendations <= 4)
		{
			for(int i = 0; i < numberRecommendations; i++)
			{
				recomLabelList.get(i).setText(FilmDataManager.getFilmPerID(FilmDataManager.getRecommendedFilms().get(i)).getTitel());
			}

			for(int i = numberRecommendations; i < 4; i++)
			{
				recomLabelList.get(i).setText("-");
			}
		}
		if(numberRecommendations == 4)
		{
			btnRecom.setDisable(true);
			//btnRecom.setStyle("-fx-background-color: #121212; -fx-background-image: url('images/star_white.PNG')");
		}
	}


	public void selectRecommendation(MouseEvent event)
	{
		// Get selected film
		int filmIndex = movies.getSelectionModel().getSelectedIndex(); // This index may be greater than the actual
		// count of films in the list
		if (filmIndex >= movies.getItems().size())
			return; // FilmIndex is out of bounds

		int selectedFilmID = movies.getItems().get(filmIndex).getId();

		if(FilmDataManager.checkFilmInRecommendations(selectedFilmID))
		{
			btnRecom.setStyle("-fx-background-color: #121212; -fx-background-image: url('images/star_white.PNG')");
			btnRecom.setDisable(false);
		}
		else
		{
			btnRecom.setStyle("-fx-background-color: #121212; -fx-background-image: url('images/star_border.PNG')");
		}
	}

	public void mouseEnter(MouseEvent event)
	{
		lblRecom3.setTextFill(Color.BLUEVIOLET);
	}

	public void mouseExit(MouseEvent event)
	{
		lblRecom3.setTextFill(Color.WHITE);
	}

}
