/**
 * 
 */
package dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

import data.User;

/**
 * @author Joulian This class is the dao for the users
 */
public class DAO_User {
	private final Firestore db;

	/**
	 * @param db
	 */
	public DAO_User(Firestore db) {
		super();
		this.db = db;
	}
	
	public void insertUser(String username, String password) throws InterruptedException, ExecutionException {
		Map<String, Object> data = new HashMap<>();
		data.put("initialLogin", false);
		data.put("password", password);
		data.put("username", username);
		ApiFuture<DocumentReference> addedDocRef = db.collection("User").add(data);
		System.out.println("Added document with ID: " + addedDocRef.get().getId());
	}

	public void getAllUsers() throws InterruptedException, ExecutionException {
		CollectionReference collectionRef = db.collection("User");
		ApiFuture<QuerySnapshot> future = collectionRef.get();
		QuerySnapshot allUsersFromFireStore = future.get();
		HashSet<User> allUsers = new HashSet<User>();
		
		if(!allUsersFromFireStore.isEmpty()){
			for(QueryDocumentSnapshot user : allUsersFromFireStore) {
				allUsers.add(new User(user.getString("username"), user.getString("password"), user.getBoolean("initialLogin")));
				System.out.println(user.getString("username") + user.getString("password") + user.getBoolean("initialLogin"));
			}
		} else {
			System.out.println("Ther are no users existing");
		}
	}
}
