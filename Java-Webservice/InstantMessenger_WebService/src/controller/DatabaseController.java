/**
 * 
 */
package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
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
		FileInputStream serviceAccount = new FileInputStream("/home/pupil/eclipse-workspace/InstantMessenger_WebService/src/controller/serviceAccountKey.json");
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
