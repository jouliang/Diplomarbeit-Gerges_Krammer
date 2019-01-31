/**
 * 
 */
package dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import controller.DatabaseController;
import data.Group;

/**
 * @author Joulian
 * This is the dao for the Group collection
 */
public class DAO_Group {
	private final String GROUP_NAME = "groupName";
	private final String GROUP_MEMBERS = "groupMembers";
	private final String GROUP_COLLECTION = "Group";
	
	private static DAO_Group daoGroup = null;
	
	private DatabaseController dbController = null;

	/**
	 * @param db
	 * @throws IOException 
	 */
	private DAO_Group() throws IOException {
		super();
		dbController = DatabaseController.getDbController();
	}

	/**
	 * This method is inserting a group to our cloud fire store database
	 * @param groupName
	 * @param groupMembers
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void insertGroup(String groupName, ArrayList<String> groupMembers) throws InterruptedException, ExecutionException {
		Map<String, Object> group = new HashMap<>();
		group.put(this.GROUP_MEMBERS, groupMembers);
		group.put(this.GROUP_NAME, groupName);
		dbController.insert(this.GROUP_COLLECTION, group);
	}
	
	/**
	 * This method is getting all groups of the cloud fire store databases
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@SuppressWarnings("unchecked")
	public HashSet<Group> getAllGroups() throws InterruptedException, ExecutionException {
		QuerySnapshot allGroupsFromFireStore = dbController.getCollection(this.GROUP_COLLECTION);
		HashSet<Group> allGroups = new HashSet<Group>();

		if (!allGroupsFromFireStore.isEmpty()) {
			for (QueryDocumentSnapshot group : allGroupsFromFireStore) {
				allGroups.add(new Group(group.getString(this.GROUP_NAME), (ArrayList<String>) group.get(this.GROUP_MEMBERS)));
			}
		} else {
			System.out.println("Ther are no groups existing");
		}

		return allGroups;
	}

	/**
	 * @return the daoGroup
	 * @throws IOException 
	 */
	public static DAO_Group getDaoGroup() throws IOException {
		if(DAO_Group.daoGroup == null) {
			DAO_Group.daoGroup = new DAO_Group();
		}
		return daoGroup;
	}
}
