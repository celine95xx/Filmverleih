package controllers;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import models.FilmData;
import models.UserData;

public class UserDataManager {
	// private Pattern pattern;
	// private Matcher matcher;

	private static Pattern userNamePattern = Pattern.compile("^[a-zA-Z0-9_-]{3,14}$");

	private static List<UserData> oldUserList = new ArrayList<UserData>();

	private static String currentUserId;

	public static void initializeUserList() {
		oldUserList = SaveLoadManager.loadUser();
		if(!checkUserInList("admin"))
		{
			addUser("admin", "admin", "23.05.1995", 20, true);
		}
		SaveLoadManager.saveUser(oldUserList);

		for (UserData e : oldUserList)
			System.out.println(e.toString());

	}

	// Pr�ft, ob Registrierung m�glich ist
	public static boolean manageUserRegistration(String name, String password, String passwordConfirmed,
			String dateOfBirth) {
		boolean approvedRegistration = false;

		if (checkRegistrationConditions(name, password, passwordConfirmed, calculateAge(dateOfBirth))) {
			approvedRegistration = true;

			addUser(name, password, dateOfBirth, calculateAge(dateOfBirth), false);
			currentUserId = getUserID(name);
			System.out.println(dateOfBirth);
			getCurrentUser().setDateOfBirth(dateOfBirth);
			SaveLoadManager.saveUser(oldUserList);

			List<UserData> newUserList = SaveLoadManager.loadUser();

			for (UserData e : newUserList)
				System.out.println(e.toString());
		} else {
			System.out.println("Registration not possible");
		}
		return approvedRegistration;
	}

	// Pr�ft, ob Login m�glich ist �ber Kombination
	public static boolean manageLogin(String name, String password) {
		oldUserList = SaveLoadManager.loadUser();
		boolean loginSuccessful = false;

		if (checkLoginDataCombination(name, password)) {
			currentUserId = getUserID(name);
			checkUserFilmLists();
			checkRemainingRentTime();
			loginSuccessful = true;
			System.out.println(currentUserId);
		}

		List<UserData> newUserList = SaveLoadManager.loadUser();

		for (UserData e : newUserList)
			System.out.println(e.toString());

		return loginSuccessful;
	}

	public static void addUser(String name, String password, String dateOfBirth, int age, boolean isAdmin) {
		oldUserList.add(new UserData(name, password, dateOfBirth, age, isAdmin));
	}

	/// Pr�ft, ob Registrierung zul�ssig ist. Es wird �berpr�ft, ob die beiden
	/// eingegebenen Passw�rter �bereinstimmen,
	// ob Username zu lang und Sonderzeichen
	public static boolean checkRegistrationConditions(String name, String password, String passwordConfirmed, int age) {
		boolean registrationPossible = false;

		// Sind Password und PasswordConfirmed gleich? Ist Username zul�ssig?
		if (!checkUserInList(name) && password.equals(passwordConfirmed) && validateUserName(name) && age >= 16) // GEBURTSTAG
		{
			registrationPossible = true;
			System.out.println("RegistrationPossible: true");
		} else {
			System.out.println("RegistrationPossible: false");
		}

		return registrationPossible;
	}

	// Quelle:
	// https://www.javascan.com/226/validate-username-using-regular-expression-in-java
	// Pr�ft Username auf Sonderzeichen und L�nge mit Hilfe von Regular Expressions
	public static boolean validateUserName(String userName) {
		Matcher mtch = userNamePattern.matcher(userName);
		boolean userNameIsValid;

		if (mtch.matches()) {
			userNameIsValid = true;
		} else {

			userNameIsValid = false;
		}

		return userNameIsValid;
	}

	// Pr�ft Username-Passwort-Kombination
	public static boolean checkLoginDataCombination(String name, String password) {
		boolean combinationCorrect = false;

		for (UserData u : oldUserList) {
			if (u.getName().equals(name) && u.getPasswort().equals(password)) {
				System.out.println("Combination is correct!");
				combinationCorrect = true;
				break;
			}
		}

		return combinationCorrect;
	}

