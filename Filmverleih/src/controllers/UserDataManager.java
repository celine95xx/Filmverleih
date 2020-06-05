package controllers;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
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
		if(!checkUserInList("admin"))
		{
			addUser("admin", "admin", 20);
		}
		saveUser(oldUserList);

		for(UserData e : oldUserList)
			System.out.println(e.toString());
	}
	
	
	//Pr�ft, ob Registrierung m�glich ist
	public static boolean manageUserRegistration(String name, String password, String passwordConfirmed, int age)
	{
		boolean approvedRegistration = false;

		if(checkRegistrationConditions(name, password, passwordConfirmed, age))
		{
			approvedRegistration = true;

			//List<UserData> oldUserList = loadUser();
			addUser(name, password,age);
			saveUser(oldUserList);


			List<UserData> newUserList = loadUser();

			for(UserData e : newUserList)
				System.out.println(e.toString());
		}
		else
		{
			System.out.println("Registration not possible");
		}
		return approvedRegistration;
	}

	//Pr�ft, ob Login m�glich ist �ber Kombination
	public static boolean manageLogin(String name, String password)
	{
		oldUserList = loadUser();
		boolean loginSuccessful = false;

		if(checkLoginDataCombination(name, password))
		{
			loginSuccessful = true;
		}
		
		List<UserData> newUserList = loadUser();

		for(UserData e : newUserList)
			System.out.println(e.toString());

		return loginSuccessful;
	}
	

	public static void addUser(String name, String password, int age)
	{
		oldUserList.add(new UserData(name, password, age));
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

	///Pr�ft, ob Registrierung zul�ssig ist. Es wird �berpr�ft, ob die beiden eingegebenen Passw�rter �bereinstimmen,
	//ob Username zu lang und Sonderzeichen
	public static boolean checkRegistrationConditions(String name, String password, String passwordConfirmed, int age)
	{
		boolean registrationPossible = false;

		//Sind Password und PasswordConfirmed gleich? Ist Username zul�ssig?
		if(!checkUserInList(name) && password.equals(passwordConfirmed) && validateUserName(name)) //GEBURTSTAG
		{
			registrationPossible = true;
			System.out.println("RegistrationPossible: true");
		}
		else
		{
			System.out.println("RegistrationPossible: false");
		}

		return registrationPossible;
	}

	//Quelle: https://www.javascan.com/226/validate-username-using-regular-expression-in-java
	//Pr�ft Username auf Sonderzeichen und L�nge mit Hilfe von Regular Expressions
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

	//Pr�ft Username-Passwort-Kombination
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
				break;
			}
			else
			{
				System.out.println("This is not the user you search for.");
			}
		}
		
		return userExists;
		
	}
	
	public static boolean checkAdminLogIn(String name, String password)
	{
		boolean isAdmin = false;
		if(name.equals("admin") && password.equals("admin")) //Sp�ter �ndern f�r andere Passw�rter
		{
			isAdmin = true;
		}
		
		return isAdmin;
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
