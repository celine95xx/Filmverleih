package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserData implements Serializable
{
	private String name;
	private String passwort;
	private int age;
	private String id;
	private boolean isAdmin;
	

	private List<Integer> rentedFilms = new ArrayList<Integer>();;
	private List<Integer> watchlist = new ArrayList<Integer>();;
	
	public UserData (String name, String passwort, int age, boolean isAdmin)
	{
		this.name = name;
		this.passwort = passwort;
		this.age = age;
		this.id = name+age+passwort;
		this.isAdmin = isAdmin;
	}
	
	public List<Integer> getRentedFilms()
	{
		return rentedFilms;
	}
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
