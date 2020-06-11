package application;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.FilmDataManager;
import controllers.UserDataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

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
	private AnchorPane filmBanner;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		System.out.println("Initialize FilmProfile");
		txtFilmTitle.setText(FilmDataManager.getFilm().getTitel());
		txtGenre.setText(FilmDataManager.getFilm().getGenre());
		
		if(FilmDataManager.getFilm().getAlter() == true)
		{
			txtAge.setText("FSK 18");
		}
		
		filmBanner.setStyle("-fx-background-image: url('/images/"+ FilmDataManager.getFilm().getBanner() + "')");
		
		txtDescription.setText(FilmDataManager.getFilm().getDescription());
		
		if(UserDataManager.checkRentedFilm(FilmDataManager.getFilm().getId()))
		{
			btnRentFilm.setStyle("-fx-background-color: #DC1378; -fx-font: 16 system");
			btnRentFilm.setText("Ausgeliehen");
			btnRentFilm.setDisable(true);
		}
	}
	
	public void rentFilm(ActionEvent event) throws Exception
	{
		UserDataManager.rentFilm(FilmDataManager.getFilm().getId());
		UserDataManager.getCurrentUser().showRentedFilms();
		
		btnRentFilm.setStyle("-fx-background-color: #DC1378; -fx-font: 16 system");
		btnRentFilm.setText("Ausgeliehen");
		btnRentFilm.setDisable(true);
		
		
	}
	
	

}
