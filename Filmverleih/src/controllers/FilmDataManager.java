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

import models.FilmData;
import models.UserData;

public class FilmDataManager {
	private Pattern pattern;
	private Matcher matcher;

	private static Pattern filmNamePattern = Pattern.compile("^[A-Za-z0-9_-]{3,14}$");

	private static List<FilmData> oldFilmList = new ArrayList<FilmData>();

	public static void initializeFilmList() {
		oldFilmList = loadFilm();

	}

	public static void manageFilmRegistration(int id, String title, String genre, int price, boolean alter) {
		addFilm(id, title, genre, price, alter);
		saveFilm(oldFilmList);

		List<FilmData> newFilmList = loadFilm();
		for (FilmData f : newFilmList) {
			System.out.println(f.toString());
		}
	}

	public static void addFilm(int id, String titel, String genre, int preis, boolean alter) {
		oldFilmList.add(new FilmData(id, titel, genre, preis, alter));

	}

	public static void saveFilm(List<FilmData> film) {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("film.save"))) {
			for (FilmData e : film)
				out.writeObject(e);
			System.out.println("Serialisierung erfolgreich!");
		} catch (Exception e) {
			System.out.println("Serialisierung nicht erfolgreich.");
		}
	}

	public static List<FilmData> loadFilm() {
		List<FilmData> newFilm = new ArrayList<FilmData>();

		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("film.save"))) {
			while (true) {
				newFilm.add((FilmData) input.readObject());
			}
		} catch (EOFException e) {
			System.out.println("Ende der Datei erreicht! Deserialisierung erfolgreich!");
		} catch (Exception e) {
			System.out.println("Laden fehlgeschlagen. Keine Datei gefunden.");
		}

		return newFilm;
	}

	public static List<FilmData> getFilmList() {
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

	public static void deleteMovie(FilmData film) {
		oldFilmList.remove(film);
		saveFilm(oldFilmList);

	}

}