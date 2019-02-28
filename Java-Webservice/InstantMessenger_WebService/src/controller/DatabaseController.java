/**
 * 
 */
package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

/**
 * @author Joulian
 * This class is the controller of the database
 */
public class DatabaseController {
	private final Firestore db;
	private static DatabaseController dbController = null;


	/**
	 * @throws IOException 
	 */
	private DatabaseController() throws IOException {
		super();
		
		//FileInputStream serviceAccount = new FileInputStream("./serviceAccountKey.json");
		FileInputStream serviceAccount = new FileInputStream("/home/joulian/Desktop/Diplomarbeit-2018_19/Diplomarbeit-Gerges_Krammer/Java-Webservice/InstantMessenger_WebService/src/controller/serviceAccountKey.json");
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://daims-75874.firebaseio.com").build();
		//FirebaseApp.getInstance().delete();
		FirebaseApp.initializeApp(options);
		this.db = FirestoreClient.getFirestore();
	}
	
	/**
	 * This method is doing an insert to a cloud firestore database
	 * @param collectionName
	 * @param dataToInsert
	 * @return
	 */
	public ApiFuture<DocumentReference> insert(String collectionName, Map<String, Object> dataToInsert) {
		return db.collection(collectionName).add(dataToInsert);
	}
	
	/**
	 * This Get executes an select to a cloud fire store database and returns the values
	 * @param collectionName
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public QuerySnapshot getCollection(String collectionName) throws InterruptedException, ExecutionException {
		CollectionReference collectionRef = db.collection(collectionName);
		ApiFuture<QuerySnapshot> future = collectionRef.get();
		QuerySnapshot allUsersFromFireStore = future.get();
		
		return allUsersFromFireStore;
	}
	
	/**
	 * This method updates one field in an document
	 * @param collectionName
	 * @param documentId
	 * @param key
	 * @param value
	 */
	public void updateOneField(String collectionName, String documentId, String key, String value) {
		DocumentReference user = db.collection(collectionName).document(documentId);
		user.update(key, value);
	}
	
	public void deleteDocument(String collectionName, String documentName) {
		db.collection(collectionName).document(documentName).delete();
	}
	
	/**
	 * This method returns the id of an document
	 * @param collection
	 * @param uniqueField
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public String getIdOfDocument(String collection, String uniqueField) throws InterruptedException, ExecutionException {
		QuerySnapshot allUsersFromFireStore = dbController.getCollection(collection);
		String userId = "";
		
		if(!allUsersFromFireStore.isEmpty()) {
			for (QueryDocumentSnapshot user : allUsersFromFireStore) {
				if(user.getString(collection).equals(uniqueField)) {
					userId = user.getId();
					break;
				}
			}
		}
		
		return userId;
	}
	
	/**
	 * @return the dbController
	 * @throws IOException 
	 */
	public static DatabaseController getDbController() throws IOException {
		if(DatabaseController.dbController == null) {
			DatabaseController.dbController = new DatabaseController();
		}
		
		return dbController;
	}
}