	public static boolean checkUserInList(String name) {
		boolean userExists = false;

		for (UserData u : oldUserList) {
			if (u.getName().equals(name)) {
				userExists = true;
				System.out.println("User found in list!");
				break;
			} else {
				System.out.println("This is not the user you search for.");
			}
		}

		return userExists;

	}

	public static boolean checkAdminLogIn(String name, String password) {
		boolean isAdmin = false;
		if (name.equals("admin") && password.equals("admin")) // Sp�ter �ndern f�r andere Passw�rter
		{
			isAdmin = true;
		}

		return isAdmin;
	}

	public static void rentFilm(int filmID) {
		LocalDateTime rentTime = LocalDateTime.now();
		getCurrentUser().addRentedFilm(filmID);
		getCurrentUser().getRentTimes().put(filmID, rentTime.toString());

		SaveLoadManager.saveUser(oldUserList);

	}

	public static void checkRemainingRentTime() {
		LocalDateTime currentTime = LocalDateTime.now();
		List<Integer> expiredFilmIds = new ArrayList<Integer>();

		for (Map.Entry<Integer, String> entry : getCurrentUser().getRentTimes().entrySet()) {
			int id = entry.getKey();
			String stringRentTime = entry.getValue();
			LocalDateTime rentTime = LocalDateTime.parse(stringRentTime); // https://stackoverflow.com/questions/30788369/coverting-string-to-localtime-with-without-nanoofseconds
			Duration timeDifference = Duration.between(rentTime, currentTime); // https://mkyong.com/java8/java-8-difference-between-two-localdate-or-localdatetime/

			if (timeDifference.toMinutes() > 1) {
				expiredFilmIds.add(id);
			}
		}

		for (int i = 0; i < expiredFilmIds.size(); i++) {
			int id = expiredFilmIds.get(i);
			deleteFilmFromRentedList(id);
		}

	}

	public static void deleteFilmFromRentedList(int filmID) {
		System.out.println("UDM-deleteFilmFromRentedList: " + FilmDataManager.getFilmPerID(filmID).getTitel()
				+ " wurde aus der Rented Filmlist gel�scht.");
		getCurrentUser().getRentedFilms().remove(Integer.valueOf(filmID));
		getCurrentUser().getRentTimes().remove(filmID);
		getCurrentUser().showRentedFilms();
		System.out.println("RentList Size: " + getCurrentUser().getRentTimes().size());
	}

	public static boolean checkRentedFilm(int filmID) {
		boolean isAlreadyRented = false;

		// :::ArrayList-Version:::
		for (Integer i : getCurrentUser().getRentedFilms()) {
			if (i == filmID) {
				isAlreadyRented = true;
				break;
			}
		}
		return isAlreadyRented;
	}

	public static void addFilmToWatchList(int filmID) {
		getCurrentUser().addToWatchList(filmID);
		SaveLoadManager.saveUser(oldUserList);
	}

	public static boolean checkWatchList(int filmID) {
		boolean isAlreadyBookmarked = false;

		for (Integer i : getCurrentUser().getWatchList()) {
			if (i == filmID) {
				isAlreadyBookmarked = true;
				break;
			}
		}

		return isAlreadyBookmarked;
	}

	public static void deleteFilmFromWatchList(int filmID) {
		getCurrentUser().getWatchList().removeIf(Integer -> Integer == filmID);
		getCurrentUser().showWatchList();
	}

	public static String getUserID(String username) {
		String userID = null;

		for (UserData u : oldUserList) {
			if (u.getName().equals(username)) {
				userID = u.getId();
			}
		}
		return userID;
	}

	public static UserData getCurrentUser() {
		UserData user = null;
		for (UserData u : oldUserList) {
			if (u.getId().equals(currentUserId)) {
				user = u;
			}
		}
		return user;
	}

