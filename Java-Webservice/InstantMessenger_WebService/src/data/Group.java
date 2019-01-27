/**
 * 
 */
package data;

import java.util.HashSet;

/**
 * @author Joulian
 * This class represents a group of users
 */
public class Group {
	private String groupName = "";
	private HashSet<User> groupMembers = null;
	
	/**
	 * @param groupName
	 * @param groupMembers
	 */
	public Group(String groupName, HashSet<User> groupMembers) {
		super();
		this.groupName = groupName;
		this.groupMembers = groupMembers;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the groupMembers
	 */
	public HashSet<User> getGroupMembers() {
		return groupMembers;
	}

	/**
	 * @param groupMembers the groupMembers to set
	 */
	public void setGroupMembers(HashSet<User> groupMembers) {
		this.groupMembers = groupMembers;
	}
}
