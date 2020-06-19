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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class WelcomePageController implements Initializable {

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

	@FXML
	private Button btnRecom1;

	@FXML
	private Button btnRecom2;

	@FXML
	private Button btnRecom3;

	@FXML
	private Button btnRecom4;

	@FXML
	private Button btnPopular1;

	@FXML
	private Button btnPopular2;

	@FXML
	private Button btnPopular3;

	@FXML
	private Button btnPopular4;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lblUsername.setText(UserDataManager.getCurrentUser().getName() + "!");

		showNewFilms();
		System.out.println(FilmDataManager.getRecommendedFilms().size());
		showRecommendedFilms();
		showPopularFilms();
	}

	public void showFilmProfile(ActionEvent event) throws Exception {
		paneWelcome.getChildren().clear();

		int id = Integer.parseInt(((Button) event.getSource()).getText());
		FilmDataManager.setCurrentFilm(id);

		Parent fxml = FXMLLoader.load(getClass().getResource("FilmProfile.fxml"));
		paneWelcome.getChildren().setAll(fxml);

		System.out.println(FilmDataManager.getFilm().getTitel());
	}

	public void showNewFilms() {
		// Number of all films
		int listsize = 0;
		if (FilmDataManager.getNewestFilms().size() < 4) {
			listsize = FilmDataManager.getNewestFilms().size();
		} else {
			listsize = 4;
		}

		List<Button> newFilmsButtonList = new ArrayList<Button>();
		newFilmsButtonList.add(btnNeuerscheinungen1);
		newFilmsButtonList.add(btnNeuerscheinungen2);
		newFilmsButtonList.add(btnNeuerscheinungen3);
		newFilmsButtonList.add(btnNeuerscheinungen4);

		for (int i = 0; i < listsize; i++) {
			String id = String.valueOf(FilmDataManager.getNewestFilms().get(i).getId());
			newFilmsButtonList.get(i).setText(id);
			newFilmsButtonList.get(i)
					.setStyle("-fx-background-image: url('/filmimages/"
							+ FilmDataManager.getNewestFilms().get(i).getThumbnail()
							+ "'); -fx-text-fill: transparent; -fx-background-color: #121212");
			newFilmsButtonList.get(i).setVisible(true);
		}
	}

	public void showRecommendedFilms() {
		int listsize = 0;
		if (FilmDataManager.getRecommendedFilms().size() < 4) {
			listsize = FilmDataManager.getRecommendedFilms().size();
		} else {
			listsize = 4;
		}

		List<Button> recomFilmList = new ArrayList<Button>();
		recomFilmList.add(btnRecom1);
		recomFilmList.add(btnRecom2);
		recomFilmList.add(btnRecom3);
		recomFilmList.add(btnRecom4);

		for (int i = 0; i < listsize; i++) {
			int id = (int) FilmDataManager.getRecommendedFilms().get(i);
			recomFilmList.get(i).setText(String.valueOf(id));
			recomFilmList.get(i).setStyle(
					"-fx-background-image: url('/filmimages/" + FilmDataManager.getFilmPerID(id).getThumbnail()
							+ "'); -fx-text-fill: transparent; -fx-background-color: #121212");
			recomFilmList.get(i).setVisible(true);
		}
		for (int i = listsize; i < 4; i++) {
			recomFilmList.get(i).setVisible(false);
		}
	}

	public void showPopularFilms() {
		System.out.println("WPC: showPopularFilms: " + FilmDataManager.getPopularFilms());

		int listsize = 0;
		if (FilmDataManager.getPopularFilms().size() < 4) {
			listsize = FilmDataManager.getPopularFilms().size();
		} else {
			listsize = 4;
		}

		List<Button> popularFilmButtonList = new ArrayList<Button>();
		popularFilmButtonList.add(btnPopular1);
		popularFilmButtonList.add(btnPopular2);
		popularFilmButtonList.add(btnPopular3);
		popularFilmButtonList.add(btnPopular4);

		for (int i = 0; i < listsize; i++) {
			int id = (int) FilmDataManager.getPopularFilms().get(i).getId();
			popularFilmButtonList.get(i).setText(String.valueOf(id));
			popularFilmButtonList.get(i).setStyle(
					"-fx-background-image: url('/filmimages/" + FilmDataManager.getFilmPerID(id).getThumbnail()
							+ "'); -fx-text-fill: transparent; -fx-background-color: #121212");
			popularFilmButtonList.get(i).setVisible(true);
		}
		for (int i = listsize; i < 4; i++) {
			popularFilmButtonList.get(i).setVisible(false);
		}
	}

}
