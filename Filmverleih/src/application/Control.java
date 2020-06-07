package application;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.FilmDataManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
			FXCollections.observableArrayList());

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		titel.setCellValueFactory(new PropertyValueFactory<>("titel"));
		genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
		alter.setCellValueFactory(new PropertyValueFactory<>("alter"));

		movies.itemsProperty().bind(dataListProperty);

		dataListProperty.get().addAll(FilmDataManager.getFilmList());

		movies.refresh();
	}
}
