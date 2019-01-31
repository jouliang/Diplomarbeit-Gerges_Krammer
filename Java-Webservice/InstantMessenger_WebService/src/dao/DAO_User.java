/**
 * 
 */
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
 * @author Joulian 
 * This class is the dao for the users
 */
public class DAO_User {
	private final String INTIAL_LOGIN = "initialLogin";
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
	 * @param username
	 * @param password
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void insertUser(String username, String password) throws InterruptedException, ExecutionException {
		Map<String, Object> user = new HashMap<>();
		user.put(this.INTIAL_LOGIN, false);
		user.put(this.PASSWORD, password);
		user.put(this.USERNAME, username);
		dbController.insert(this.USER_COLLECTION, user);
	}
	
	/**
	 * This method is getting all users of the cloud fire store database
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public HashSet<User> getAllUsers() throws InterruptedException, ExecutionException {
		QuerySnapshot allUsersFromFireStore = dbController.getCollection(this.USER_COLLECTION);
		HashSet<User> allUsers = new HashSet<User>();

		if (!allUsersFromFireStore.isEmpty()) {
			for (QueryDocumentSnapshot user : allUsersFromFireStore) {
				allUsers.add(new User(user.getString(this.USERNAME), user.getString(this.PASSWORD),
						user.getBoolean(this.INTIAL_LOGIN)));
			}
		} else {
			System.out.println("Ther are no users existing");
		}

		return allUsers;
	}

	/**
	 * @return the daoUser
	 * @throws IOException 
	 */
	public static DAO_User getDaoUser() throws IOException {
		if(DAO_User.daoUser == null) {
			DAO_User.daoUser = new DAO_User();
		}
		
		return daoUser;
	}
}
