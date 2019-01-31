/**
 * 
 */
package data;

import java.util.ArrayList;

/**
 * @author Joulian
 * This class represents a group of users
 */
public class Group {
	private String groupName = "";
	private ArrayList<String> groupMembers = null;
	
	/**
	 * @param groupName
	 * @param arrayList
	 */
	public Group(String groupName, ArrayList<String> arrayList) {
		super();
		this.groupName = groupName;
		this.groupMembers = arrayList;
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
	public ArrayList<String> getGroupMembers() {
		return groupMembers;
	}

	/**
	 * @param groupMembers the groupMembers to set
	 */
	public void setGroupMembers(ArrayList<String> groupMembers) {
		this.groupMembers = groupMembers;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupMembers == null) ? 0 : groupMembers.hashCode());
		result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Group)) {
			return false;
		}
		Group other = (Group) obj;
		if (groupMembers == null) {
			if (other.groupMembers != null) {
				return false;
			}
		} else if (!groupMembers.equals(other.groupMembers)) {
			return false;
		}
		if (groupName == null) {
			if (other.groupName != null) {
				return false;
			}
		} else if (!groupName.equals(other.groupName)) {
			return false;
		}
		return true;
	}
}
