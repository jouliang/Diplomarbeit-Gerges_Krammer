/**
 * 
 */
package app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import dao.DAO_Group;
import dao.DAO_Message;
import dao.DAO_User;
import data.Group;
import data.Message;
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
			DAO_Group dao = DAO_Group.getDaoGroup();
			
			ArrayList<String> members = new ArrayList<String>();
			members.add("Thomas");
			members.add("krammerg");
			int indexOfMemberInList = members.indexOf("Thomas");
			dao.updateGroupMember("Testgruppe", members, indexOfMemberInList, "Thomas", "Bernd"); 
			
			//DAO_Message daom = DAO_Message.getDaoMessage();
			// DAO_User dao = DAO_User.getDaoUser();

			/*
			 * dao.deleteUser("456");
			 * 
			 * HashSet<User> allUsers = dao.getAllUsers();
			 * 
			 * Thread.sleep(2000);
			 * 
			 * for (User u : allUsers) { System.out.println(u.getUsername() +
			 * u.getPassword() + u.isInitialLogin() + "\n"); }
			 */

			// DAO_Group daoG = DAO_Group.getDaoGroup();

			// dao.deleteUser("ga");

			// HashSet<User> allUsers = dao.getAllUsers();

			// daom.insertMessage("TestContent", "MyGroupname", "user", new Date());
			/*
			 * HashSet<Message> allMessages = new HashSet<Message>(); allMessages =
			 * daom.getAllMessages();
			 * 
			 * Thread.sleep(2000); for (Message m : allMessages) {
			 * System.out.println(m.getSender() + " " + m.getReceiver() + " " +
			 * m.getMessageContent() + " " + m.getTransmittedTime() + "\n"); }
			 */

			/*
			 * ArrayList<String> groupUsers = new ArrayList<String>() { { add("User 1");
			 * add("User 2"); add("Bearte"); } }; daoG.insertGroup("MyGroupName",
			 * groupUsers); Thread.sleep(2000); HashSet<Group> allGroups =
			 * daoG.getAllGroups();
			 * 
			 * System.out.println("Group"); for(Group g : allGroups) {
			 * System.out.println(g.getGroupName()); for(String mName : g.getGroupMembers())
			 * { System.out.println(mName); } }
			 * 
			 * System.out.println("User"); dao.insertUser("ga", "ga"); Thread.sleep(2000);
			 */
			/*
			 * HashSet<User> allUsers = dao.getAllUsers();
			 * 
			 * Thread.sleep(2000);
			 * 
			 * for (User u : allUsers) { System.out.println(u.getUsername() +
			 * u.getPassword() + u.isInitialLogin() + "\n"); }
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
