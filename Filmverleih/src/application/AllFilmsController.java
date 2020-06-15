package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import controllers.FilmDataManager;
import controllers.UserDataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class AllFilmsController implements Initializable
{
	@FXML
	private AnchorPane paneAllFilms;
	
	@FXML
	private Button btnFilm1, btnFilm2, btnFilm3, btnFilm4, btnFilm5, btnFilm6, btnFilm7, btnFilm8, btnFilm9, btnFilm10, btnFilm11, btnFilm12, btnFilm13, btnFilm14, btnFilm15, btnFilm16;
	
	@FXML
	private Button btnNextPage;
	
	@FXML
	private Button btnPrevPage;
	
	private static int page;
	
	private static List<Button> allFilms = new ArrayList<Button>();
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		System.out.println("Initialize ALL FILMS");
		
		page = 1;
		
		allFilms.clear();
		allFilms.add(btnFilm1);
		allFilms.add(btnFilm2);
		allFilms.add(btnFilm3);
		allFilms.add(btnFilm4);
		allFilms.add(btnFilm5);
		allFilms.add(btnFilm6);
		allFilms.add(btnFilm7);
		allFilms.add(btnFilm8);
		allFilms.add(btnFilm9);
		allFilms.add(btnFilm10);
		allFilms.add(btnFilm11);
		allFilms.add(btnFilm12);
		allFilms.add(btnFilm13);
		allFilms.add(btnFilm14);
		allFilms.add(btnFilm15);
		allFilms.add(btnFilm16);
		
		if(FilmDataManager.getFilmList().size() <= 16)
		{
			btnNextPage.setVisible(false);
		}
		btnPrevPage.setVisible(false);
		showFilms();
		
	}
	
	
	
	public void showFilmProfile(ActionEvent event) throws Exception
	{
		paneAllFilms.getChildren().clear();

		int id = Integer.parseInt(((Button) event.getSource()).getText()); 
		FilmDataManager.setCurrentFilm(id);

		Parent fxml = FXMLLoader.load(getClass().getResource("FilmProfile.fxml"));
		paneAllFilms.getChildren().setAll(fxml);


		System.out.println(FilmDataManager.getFilm().getTitel());
	}
	
	public void clickThrough(ActionEvent event) throws Exception
	{
		if(((Button) event.getSource()).getText().equals("next"))
		{
			page = page + 1;
		}
		else
		{
			page = page - 1;
		}
		System.out.println("WatchList - Seite: " + page);

		showFilms();
	}
	
	public void showFilms()
	{
		//Show/Hide "Previous"-Button
				if(page == 1)
				{
					btnPrevPage.setVisible(false);
				}
				else
				{
					btnPrevPage.setVisible(true);
				}

				//Save number of rented films and "leftovers"
				int numberAllFilms = FilmDataManager.getFilmList().size();
				int leftovers = numberAllFilms%16;


				//If there are more films than could be displayed yet, show the next 4 films
				if(numberAllFilms >= (page * 16))
				{
					for(int i = 0; i < 16; i++)
					{
						int id = FilmDataManager.getFilmList().get((numberAllFilms - 16*(page - 1) -1 - i)).getId();
						allFilms.get(i).setText(String.valueOf(id));
						allFilms.get(i).setStyle("-fx-background-image: url('/images/"+ FilmDataManager.getFilmPerID(id).getThumbnail()+"'); -fx-text-fill: transparent; -fx-background-color: #121212");
						allFilms.get(i).setVisible(true);

						System.out.println(FilmDataManager.getFilmPerID(id).getTitel());
						btnNextPage.setVisible(true);
					}
					
					if(numberAllFilms == (page * 4))
					{
						btnNextPage.setVisible(false);
					}
				}
				//If there are not enough films to fill 4 slots, only show the leftovers
				else 
				{
					for(int i = 0; i < leftovers; i++)
					{
						int id = FilmDataManager.getFilmList().get((numberAllFilms - 16*(page - 1) -1 - i)).getId();
						allFilms.get(i).setText(String.valueOf(id));
						allFilms.get(i).setStyle("-fx-background-image: url('/images/"+ FilmDataManager.getFilmPerID(id).getThumbnail()+"'); -fx-text-fill: transparent; -fx-background-color: #121212");
						allFilms.get(i).setVisible(true);

						System.out.println(FilmDataManager.getFilmPerID(id).getTitel());
					}
					for(int i = leftovers; i < 16; i++)
					{
						allFilms.get(i).setVisible(false);
					}
					btnNextPage.setVisible(false);
				}
	}
	
	

}
