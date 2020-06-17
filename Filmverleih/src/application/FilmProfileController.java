package application;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.FilmDataManager;
import controllers.UserDataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FilmProfileController implements Initializable
{

	@FXML
	private Label txtFilmTitle;

	@FXML
	private Label txtGenre;

	@FXML
	private Label txtAge;

	@FXML
	private Label txtDescription;

	@FXML
	private Button btnRentFilm;

	@FXML
	private Button btnAddToWatchList;


	@FXML
	private AnchorPane filmBanner;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		updateScene();
		txtFilmTitle.setText(FilmDataManager.getFilm().getTitel());
		txtGenre.setText(FilmDataManager.getFilm().getGenre());
		filmBanner.setStyle("-fx-background-image: url('/images/"+ FilmDataManager.getFilm().getBanner() + "')");

		if(FilmDataManager.getFilm().getAlter() == true)
		{
			txtAge.setText("FSK 18");
		}

		if(FilmDataManager.getFilm().getAlter() == true & !UserDataManager.checkIfAdult())
		{
			btnRentFilm.setStyle("-fx-background-color: #DC1378; -fx-font: 16 system");
			btnRentFilm.setText("Nicht verfügbar");
			btnRentFilm.setDisable(true);

			btnAddToWatchList.setDisable(true);
		}
		
		if(UserDataManager.checkWatchList(FilmDataManager.getFilm().getId()))
		{
			btnAddToWatchList.setStyle("-fx-background-color: #121212; -fx-background-image: url('images/bookmark_white.PNG')");
		}
		else
		{
			btnAddToWatchList.setStyle("-fx-background-color: #121212; -fx-background-image: url('images/bookmark_border.PNG')");
		}

		txtDescription.setText(FilmDataManager.getFilm().getDescription());


	}

	public void updateScene()
	{
		System.out.println("Update Scene. Rent status: ");
		if(UserDataManager.checkRentedFilm(FilmDataManager.getFilm().getId()))
		{
			btnRentFilm.setStyle("-fx-background-color: #DC1378; -fx-font: 16 system");
			btnRentFilm.setText("Ausgeliehen");
			btnRentFilm.setDisable(true);
			System.out.println("Is rented!!: "+btnRentFilm.getText());
		}
		else
		{
			System.out.println("Not rented yet");
		}
	}

	public void rentFilm(ActionEvent event) throws Exception
	{
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/PayWindow.fxml"));
		Scene scene = new Scene(root,600,800);
		scene.setFill(Color.TRANSPARENT);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		//primaryStage.initStyle(StageStyle.TRANSPARENT); //https://stackoverflow.com/questions/35250783/stage-without-bar-javafx
		primaryStage.showAndWait(); //https://stackoverflow.com/questions/31184670/updating-ui-components-of-one-stage-based-on-event-in-another-stage-in-javafx
		updateScene();
	}

	public void addToWatchList(ActionEvent event) throws Exception
	{
		if(UserDataManager.checkWatchList(FilmDataManager.getFilm().getId()))
		{
			UserDataManager.deleteFilmFromWatchList(FilmDataManager.getFilm().getId());
			UserDataManager.getCurrentUser().showWatchList();
			btnAddToWatchList.setStyle("-fx-background-color: #121212; -fx-background-image: url('images/bookmark_border.PNG')");
		}
		else
		{
			UserDataManager.addFilmToWatchList(FilmDataManager.getFilm().getId());
			UserDataManager.getCurrentUser().showWatchList();
			btnAddToWatchList.setStyle("-fx-background-color: #121212; -fx-background-image: url('images/bookmark_white.PNG')");
		}
	}



}
