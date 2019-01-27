/**
 * 
 */
package data;

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
}
