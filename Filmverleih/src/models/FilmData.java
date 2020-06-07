package models;

import java.io.Serializable;
import java.util.List;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.SelectionModel;

public class FilmData implements Serializable {
	private SimpleIntegerProperty id;
	private SimpleStringProperty titel;
	private SimpleStringProperty genre;
	private SimpleIntegerProperty preis;
	private SimpleBooleanProperty alter;

	public FilmData(int id, String titel, String genre, int preis, boolean alter) {
		this.id = new SimpleIntegerProperty(id);
		this.titel = new SimpleStringProperty(titel);
		this.genre = new SimpleStringProperty(genre);
		this.preis = new SimpleIntegerProperty(preis);
		this.alter = new SimpleBooleanProperty(alter);
	}

	public int getId() {
		return id.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public String getTitel() {
		return titel.get();
	}

	public void setTitel(String titel) {
		this.titel.set(titel);
	}

	public String getGenre() {
		return genre.get();
	}

	public void setGenre(String genre) {
		this.genre.set(genre);

	}

	public int getPreis() {
		return preis.get();
	}

	public void setPreis(int preis) {
		this.preis.set(preis);
	}

	public boolean getAlter() {
		return alter.get();
	}

	public void setAlter(boolean alter) {
		this.alter.set(alter);
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
