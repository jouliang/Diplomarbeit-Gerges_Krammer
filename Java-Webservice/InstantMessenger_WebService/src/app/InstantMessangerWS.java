/**
 * 
 */
package app;

import java.util.ArrayList;
import java.util.HashSet;

import dao.DAO_Group;
import dao.DAO_User;
import data.Group;
import data.User;

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
			DAO_User dao = DAO_User.getDaoUser();
			DAO_Group daoG = DAO_Group.getDaoGroup();
			
			ArrayList<String> groupUsers = new ArrayList<String>() {
				{
					add("User 1");
					add("User 2");
					add("Bearte");
				}
			};
			daoG.insertGroup("MyGroupName", groupUsers);
			Thread.sleep(2000);
			HashSet<Group> allGroups = daoG.getAllGroups();
			
			System.out.println("Group");
			for(Group g : allGroups) {
				System.out.println(g.getGroupName());
				for(String mName : g.getGroupMembers()) {
					System.out.println(mName);
				}
			}
			
			System.out.println("User");
			dao.insertUser("ga", "ga");
			Thread.sleep(2000);
			HashSet<User> allUsers = dao.getAllUsers();
			
			for(User u : allUsers) {
				System.out.println(u.getUsername() + u.getPassword() + u.isInitialLogin() + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
