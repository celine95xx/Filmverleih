package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.sun.prism.paint.Color;

import controllers.FilmDataManager;
import controllers.UserDataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.UserData;

public class WelcomePageController implements Initializable
{

	@FXML
	private Label lblUsername;

	@FXML
	private AnchorPane paneWelcome;

	@FXML
	private Button btnNeuerscheinungen1;

	@FXML
	private Button btnNeuerscheinungen2;

	@FXML
	private Button btnNeuerscheinungen3;

	@FXML
	private Button btnNeuerscheinungen4;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		// TODO Auto-generated method stub
		lblUsername.setText(UserDataManager.getCurrentUser().getName() + "!");

		//Number of all films
		int listsize = 0;
		if(FilmDataManager.getNewestFilms().size() < 4)
		{
			listsize = FilmDataManager.getNewestFilms().size();
		}
		else
		{
			listsize = 4;
		}
		
		List<Button> buttonList = new ArrayList<Button>();
		buttonList.add(btnNeuerscheinungen1);
		buttonList.add(btnNeuerscheinungen2);
		buttonList.add(btnNeuerscheinungen3);
		buttonList.add(btnNeuerscheinungen4);


		for(int i = 0; i < listsize; i++)
		{
			String id = String.valueOf(FilmDataManager.getNewestFilms().get(i).getId());
			buttonList.get(i).setText(id);
			buttonList.get(i).setStyle("-fx-background-image: url('/images/"+ FilmDataManager.getNewestFilms().get(i).getThumbnail()+"'); -fx-text-fill: transparent; -fx-background-color: #121212");
			buttonList.get(i).setVisible(true);
		}
	}


	public void showFilmProfile (ActionEvent event) throws Exception
	{
		paneWelcome.getChildren().clear();

		int id = Integer.parseInt(((Button) event.getSource()).getText()); 
		FilmDataManager.setCurrentFilm(id);

		Parent fxml = FXMLLoader.load(getClass().getResource("FilmProfile.fxml"));
		paneWelcome.getChildren().setAll(fxml);


		System.out.println(FilmDataManager.getFilm().getTitel());
	}


}
