/**
 * 
 */
package app;

import java.io.FileInputStream;
import java.util.concurrent.ExecutionException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import dao.DAO_User;

/**
 * @author Joulian This class is the web service
 */
public class InstantMessangerWS {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			FileInputStream serviceAccount = new FileInputStream("./serviceAccountKey.json");

			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://daims-75874.firebaseio.com").build();

			FirebaseApp.initializeApp(options);
			Firestore db = FirestoreClient.getFirestore();

			DAO_User dao = new DAO_User(db);
			
			dao.insertUser("joulian", "joulianPassword");
			dao.getAllUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
