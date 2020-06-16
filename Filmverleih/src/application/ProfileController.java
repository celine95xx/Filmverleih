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
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class ProfileController implements Initializable
{
	@FXML
	private AnchorPane paneProfile;

	@FXML
	private Label lblUsernameTitle;

	@FXML
	private TextField txtUsername;

	@FXML
	private TextField txtBirthday;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private Button btnEdit;

	@FXML
	private Button btnSave;

	@FXML
	private HBox boxNewPassword;

	@FXML
	private PasswordField txtNewPassword;

	@FXML
	private PasswordField txtNewPasswordConfirmed;

	@FXML
	private Button btnRentedFilm1;

	@FXML
	private Button btnRentedFilm2;

	@FXML
	private Button btnRentedFilm3;

	@FXML
	private Button btnRentedFilm4;

	@FXML
	private Button btnWatchList1;

	@FXML
	private Button btnWatchList2;

	@FXML
	private Button btnWatchList3;

	@FXML
	private Button btnWatchList4;

	@FXML
	private Button btnRentedFilmsNext;

	@FXML
	private Button btnRentedFilmsPrev;

	@FXML
	private Button btnWatchlistNext;

	@FXML
	private Button btnWatchlistPrev;

	@FXML
	private GridPane gpRentedFilms;

	@FXML
	private ScrollPane spRentedFilms;

	private static int rentedFilmPage;

	private static int watchlistPage;

	private static List<Button> rentedList = new ArrayList<Button>();

	private static List<Button> watchList = new ArrayList<Button>();


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		rentedFilmPage = 1;
		watchlistPage = 1;

		lblUsernameTitle.setText(UserDataManager.getCurrentUser().getName());
		txtUsername.setPromptText(UserDataManager.getCurrentUser().getName());
		txtBirthday.setPromptText(UserDataManager.getCurrentUser().getDateOfBirth());
		txtPassword.setText(UserDataManager.getCurrentUser().getPasswort());
		

		//RENTED FILMS
		rentedList.clear();
		rentedList.add(btnRentedFilm1);
		rentedList.add(btnRentedFilm2);
		rentedList.add(btnRentedFilm3);
		rentedList.add(btnRentedFilm4);

		if(UserDataManager.getCurrentUser().getRentedFilms().size() <= 4)
		{
			btnRentedFilmsNext.setVisible(false);
		}
		showRentedFilms();


		//WATCH LIST
		watchList.clear();
		watchList.add(btnWatchList1);
		watchList.add(btnWatchList2);
		watchList.add(btnWatchList3);
		watchList.add(btnWatchList4);

		if(UserDataManager.getCurrentUser().getWatchList().size() <= 4)
		{
			btnWatchlistNext.setVisible(false);
		}
		showWatchList();

	}


	public void editUserData(ActionEvent event) throws Exception
	{
		btnEdit.setStyle("-fx-background-color: #30c9c4");
		btnEdit.setOpacity(1);

		if(!UserDataManager.getCurrentUser().isAdmin())
		{
			txtUsername.setDisable(false);
		}

		boxNewPassword.setVisible(true);


		btnEdit.setVisible(false);
		btnSave.setVisible(true);
	}

	public void saveUserData(ActionEvent event) throws Exception
	{
		String changedUsername = txtUsername.getText();
		String changedPassword = txtNewPassword.getText();

		if(!UserDataManager.validateUserName(changedUsername))
		{
			System.out.println("Changed Username invalid!");
			changedUsername = UserDataManager.getCurrentUser().getName();
			txtUsername.setText(changedUsername);
		}
		//Send Data to UserDataManager
		else if(txtNewPassword.getText().equals(txtNewPasswordConfirmed.getText()))
		{
			if(!UserDataManager.saveUserDataChanges(txtUsername.getText(), txtNewPassword.getText()))
			{
				System.out.println("Username already taken");
				txtUsername.setText(UserDataManager.getCurrentUser().getName());
			}
		}
		else
		{
			System.out.println("Neue Passwörter stimmen nicht überein");
		}

		//Change GUI elements
		txtUsername.setDisable(true);
		btnSave.setVisible(false);
		boxNewPassword.setVisible(false);

		btnEdit.setStyle("-fx-background-color: #FFFFFF");
		btnEdit.setOpacity(0.07);
		btnEdit.setVisible(true);
	}

	public void showFilmProfile (ActionEvent event) throws Exception
	{
		paneProfile.getChildren().clear();

		int id = Integer.parseInt(((Button) event.getSource()).getText()); 
		FilmDataManager.setCurrentFilm(id);

		Parent fxml = FXMLLoader.load(getClass().getResource("FilmProfile.fxml"));
		paneProfile.getChildren().setAll(fxml);


		System.out.println(FilmDataManager.getFilm().getTitel());
	}

	public void showNextRentedFilms (ActionEvent event) throws Exception
	{
		if(((Button) event.getSource()).getText().equals("next"))
		{
			rentedFilmPage = rentedFilmPage + 1;
		}
		else
		{
			rentedFilmPage = rentedFilmPage - 1;
		}
		//System.out.println("RentedFilms - Seite: " + rentedFilmPage);

		showRentedFilms();

	}

	public void showRentedFilms()
	{
		//Show/Hide "Previous"-Button
		if(rentedFilmPage == 1)
		{
			btnRentedFilmsPrev.setVisible(false);
		}
		else
		{
			btnRentedFilmsPrev.setVisible(true);
		}

		//Save number of rented films and "leftovers"
		int numberRentedFilms = UserDataManager.getCurrentUser().getRentedFilms().size();
		int leftovers = UserDataManager.getCurrentUser().getRentedFilms().size()%4;
		
		System.out.println("Number of rented Films: " + numberRentedFilms);

		//If there are more films than could be displayed yet, show the next 4 films
		if(numberRentedFilms >= (rentedFilmPage * 4))
		{
			for(int i = 0; i < 4; i++)
			{
				int id = UserDataManager.getCurrentUser().getRentedFilms().get((UserDataManager.getCurrentUser().getRentedFilms().size() - 4*(rentedFilmPage - 1) -1 - i));
				rentedList.get(i).setText(String.valueOf(id));
				rentedList.get(i).setStyle("-fx-background-image: url('/images/"+ FilmDataManager.getFilmPerID(id).getThumbnail()+"'); -fx-text-fill: transparent; -fx-background-color: #121212");
				rentedList.get(i).setVisible(true);

				System.out.println(FilmDataManager.getFilmPerID(id).getTitel());
				btnRentedFilmsNext.setVisible(true);
			}
			
			if(numberRentedFilms == (rentedFilmPage * 4))
			{
				btnRentedFilmsNext.setVisible(false);
			}
		}
		//If there are not enough films to fill 4 slots, only show the leftovers
		else 
		{
			for(int i = 0; i < leftovers; i++)
			{
				int id = UserDataManager.getCurrentUser().getRentedFilms().get((UserDataManager.getCurrentUser().getRentedFilms().size() - 4*(rentedFilmPage - 1) -1 - i));
				rentedList.get(i).setText(String.valueOf(id));
				rentedList.get(i).setStyle("-fx-background-image: url('/images/"+ FilmDataManager.getFilmPerID(id).getThumbnail()+"'); -fx-text-fill: transparent; -fx-background-color: #121212");
				rentedList.get(i).setVisible(true);

				System.out.println(FilmDataManager.getFilmPerID(id).getTitel());
			}
			for(int i = leftovers; i < 4; i++)
			{
				rentedList.get(i).setVisible(false);
			}
			btnRentedFilmsNext.setVisible(false);
		}
	}

	
	//WATCH LIST
	public void showNextWatchList (ActionEvent event) throws Exception
	{
		if(((Button) event.getSource()).getText().equals("next"))
		{
			watchlistPage = watchlistPage + 1;
		}
		else
		{
			watchlistPage = watchlistPage - 1;
		}
		System.out.println("WatchList - Seite: " + watchlistPage);

		showWatchList();

	}
	
	public void showWatchList()
	{
		//Show/Hide "Previous"-Button
		if(watchlistPage == 1)
		{
			btnWatchlistPrev.setVisible(false);
		}
		else
		{
			btnWatchlistPrev.setVisible(true);
		}

		//Save number of rented films and "leftovers"
		int numberWatchListFilms = UserDataManager.getCurrentUser().getWatchList().size();
		int leftovers = numberWatchListFilms%4;

		//If there are more films than could be displayed yet, show the next 4 films
		if(numberWatchListFilms >= (watchlistPage * 4))
		{
			for(int i = 0; i < 4; i++)
			{
				int id = UserDataManager.getCurrentUser().getWatchList().get((UserDataManager.getCurrentUser().getWatchList().size() - 4*(watchlistPage - 1) -1 - i));
				watchList.get(i).setText(String.valueOf(id));
				watchList.get(i).setStyle("-fx-background-image: url('/images/"+ FilmDataManager.getFilmPerID(id).getThumbnail()+"'); -fx-text-fill: transparent; -fx-background-color: #121212");
				watchList.get(i).setVisible(true);
				
				btnWatchlistNext.setVisible(true);
			}
			
			if(numberWatchListFilms == (watchlistPage * 4))
			{
				btnWatchlistNext.setVisible(false);
			}
		}
		//If there are not enough films to fill 4 slots, only show the leftovers
		else 
		{
			for(int i = 0; i < leftovers; i++)
			{
				int id = UserDataManager.getCurrentUser().getWatchList().get((UserDataManager.getCurrentUser().getWatchList().size() - 4*(watchlistPage - 1) -1 - i));
				watchList.get(i).setText(String.valueOf(id));
				watchList.get(i).setStyle("-fx-background-image: url('/images/"+ FilmDataManager.getFilmPerID(id).getThumbnail()+"'); -fx-text-fill: transparent; -fx-background-color: #121212");
				watchList.get(i).setVisible(true);

				//System.out.println(FilmDataManager.getFilmPerID(id).getTitel());
			}
			for(int i = leftovers; i < 4; i++)
			{
				watchList.get(i).setVisible(false);
			}
			btnWatchlistNext.setVisible(false);
		}
	}


}
