package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import controllers.FilmDataManager;

public class UserData implements Serializable
{
	private String name;
	private String passwort;
	private int age;
	private String dateOfBirth;
	private String id;
	private boolean isAdmin;


	private ArrayList<Integer> rentedFilms = new ArrayList<Integer>();
	private ArrayList<Integer> watchlist = new ArrayList<Integer>();


	//Konstruktor
	public UserData (String name, String passwort, int age, boolean isAdmin)
	{
		this.name = name;
		this.passwort = passwort;
		this.age = age;
		this.id = name+age+passwort;
		this.isAdmin = isAdmin;

	}

	//Ausgeliehene Filme - Liste
	public ArrayList<Integer> getRentedFilms()
	{
		return rentedFilms;
	}

	public void showRentedFilms()
	{
		System.out.println("Ausgeliehene Filme: ");

		for(int i = 0; i < rentedFilms.size(); i++)
		{
			System.out.println("ID: " + rentedFilms.get(i) + " --- " + FilmDataManager.getFilmPerID(rentedFilms.get(i)));
		}
	}

	public void addRentedFilm(int id)
	{
		rentedFilms.add(id);
	}

	
	//WatchList
	public ArrayList<Integer> getWatchList()
	{
		return watchlist;
	}

	public void showWatchList()
	{
		System.out.println("Vorgemerkte Filme: ");

		for(int i = 0; i < watchlist.size(); i++)
		{
			System.out.println("ID: " + watchlist.get(i) + " --- " + FilmDataManager.getFilmPerID(watchlist.get(i)));
		}
	}

	public void addToWatchList(int id)
	{
		watchlist.add(id);
	}


	//Getter und Setter
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public String getId() {
		return id;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public String getDateOfBirth() 
	{
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) 
	{
		this.dateOfBirth = dateOfBirth;
	}



	@Override 
	public String toString()
	{
		return "Username: " + this.name + " ---- Passwort: " + this.passwort + " ----- Geburtstag: " + this.dateOfBirth + " ------ Alter: " + this.age; 
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((passwort == null) ? 0 : passwort.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserData other = (UserData) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