	//https://stackoverflow.com/questions/21393717/calculating-age-with-current-date-and-birth-date-in-java
	public static int calculateAge(String dateOfBirth)
	{
		String[] parts = dateOfBirth.split("\\.");
		System.out.println("UDM - calcAge - Parts Length: " +parts.length);
		int day = Integer.parseInt(parts[0]);
		int month = Integer.parseInt(parts[1]);
		int year = Integer.parseInt(parts[2]);


		LocalDate birthday = LocalDate.of(year, month, day);
		int age = (int) birthday.until(LocalDate.now(), ChronoUnit.YEARS);
		System.out.println("Age:" + age);

		return age;
	}

	public static boolean checkValidBirthday(String dateOfBirth)
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT);
			try
			{
				LocalDate date = LocalDate.parse(dateOfBirth, dtf);
				System.out.println("UDM - checkBirthday: Birthday valid");
				return true;
			}
			catch(DateTimeException dte)
			{
				//dte.printStackTrace();
				System.out.println("UDM - checkBirthday: Birthday invalid");
				return false;
			}



	}

	public static boolean checkIfAdult()
	{
		boolean isAdult = false;

		if(calculateAge(getCurrentUser().getDateOfBirth()) >= 18)
		{
			isAdult = true;
		}

		return isAdult;
	}

	public static boolean saveUserDataChanges(String name, String password) {
		boolean dataSuccessfullyChanged = false;

		if (!checkUserInList(name)) {
			getCurrentUser().setName(name);
			getCurrentUser().setPasswort(password);

			SaveLoadManager.saveUser(oldUserList);

			dataSuccessfullyChanged = true;
		}

		return dataSuccessfullyChanged;
	}

	public static void checkUserFilmLists() {
		// RENTED FILMS
		boolean rentedFilmAvailable = false;
		List<Integer> notAvailableRentedFilms = new ArrayList<Integer>();

		if (FilmDataManager.getFilmList().size() == 0 & getCurrentUser().getRentedFilms().size() != 0) {
			for (int i = 0; i < getCurrentUser().getRentedFilms().size(); i++) {
				getCurrentUser().getRentedFilms().clear(); /// ???
			}
		} else {
			// :::ArrayList-Version:::
			for (int i : getCurrentUser().getRentedFilms()) {
				for (FilmData film : FilmDataManager.getFilmList()) {
					if (film.getId() == i) {
						rentedFilmAvailable = true;
						break;
					}
				}
				if (!rentedFilmAvailable) {
					notAvailableRentedFilms.add(i);
				}

			}

			for (int i : notAvailableRentedFilms) {
				// FilmDataManager.deleteFilmFromRentedList(getCurrentUser(), i);
				deleteFilmFromRentedList(i);
			}
		}

		// WATCHLIST
		boolean watchlistFilmAvailable = false;
		List<Integer> notAvailableWatchlistFilms = new ArrayList<Integer>();

		if (FilmDataManager.getFilmList().size() == 0) {
			for (int i = 0; i < getCurrentUser().getWatchList().size(); i++) {
				deleteFilmFromWatchList(getCurrentUser().getWatchList().get(i));
			}
		} else {
			for (int i : getCurrentUser().getWatchList()) {
				for (FilmData film : FilmDataManager.getFilmList()) {
					if (film.getId() == i) {
						watchlistFilmAvailable = true;
						break;
					}
				}
				if (!watchlistFilmAvailable) {
					notAvailableWatchlistFilms.add(i);
				}

			}

			for (int i : notAvailableWatchlistFilms) {
				deleteFilmFromWatchList(i);
			}
		}

		// System.out.println("UDM - checkUserFilmLists - AllFilms Listsize: " +
		// FilmDataManager.getFilmList().size());
		// System.out.println("UDM - checkUserFilmLists - RentedFilms Listsize " +
		// getCurrentUser().getRentedFilms().size());
		// System.out.println("UDM - checkUserFilmLists - WatchList Listsize: " +
		// getCurrentUser().getWatchList().size());

		SaveLoadManager.saveUser(oldUserList);
		oldUserList = SaveLoadManager.loadUser();
	}

}
