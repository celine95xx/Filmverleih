package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserData implements Serializable
{
	private String name;
	private String passwort;
	
	private List<Integer> rentedFilms = new ArrayList<Integer>();;
	private List<Integer> watchlist = new ArrayList<Integer>();;
	
	public UserData (String name, String passwort)
	{
		this.name = name;
		this.passwort = passwort;
	}
	
	public List<Integer> getRentedFilms()
	{
		return rentedFilms;
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
	


	@Override 
	public String toString()
	{
		return "Username: " + this.name + " ---- Passwort: " + this.passwort; 
	}
	
	public void showRentedFilmList()
	{
		System.out.println("Liste von User: " + this.name);
		for(Integer i : rentedFilms)
		{
			System.out.println(i);
		}
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
