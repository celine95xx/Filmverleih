package controllers;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.FilmData;
import models.UserData;

public class SaveLoadManager {
	public static void saveFilm(List<FilmData> film) {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("film.save"))) {
			for (FilmData e : film)
				out.writeObject(e);
			System.out.println("Serialisierung erfolgreich!");
		} catch (Exception e) {
			System.out.println("Serialisierung nicht erfolgreich.");
			e.printStackTrace();
		}
	}

	public static ObservableList<FilmData> loadFilm() {
		ObservableList<FilmData> newFilm = FXCollections.observableArrayList();

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

	public static void saveRecommendations(List<Integer> recommendations) {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("recommendations.save"))) {
			for (Integer e : recommendations)
				out.writeObject(e);
			System.out.println("Serialisierung erfolgreich!");
		} catch (Exception e) {
			System.out.println("Serialisierung nicht erfolgreich.");
			e.printStackTrace();
		}
	}

	public static List<Integer> loadRecommendations() {
		List<Integer> newRecommendationList = new ArrayList<Integer>();

		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("recommendations.save"))) {
			while (true) {
				newRecommendationList.add((Integer) input.readObject());
			}
		} catch (EOFException e) {
			// System.out.println("Ende der Datei erreicht! Deserialisierung erfolgreich!");
		} catch (Exception e) {
			// System.out.println("Laden fehlgeschlagen. Keine Datei gefunden.");
		}

		return newRecommendationList;
	}

	public static void saveUser(List<UserData> user) {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("user1.save"))) {
			for (UserData e : user)
				out.writeObject(e);
			// System.out.println("Serialisierung erfolgreich!");
		} catch (Exception e) {
			// System.out.println("Serialisierung nicht erfolgreich.");
		}
	}

	public static List<UserData> loadUser() {
		List<UserData> newUser = new ArrayList<UserData>();

		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("user1.save"))) {
			while (true) {
				newUser.add((UserData) input.readObject());
			}
		} catch (EOFException e) {
			// System.out.println("Ende der Datei erreicht! Deserialisierung erfolgreich!");
		} catch (Exception e) {
			// System.out.println("Laden fehlgeschlagen. Keine Datei gefunden.");
		}

		return newUser;
	}
}
