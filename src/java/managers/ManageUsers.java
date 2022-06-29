package managers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

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
		String query = "SELECT users.userName, users.pwd FROM users WHERE users.userName = ?";
		System.out.println(user);
		ResultSet result = null;
		PreparedStatement statement= null;
		boolean correctPwd = false;
		try {
			statement = db.prepareStatement(query);
			statement.setString(1, user);
			
			result = statement.executeQuery();
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
		String query = "SELECT users.userName FROM users WHERE users.userName=?;";
		ResultSet result = null;
		PreparedStatement statement= null;
		boolean presentUser = false;
		try {
			statement = db.prepareStatement(query);
			statement.setString(1, user);
			result = statement.executeQuery();
			while(result.next()) {
				String dbUser = result.getString(1);
				presentUser = dbUser.equals(user);
			}
		}catch (SQLException e){
			System.out.println(e);
			return false;
		}
		
		return presentUser;
	}
	
	
	
	// Unfollow a user
	public void unfollowUser(Integer userID, Integer followedID) {
		String query = "DELETE FROM follows WHERE id = ? AND followedID = ?";
		PreparedStatement statement = null;
		try {
			statement = db.prepareStatement(query);
			statement.setInt(1,userID);
			statement.setInt(2,followedID);
			statement.executeUpdate();
			statement.close();
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public List<User> getNotFollowedUsers(Integer id, Integer start, Integer end) {
		 String query = "SELECT userID,userName FROM users WHERE userID NOT IN (SELECT userID FROM users,follows WHERE userID = followedID AND id = ?) AND userID <> ? ORDER BY userName LIMIT ?,?;";
		 PreparedStatement statement = null;
		 List<User> l = new ArrayList<User>();
		 try {
			 statement = db.prepareStatement(query);
			 statement.setInt(1,id);
			 statement.setInt(2, id);
			 statement.setInt(3,start);
			 statement.setInt(4,end);
			 ResultSet rs = statement.executeQuery();
			 while (rs.next()) {
				 User user = new User();
				 //user.setId(rs.getInt("id"));
				 user.setUser(rs.getString("userName"));
				 l.add(user);
			 }
			 rs.close();
			 statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return  l;
	}
	
	public List<User> getFollowedUsers(Integer id, Integer start, Integer end) {
		 String query = "SELECT userID,userName FROM users,following WHERE userID = followedID AND id = ? ORDER BY userName LIMIT ?,?;";
		 PreparedStatement statement = null;
		 List<User> l = new ArrayList<User>();
		 try {
			 statement = db.prepareStatement(query);
			 statement.setInt(1,id);
			 statement.setInt(2,start);
			 statement.setInt(3,end);
			 ResultSet rs = statement.executeQuery();
			 while (rs.next()) {
				 User user = new User();
				 //user.setId(rs.getInt("id"));
				 user.setUser(rs.getString("userName"));
				 l.add(user);
			 }
			 rs.close();
			 statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return  l;
	}
	
	
	
	
}
