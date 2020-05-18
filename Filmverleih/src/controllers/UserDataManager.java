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

import models.UserData;

public class UserDataManager 
{	
	private Pattern pattern;
	private Matcher matcher;

	private static Pattern userNamePattern = Pattern.compile("^[a-zA-Z0-9_-]{3,14}$");

	private static List<UserData> oldUserList = new ArrayList<UserData>();
	
	public static void initializeUserList()
	{
		oldUserList = loadUser();
	}
	
	
	//Prüft, ob Registrierung möglich ist
	public static boolean manageUserRegistration(String name, String password, String passwordConfirmed)
	{
		boolean approvedRegistration = false;

		if(checkRegistrationConditions(name, password, passwordConfirmed))
		{
			approvedRegistration = true;

			//List<UserData> oldUserList = loadUser();
			addUser(name, password);
			saveUser(oldUserList);


			List<UserData> newUserList = loadUser();

			for(UserData e : newUserList)
				System.out.println(e.toString());
		}
		return approvedRegistration;
	}

	//Prüft, ob Login möglich ist über Kombination
	public static boolean manageLogin(String name, String password)
	{
		oldUserList = loadUser();
		boolean loginSuccessful = false;

		if(checkLoginDataCombination(name, password))
		{
			loginSuccessful = true;
		}

		return loginSuccessful;
	}

	public static void addUser(String name, String password)
	{
		oldUserList.add(new UserData(name, password));
	}

	public static void saveUser(List<UserData> user)
	{
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("user.save")))
		{
			for(UserData e : user)
				out.writeObject(e);
			System.out.println("Serialisierung erfolgreich!");
		}
		catch(Exception e)
		{
			System.out.println("Serialisierung nicht erfolgreich.");
		}
	}

	public static List<UserData> loadUser()
	{
		List<UserData> newUser = new ArrayList<UserData>();

		try(ObjectInputStream input = new ObjectInputStream(new FileInputStream("user.save")))
		{
			while(true)
			{
				newUser.add((UserData) input.readObject());
			}
		}
		catch(EOFException e)
		{
			System.out.println("Ende der Datei erreicht! Deserialisierung erfolgreich!");
		}
		catch(Exception e)
		{
			System.out.println("Laden fehlgeschlagen. Keine Datei gefunden.");
		}

		return newUser;
	}

	///Prüft, ob Registrierung zulässig ist. Es wird überprüft, ob die beiden eingegebenen Passwörter übereinstimmen,
	//ob Username zu lang und Sonderzeichen
	public static boolean checkRegistrationConditions(String name, String password, String passwordConfirmed)
	{
		boolean registrationPossible = false;

		//Sind Password und PasswordConfirmed gleich? Ist Username zulässig?
		if(!checkUserInList(name) && password.equals(passwordConfirmed) && validateUserName(name))
		{
			registrationPossible = true;
		}

		return registrationPossible;
	}

	//Quelle: https://www.javascan.com/226/validate-username-using-regular-expression-in-java
	//Prüft Username auf Sonderzeichen und Länge mit Hilfe von Regular Expressions
	public static boolean validateUserName(String userName)
	{
		Matcher mtch = userNamePattern.matcher(userName);
		boolean userNameIsValid;

		if (mtch.matches()) 
		{
			userNameIsValid = true;
		}
		else
		{
			//System.out.println("Username is not valid.");
			userNameIsValid = false;
		}

		return userNameIsValid;
	}

	//Prüft Username-Passwort-Kombination
	public static boolean checkLoginDataCombination(String name, String password)
	{
		boolean combinationCorrect = false;

		for(UserData u : oldUserList)
		{
			if(u.getName().equals(name) && u.getPasswort().equals(password))
			{
				System.out.println("Combination is correct!");
				combinationCorrect = true;
				break;
			}
		}
		
		return combinationCorrect;
	}
	
	public static boolean checkUserInList(String name)
	{
		boolean userExists = false;
		
		for(UserData u : oldUserList)
		{
			if(u.getName().equals(name))
			{
				userExists = true;
				System.out.println("User found in list!");
			}
		}
		
		return userExists;
		
	}

	public static void rentFilm(String username, int filmID)
	{
		for(UserData u : oldUserList)
		{
			if(u.getName().equals(username))
			{
				u.getRentedFilms().add(filmID);
				u.showRentedFilmList();
			}
		}
	}

}
