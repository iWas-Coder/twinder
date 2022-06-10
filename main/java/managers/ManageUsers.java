package managers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.User;
import utils.DB;

public class ManageUsers {
	
	private DB db = null ;
	
	public ManageUsers() {
		try {
			db = new DB();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void finalize() {
		try {
			db.disconnectBD();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
		
	// Add new user
	public void addUser(String name, String mail, String pwd, String gender, String date) throws Exception {
		String query = "INSERT INTO users (userID, userName, mail, pwd, gender, birthDay, isAdmin) VALUES (?,?,?,?,?,?,?)";
		PreparedStatement statement = null;
		try {
			statement = db.prepareStatement(query);
			statement.setString(1,"0"); //auto increment
			statement.setString(2,name); 
			if(hasValue(mail)) statement.setString(3,mail);
			else statement.setString(3,"");
			statement.setString(4,pwd);
			if(hasValue(gender)) statement.setString(5,gender);
			else statement.setString(5,"");
			statement.setString(6,date);
			statement.setString(7,"no");
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			
			throw new Exception("user already exists", e);
			
		}
	}
	
	/*Check if all the fields are filled correctly */
	public boolean isComplete(User user) {
	    return(hasValue(user.getUser()) &&
	    	   hasValue(user.getPwd1()) &&
	           hasValue(user.getPwd2()) &&
	           hasValue(user.getBirthDate()) );
	}
	
	private boolean hasValue(String val) {
		return((val != null) && (!val.equals("")));
	}
		
	
	// TODO: add other methods 
	
	public boolean isPwdInDB(String user, String pwd){
		String query = String.format("SELECT users.userName, users.pwd FROM users WHERE users.userName = \"%s\"", user);
		ResultSet result = null;
		PreparedStatement statement= null;
		boolean correctPwd = false;
		try {
			statement = db.prepareStatement(query);
			result = statement.executeQuery(query);
			while(result.next()) {
				String dbUser = result.getString(1);
				String dbPwd = result.getString(2);
				correctPwd = dbUser.equals(user) && dbPwd.equals(pwd);
			}
		}catch (SQLException e){
			return false;
		}
		return correctPwd;
	}

	public boolean userInDB(String user) {
		String query = String.format("SELECT users.userName FROM users WHERE users.userName = \"%s\"", user);
		ResultSet result = null;
		PreparedStatement statement= null;
		boolean presentUser = false;
		try {
			statement = db.prepareStatement(query);
			result = statement.executeQuery(query);
			while(result.next()) {
				String dbUser = result.getString(1);
				presentUser = dbUser.equals(user);
			}
		}catch (SQLException e){
			return false;
		}
		return presentUser;
	}
}
