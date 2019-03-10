
package dao;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import controller.DatabaseController;
import data.User;

/**
 * 
 * @author Joulian 
 * This class is the data acces object for the user
 */
public class DAO_User {
	private final String USERNAME = "username";
	private final String PASSWORD = "password";
	private final String USER_COLLECTION = "User";

	private static DAO_User daoUser = null;

	private DatabaseController dbController = null;

	/**
	 * @param db
	 * @throws IOException
	 */
	private DAO_User() throws IOException {
		super();
		dbController = DatabaseController.getDbController();
	}

	/**
	 * This method is inserting a user to our cloud fire store database
	 * 
	 * @param username
	 * @param password
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void insertUser(String username, String password) throws InterruptedException, ExecutionException {
		Map<String, Object> user = new HashMap<>();
		user.put(this.PASSWORD, password);
		user.put(this.USERNAME, username);
		dbController.insert(this.USER_COLLECTION, user);
	}

	/**
	 * This method is getting all users of the cloud fire store database
	 * 
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public HashSet<User> getAllUsers() throws InterruptedException, ExecutionException {
		QuerySnapshot allUsersFromFireStore = dbController.getCollection(this.USER_COLLECTION);
		HashSet<User> allUsers = new HashSet<User>();

		if (!allUsersFromFireStore.isEmpty()) {
			for (QueryDocumentSnapshot user : allUsersFromFireStore) {
				allUsers.add(new User(user.getString(this.USERNAME), user.getString(this.PASSWORD)));
			}
		} else {
			System.out.println("There are no users existing");
		}

		return allUsers;
	}

	/**
	 * This method updates the username from an user collection
	 * 
	 * @param newUsername
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public void updateUsername(String oldUsername, String newUsername) throws InterruptedException, ExecutionException {
		this.dbController.updateOneField(this.USER_COLLECTION,
				this.dbController.getIdOfDocument(this.USER_COLLECTION, this.USERNAME, oldUsername), this.USERNAME,
				newUsername);
	}

	/**
	 * This method updates the password from an user collection
	 * 
	 * @param username
	 * @param newPassword
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void updatePassword(String username, String newPassword) throws InterruptedException, ExecutionException {
		this.dbController.updateOneField(this.USER_COLLECTION,
				this.dbController.getIdOfDocument(this.USER_COLLECTION, this.USERNAME, username), this.PASSWORD,
				newPassword);
	}

	/**
	 * This method deletes an user
	 * 
	 * @param username
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void deleteUser(String username) throws InterruptedException, ExecutionException {
		this.dbController.deleteDocument(this.USER_COLLECTION,
				this.dbController.getIdOfDocument(this.USER_COLLECTION, this.USERNAME, username));
	}

	/**
	 * @return the daoUser
	 * @throws IOException
	 */
	public static DAO_User getDaoUser() throws IOException {
		if (DAO_User.daoUser == null) {
			DAO_User.daoUser = new DAO_User();
		}

		return daoUser;
	}
}
