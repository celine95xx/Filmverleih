package controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.FilmData;

public class FilmDataManager {
	private Pattern pattern;
	private Matcher matcher;

	private static Pattern filmNamePattern = Pattern.compile("^[A-Za-z0-9_-]{3,14}$");

	private static ObservableList<FilmData> oldFilmList = FXCollections.observableArrayList();

	private static ObservableList<FilmData> currentFilmList;

	private static List<Integer> recommendedFilms = new ArrayList<Integer>();

	private static int currentFilmID;

	public static void initializeFilmList() {
		oldFilmList = SaveLoadManager.loadFilm();
		recommendedFilms = SaveLoadManager.loadRecommendations();

		checkAvailableRecommendations();

		currentFilmList = FXCollections.observableArrayList();
		currentFilmList.addAll(oldFilmList);

		for (FilmData f : oldFilmList) {
			System.out.println(f.toString());
		}

	}

	public static boolean manageFilmRegistration(int id, String title, String genre, double price, int fsk,
			String thumbnail, String banner, String description) {
		addFilm(id, title, genre, price, fsk, thumbnail, banner, description);
		SaveLoadManager.saveFilm(oldFilmList);

		List<FilmData> newFilmList = SaveLoadManager.loadFilm(); // 1111
		for (FilmData f : newFilmList) {
			System.out.println(f.toString());
		}
		return true;
	}

	public static void addFilm(int id, String titel, String genre, double preis, int fsk, String thumbnail,
			String banner, String description) {
		oldFilmList.add(new FilmData(id, titel, genre, preis, fsk, thumbnail, banner, description));
		updateCurrentFilmList();

	}

	public static ObservableList<FilmData> getFilmList() {
		return oldFilmList;
	}

	public static void sortFilmListByName() {
		updateCurrentFilmList();
		currentFilmList.sort((f1, f2) -> f2.getTitel().compareTo(f1.getTitel())); // Reverse Order
	}

	public static void deleteMovie(FilmData film) {
		oldFilmList.remove(film);
		updateCurrentFilmList();

		checkAvailableRecommendations();
		UserDataManager.checkUserFilmLists();

		SaveLoadManager.saveFilm(oldFilmList);

	}

	public static List<FilmData> getNewestFilms() {
		List<FilmData> newestFilms = new ArrayList<FilmData>();

		for (int i = 0; i < oldFilmList.size(); i++) {
			newestFilms.add(oldFilmList.get(oldFilmList.size() - 1 - i));
		}

		return newestFilms;
	}

	public static List<Integer> getRecommendedFilms() {
		return recommendedFilms;
	}

	public static void addRecommendedFilm(int filmID) {
		recommendedFilms.add(filmID);
		SaveLoadManager.saveRecommendations(recommendedFilms);
	}

	public static boolean checkFilmInRecommendations(int filmID) {
		boolean isRecommended = false;

		for (Integer i : recommendedFilms) {
			if (i == filmID) {
				isRecommended = true;
				break;
			}
		}

		return isRecommended;
	}

	public static void deleteFromRecommendation(int filmID) {
		recommendedFilms.removeIf(Integer -> Integer == filmID);
		SaveLoadManager.saveRecommendations(recommendedFilms);
	}

	public static FilmData getFilm() {
		FilmData currentFilm = null;

		for (FilmData f : oldFilmList) {
			if (f.getId() == currentFilmID) {
				currentFilm = f;
				break;
			} else {

			}
		}

		return currentFilm;
	}

	public static FilmData getFilmPerID(int id) {
		FilmData currentFilm = null;

		for (FilmData f : oldFilmList) {
			if (f.getId() == id) {
				currentFilm = f;
				break;
			} else {

			}
		}

		return currentFilm;
	}

	public static void setCurrentFilm(int id) {
		currentFilmID = id;
	}

	public static void addToRentAmount() {
		int currentRentAmount = getFilm().getRentAmount();
		getFilm().setRentAmount(getFilm().getRentAmount() + 1);
		SaveLoadManager.saveFilm(oldFilmList);
	}

	public static void checkAvailableRecommendations() {
		boolean recommendedFilmAvailable = false;
		List<Integer> notAvailableWatchlistFilms = new ArrayList<Integer>();

		if (FilmDataManager.getFilmList().size() == 0) {
			getRecommendedFilms().clear();
		} else {
			for (int i : getRecommendedFilms()) {
				for (FilmData film : FilmDataManager.getFilmList()) {
					if (film.getId() == i) {
						recommendedFilmAvailable = true;
						break;
					}
				}
				if (!recommendedFilmAvailable) {
					notAvailableWatchlistFilms.add(i);
				}

			}

			for (int i : notAvailableWatchlistFilms) {
				FilmDataManager.deleteFromRecommendation(i);
			}
		}

	}

	public static ObservableList<FilmData> getPopularFilms() {
		currentFilmList.sort(Comparator.comparing(FilmData::getRentAmount).reversed());
		return currentFilmList;
	}

	public static void updateCurrentFilmList() {
		currentFilmList.clear();
		currentFilmList.addAll(oldFilmList);
		System.out.println("FDM - updateCurrentFilmList - currentFilmList Size: " + currentFilmList.size());
		System.out.println("FDM - updateCurrentFilmList - oldFilmList Size: " + oldFilmList.size());
	}

	public static ObservableList<FilmData> getCurrentFilmList() {
		return currentFilmList;
	}

	public static void sortFilmListByGenre(String genre) {

		ObservableList<FilmData> temporaryList = FXCollections.observableArrayList();
		System.out.println("FDM - sortFilmListByGenre - OldFilmList Size:" + oldFilmList.size());
		for (FilmData film : oldFilmList) {
			System.out.println("FDM - sortFilmList Genre: " + film.toString());
			if (film.getGenre().equals(genre)) {
				temporaryList.add(film);
			}
		}

		currentFilmList = temporaryList;

	}
}