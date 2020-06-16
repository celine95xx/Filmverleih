package controllers;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.FilmData;
import models.UserData;

public class FilmDataManager {
	private Pattern pattern;
	private Matcher matcher;

	private static Pattern filmNamePattern = Pattern.compile("^[A-Za-z0-9_-]{3,14}$");

	private static ObservableList<FilmData> oldFilmList = FXCollections.observableArrayList();

	private static List<Integer> recommendedFilms = new ArrayList<Integer>();

	private static int currentFilmID;

	public static void initializeFilmList() 
	{
		oldFilmList = SaveLoadManager.loadFilm();
		recommendedFilms = SaveLoadManager.loadRecommendations();

		checkAvailableRecommendations();

		for (FilmData f : oldFilmList) 
		{
			System.out.println(f.toString());
		}

	}

	public static boolean manageFilmRegistration(int id, String title, String genre, int price, boolean alter, String thumbnail, String banner, String description ) {
		addFilm(id, title, genre, price, alter, thumbnail, banner, description);
		SaveLoadManager.saveFilm(oldFilmList);

		List<FilmData> newFilmList = SaveLoadManager.loadFilm(); // 1111
		for (FilmData f : newFilmList) {
			System.out.println(f.toString());
		}
		return true;
	}

	public static void addFilm(int id, String titel, String genre, int preis, boolean alter, String thumbnail, String banner, String description) 
	{
		oldFilmList.add(new FilmData(id, titel, genre, preis, alter, thumbnail, banner, description));

	}

	//	public static void saveFilm(List<FilmData> film) {
	//		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("film.save"))) {
	//			for (FilmData e : film)
	//				out.writeObject(e);
	//			System.out.println("Serialisierung erfolgreich!");
	//		} catch (Exception e) {
	//			System.out.println("Serialisierung nicht erfolgreich.");
	//			e.printStackTrace();
	//		}
	//	}
	//
	//	public static ObservableList<FilmData> loadFilm() {
	//		ObservableList<FilmData> newFilm = FXCollections.observableArrayList();
	//
	//		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("film.save"))) {
	//			while (true) {
	//				newFilm.add((FilmData) input.readObject());
	//			}
	//		} catch (EOFException e) {
	//			System.out.println("Ende der Datei erreicht! Deserialisierung erfolgreich!");
	//		} catch (Exception e) {
	//			System.out.println("Laden fehlgeschlagen. Keine Datei gefunden.");
	//		}
	//
	//		return newFilm;
	//	}

	public static ObservableList<FilmData> getFilmList() {
		return oldFilmList;
	}

	public static void sortFilmListByName() {
		oldFilmList.sort((f1, f2) -> f1.getTitel().compareTo(f2.getTitel()));
	}

	public static List<FilmData> filterFilmListGenre(String genre) {
		List<FilmData> tempList = oldFilmList;
		List<FilmData> result = tempList.stream().filter(f1 -> f1.getGenre().equals(genre))
				.collect(Collectors.toList());
		return result;
	}

	public static void deleteMovie(FilmData film) 
	{
		oldFilmList.remove(film);
		
		checkAvailableRecommendations();
		UserDataManager.checkUserFilmLists();
		
		SaveLoadManager.saveFilm(oldFilmList);
		

	}

	public static List<FilmData> getNewestFilms()
	{
		List<FilmData> newestFilms = new ArrayList<FilmData>();

		for(int i = 0; i < oldFilmList.size(); i++)
		{
			newestFilms.add(oldFilmList.get(oldFilmList.size() - 1 - i));
		}

		return newestFilms;
	}

	public static List<Integer> getRecommendedFilms()
	{
		return recommendedFilms;
	}

	public static void addRecommendedFilm(int filmID)
	{
		recommendedFilms.add(filmID);
		SaveLoadManager.saveRecommendations(recommendedFilms);
	}

	public static boolean checkFilmInRecommendations(int filmID)
	{
		boolean isRecommended = false;

		for(Integer i : recommendedFilms)
		{
			if(i == filmID)
			{
				isRecommended = true;
				break;
			}
		}

		return isRecommended;
	}

	public static void deleteFromRecommendation(int filmID)
	{
		recommendedFilms.removeIf(Integer -> Integer == filmID);
		SaveLoadManager.saveRecommendations(recommendedFilms);
	}

	public static FilmData getFilm()
	{
		FilmData currentFilm = null;

		for(FilmData f : oldFilmList)
		{
			if(f.getId() == currentFilmID)
			{
				currentFilm = f;
				break;
			}
			else
			{
				//System.out.println("Method getFilm: Kein Film mit dieser ID vorhanden");
			}
		}

		return currentFilm;
	}

	public static FilmData getFilmPerID(int id)
	{
		FilmData currentFilm = null;

		for(FilmData f : oldFilmList)
		{
			if(f.getId() == id)
			{
				currentFilm = f;
				break;
			}
			else
			{
				//System.out.println("Method getFilm: Kein Film mit dieser ID vorhanden");
			}
		}

		return currentFilm;
	}

	public static void setCurrentFilm (int id)
	{
		currentFilmID = id;
	}

	public static void deleteFilmFromRentedList(UserData currentUser, int filmID)
	{
		currentUser.getRentedFilms().removeIf(Integer -> Integer == filmID);
		currentUser.showRentedFilms();
	}

	public static void deleteFilmFromWatchList(UserData currentUser, int filmID)
	{
		currentUser.getWatchList().removeIf(Integer -> Integer == filmID);
		currentUser.showWatchList();
	}

	public static void addToRentAmount()
	{
		getFilm().addToRentAmount();
		SaveLoadManager.saveFilm(oldFilmList);
	}

	public static void checkAvailableRecommendations()
	{
		boolean recommendedFilmAvailable = false;
		List<Integer> notAvailableWatchlistFilms = new ArrayList<Integer>();

		if(FilmDataManager.getFilmList().size() == 0)
		{
			for(int i : getRecommendedFilms())
			{
				FilmDataManager.deleteFromRecommendation(i);
			}
		}
		else
		{
			for(int i : getRecommendedFilms())
			{
				for(FilmData film : FilmDataManager.getFilmList())
				{
					if(film.getId() == i)
					{
						recommendedFilmAvailable = true;
						break;
					}
				}
				if(!recommendedFilmAvailable)
				{
					notAvailableWatchlistFilms.add(i);
				}

			}

			for(int i : notAvailableWatchlistFilms)
			{
				FilmDataManager.deleteFromRecommendation(i);
			}
		}
	}
}