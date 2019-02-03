/**
 * 
 */
package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import java.util.List;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;


/**
 * @author Joulian
 * This class represents a user
 */
public class User {
	private String username = "";
	private String password = "";
	private boolean initialLogin = false;
	
	/**
	 * @param username
	 * @param password
	 * @param initialLogin
	 */
	public User(String username, String password, boolean initialLogin) {
		super();
		this.username = username;
		this.password = password;
		this.initialLogin = initialLogin;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the initialLogin
	 */
	public boolean isInitialLogin() {
		return initialLogin;
	}

	/**
	 * @param initialLogin the initialLogin to set
	 */
	public void setInitialLogin(boolean initialLogin) {
		this.initialLogin = initialLogin;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (initialLogin ? 1231 : 1237);
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (initialLogin != other.initialLogin) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}
	

    @Override
    public String toString() {
      return "username:" + username +  "password:" + password + "initialLogin:" + initialLogin;
    }
	
}
