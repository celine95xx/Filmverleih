package controllers;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.UserData;

public class UserDataManager 
{	
	//	private Pattern pattern;
	//	private Matcher matcher;

	private static Pattern userNamePattern = Pattern.compile("^[a-zA-Z0-9_-]{3,14}$");

	private static List<UserData> oldUserList = new ArrayList<UserData>();

	private static String currentUserId;
	

	public static void initializeUserList()
	{
		oldUserList = loadUser();
		if(!checkUserInList("admin"))
		{
			addUser("admin", "admin", 20, true);
		}
		saveUser(oldUserList);

		for(UserData e : oldUserList)
			System.out.println(e.toString());

	}


	//Prüft, ob Registrierung möglich ist
	public static boolean manageUserRegistration(String name, String password, String passwordConfirmed, String dateOfBirth)
	{
		boolean approvedRegistration = false;

		if(checkRegistrationConditions(name, password, passwordConfirmed, calculateAge(dateOfBirth)))
		{
			approvedRegistration = true;

			addUser(name, password,calculateAge(dateOfBirth), false);
			currentUserId = getUserID(name);
			System.out.println(dateOfBirth);
			getCurrentUser().setDateOfBirth(dateOfBirth);
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

	//Prüft, ob Login möglich ist über Kombination
	public static boolean manageLogin(String name, String password)
	{
		oldUserList = loadUser();
		boolean loginSuccessful = false;

		if(checkLoginDataCombination(name, password))
		{
			loginSuccessful = true;
			currentUserId = getUserID(name);
			System.out.println(currentUserId);
		}



		List<UserData> newUserList = loadUser();

		for(UserData e : newUserList)
			System.out.println(e.toString());

		return loginSuccessful;
	}


	public static void addUser(String name, String password, int age, boolean isAdmin)
	{
		oldUserList.add(new UserData(name, password, age, isAdmin));
	}

	public static void saveUser(List<UserData> user)
	{
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("user1.save")))
		{
			for(UserData e : user)
				out.writeObject(e);
			//System.out.println("Serialisierung erfolgreich!");
		}
		catch(Exception e)
		{
			//System.out.println("Serialisierung nicht erfolgreich.");
		}
	}

	public static List<UserData> loadUser()
	{
		List<UserData> newUser = new ArrayList<UserData>();

		try(ObjectInputStream input = new ObjectInputStream(new FileInputStream("user1.save")))
		{
			while(true)
			{
				newUser.add((UserData) input.readObject());
			}
		}
		catch(EOFException e)
		{
			//System.out.println("Ende der Datei erreicht! Deserialisierung erfolgreich!");
		}
		catch(Exception e)
		{
			//System.out.println("Laden fehlgeschlagen. Keine Datei gefunden.");
		}

		return newUser;
	}

	///Prüft, ob Registrierung zulässig ist. Es wird überprüft, ob die beiden eingegebenen Passwörter übereinstimmen,
	//ob Username zu lang und Sonderzeichen
	public static boolean checkRegistrationConditions(String name, String password, String passwordConfirmed, int age)
	{
		boolean registrationPossible = false;

		//Sind Password und PasswordConfirmed gleich? Ist Username zulässig?
		if(!checkUserInList(name) && password.equals(passwordConfirmed) && validateUserName(name) && age >= 16) //GEBURTSTAG
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
		if(name.equals("admin") && password.equals("admin")) //Später ändern für andere Passwörter
		{
			isAdmin = true;
		}

		return isAdmin;
	}


	public static void rentFilm(int filmID)
	{
		getCurrentUser().addRentedFilm(filmID);
		saveUser(oldUserList);

		ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1); //https://stackoverflow.com/questions/10882611/how-to-make-a-delayed-non-blocking-function-call
		
		exec.schedule(new Runnable() {
			public void run() {
				FilmDataManager.deleteFilmFromRentedList(getCurrentUser(), filmID);
				System.out.println("Film (" + FilmDataManager.getFilmPerID(filmID).getTitel() + ") wurde aus der RentedFilmList entfernt");
				saveUser(oldUserList);
			}
		}, 10, TimeUnit.SECONDS);
		
		exec.shutdown();
		
	}

	public static boolean checkRentedFilm(int filmID)
	{
		boolean isAlreadyRented = false;

		for(Integer i : getCurrentUser().getRentedFilms())
		{
			if(i == filmID)
			{
				isAlreadyRented = true;
				break;
			}
		}

		return isAlreadyRented;
	}
	
	public static void addFilmToWatchList(int filmID)
	{
		getCurrentUser().addToWatchList(filmID);
		saveUser(oldUserList);
	}
	
	public static boolean checkWatchList(int filmID)
	{
		boolean isAlreadyBookmarked = false;

		for(Integer i : getCurrentUser().getWatchList())
		{
			if(i == filmID)
			{
				isAlreadyBookmarked = true;
				break;
			}
		}

		return isAlreadyBookmarked;
	}

	public static String getUserID(String username)
	{
		String userID = null;

		for(UserData u : oldUserList)
		{
			if(u.getName().equals(username))
			{
				userID = u.getId();
			}
		}
		return userID;
	}

	public static UserData getCurrentUser()
	{
		UserData user = null;
		for(UserData u : oldUserList)
		{
			if(u.getId().equals(currentUserId))
			{
				user = u;
			}
		}
		return user;
	}

	//https://stackoverflow.com/questions/21393717/calculating-age-with-current-date-and-birth-date-in-java
	public static int calculateAge(String dateOfBirth)
	{
		String[] parts = dateOfBirth.split("-");
		int year = Integer.parseInt(parts[0]);
		int month = Integer.parseInt(parts[1]);
		int day = Integer.parseInt(parts[2]);

		LocalDate birthday = LocalDate.of(year, month, day);
		int age = (int) birthday.until(LocalDate.now(), ChronoUnit.YEARS);
		System.out.println("Age:" + age);

		return age;
	}

	public static boolean saveUserDataChanges(String name, String password)
	{
		boolean dataSuccessfullyChanged = false;

		if(!checkUserInList(name))
		{
			getCurrentUser().setName(name);
			getCurrentUser().setPasswort(password);

			saveUser(oldUserList);

			dataSuccessfullyChanged = true;
		}

		return dataSuccessfullyChanged;
	}


}
