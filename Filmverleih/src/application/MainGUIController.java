package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import controllers.UserDataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MainGUIController implements Initializable {

	@FXML
	private Button btnControlPanel;

	@FXML
	private Label lblUsername;

	@FXML
	private ImageView imgControlPanel;

	@FXML
	private Pane contentArea;

	@FXML
	private Pane mainContent;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("INITIALIZE");

		Parent fxml = null;
		try {
			fxml = FXMLLoader.load(getClass().getResource("WelcomePage.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainContent.getChildren().removeAll();
		mainContent.getChildren().setAll(fxml);

		if (!UserDataManager.getCurrentUser().isAdmin()) {
			btnControlPanel.setVisible(false);
			imgControlPanel.setVisible(false);
		}

	}

	public void showAllMovies(ActionEvent event) throws Exception {
		System.out.println("SHOW ALL MOVIES");
		Parent fxml = FXMLLoader.load(getClass().getResource("AllFilms.fxml"));
		mainContent.getChildren().removeAll();
		mainContent.getChildren().setAll(fxml);

	}

	public void showProfile(ActionEvent event) throws Exception {
		Parent fxml = FXMLLoader.load(getClass().getResource("UserProfile.fxml"));
		mainContent.getChildren().removeAll();
		mainContent.getChildren().setAll(fxml);

	}

	public void showControlPanel(ActionEvent event) throws Exception {
		System.out.println("SHOW CONTROLPANEL");

		Parent fxml = FXMLLoader.load(getClass().getResource("Control.fxml"));
		mainContent.getChildren().removeAll();
		mainContent.getChildren().setAll(fxml);
	}

	public void showFilmProfile(ActionEvent event) throws Exception {
		System.out.println("SHOW FILMPROFILE from MainGUIController");

	}

	public void setContentArea() throws IOException {
		System.out.println("show filmprofile here");

		Parent fxml = FXMLLoader.load(getClass().getResource("FilmProfile.fxml"));
		mainContent.getChildren().removeAll();
		mainContent.getChildren().setAll(fxml);
	}

	public void showWelcomePage(ActionEvent event) throws Exception {

		System.out.println("Welcome Page!!");
		Parent fxml = FXMLLoader.load(getClass().getResource("WelcomePage.fxml"));
		mainContent.getChildren().removeAll();
		mainContent.getChildren().setAll(fxml);
	}

}
