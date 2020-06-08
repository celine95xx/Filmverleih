package models;

import java.io.Serializable;
import java.util.List;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.SelectionModel;

public class FilmData implements Serializable {
	private int id;
	private String titel;
	private String genre;
	private int preis;
	private boolean alter;

	public FilmData(int id, String titel, String genre, int preis, boolean alter) {
		this.id = id;
		this.titel = titel;
		this.genre = genre;
		this.preis = preis;
		this.alter = alter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;

	}

	public int getPreis() {
		return preis;
	}

	public void setPreis(int preis) {
		this.preis = preis;
	}

	public boolean getAlter() {
		return alter;
	}

	public void setAlter(boolean alter) {
		this.alter = alter;
	}

	/**
	 * @Override public String toString() { return " ID:" + this.id + " ---- Titel:"
	 *           + this.titel + " ---- Genre: " + this.genre + " ---- Preis: " +
	 *           this.preis+ " ---- Alter: " + this.alter; }
	 **/
	@Override
	public String toString() {
		return "Titel:" + this.titel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + ((titel == null) ? 0 : titel.hashCode());
		return result;
	}

}
