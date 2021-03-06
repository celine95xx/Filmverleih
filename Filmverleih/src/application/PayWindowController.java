package application;

import java.net.URL;

import java.util.ResourceBundle;
import controllers.FilmDataManager;
import controllers.UserDataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class PayWindowController implements Initializable {
	@FXML
	private AnchorPane panePayWindow;

	@FXML
	private Label lblTitle;

	@FXML
	private Label lblPrice;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private Line linePassword;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		panePayWindow.setStyle(
				"-fx-background-color: #121212; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 24, 0, 0, 0)");
		lblTitle.setText(FilmDataManager.getFilm().getTitel());
		lblPrice.setText(Double.toString(FilmDataManager.getFilm().getPreis()) + "�");

	}

	public void rentFilm(ActionEvent event) throws Exception {
		if (UserDataManager.checkLoginDataCombination(UserDataManager.getCurrentUser().getName(),
				txtPassword.getText())) {
			UserDataManager.rentFilm(FilmDataManager.getFilm().getId());
			FilmDataManager.addToRentAmount();

			System.out.println("Rented Amount of " + FilmDataManager.getFilm().getTitel() + ": "
					+ FilmDataManager.getFilm().getRentAmount());

			UserDataManager.getCurrentUser().showRentedFilms();

			Stage stage = (Stage) linePassword.getScene().getWindow();
			stage.close();
			;
		} else {
			linePassword.setStroke(Color.web("#DC1378"));
		}
	}
}
