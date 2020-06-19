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
	private double preis;
	private int fsk;
	private String thumbnail;
	private String banner;
	private String description;
	private int rentAmount;

	public FilmData(int id, String titel, String genre, double preis, int fsk, String thumbnail, String banner, String description) 
	{
		this.id = id;
		this.titel = titel;
		this.genre = genre;
		this.preis = preis;
		this.fsk = fsk;
		this.thumbnail = thumbnail;
		this.banner = banner;
		this.description = description;
	}
	
	
	public void setRentAmount(int amount)
	{
		this.rentAmount = amount;
	}
	
	public int getRentAmount()
	{
		return rentAmount;
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

	public double getPreis() {
		return preis;
	}

	public void setPreis(int preis) {
		this.preis = preis;
	}

	public int getFsk() {
		return fsk;
	}

	public void setFsk(int fsk) {
		this.fsk = fsk;
	}
	
	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @Override public String toString() { return " ID:" + this.id + " ---- Titel:"
	 *           + this.titel + " ---- Genre: " + this.genre + " ---- Preis: " +
	 *           this.preis+ " ---- Alter: " + this.alter; }
	 **/
	@Override
	public String toString() {
		return "Titel:" + this.titel + " Genre: " + this.genre +"--- Rent Amount: " + this.rentAmount;}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + ((titel == null) ? 0 : titel.hashCode());
		return result;
	}

}
