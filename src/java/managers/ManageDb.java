package managers;

import utils.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import org.json.*;



public class ManageDb {
	private DB db = null ;
	
	public ManageDb() {
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
	
	public JSONObject getIdOfUser(String user) {
		
		String query = "SELECT u.userId FROM users AS u WHERE u.userName = ?";
		ResultSet result = null;
		PreparedStatement statement= null;
		JSONObject userID = new JSONObject();
		
		try {
			statement = db.prepareStatement(query);
			
			statement.setString(1, user);
			
			
			result = statement.executeQuery();
			
			
			while(result.next()) {
				int id = result.getInt(1);
				
				userID.put("userID", id);
				
			}
				
		}catch (SQLException e){
			System.out.println("Failed Requesting userID");
			System.out.println(e);
		}
		
		return userID;
	}
	
	public JSONObject getFollowing(String userN) {
		String query = "SELECT  f.followedID, f.followingDate , us.userName"
                + "    FROM users as u"
                + "    INNER JOIN following as f on  u.userID=f.id"
                + " INNER JOIN users as us ON us.userID=f.followedID"
                + "    WHERE u.userName = ? ";
        PreparedStatement statement= null;

        JSONObject users = new JSONObject();
        JSONArray usersArray = new JSONArray();

        try {
            statement = db.prepareStatement(query);
            statement.setString(1, userN);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                JSONObject user = new JSONObject();

                int userID = result.getInt(1);
                String followingDate = result.getString(2);
                String userName = result.getString(3);

                user.put("userID", userID);
                user.put("userName", userName);

                usersArray.put(user);
            }
            users.put("users", usersArray);
            result.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return users;
	}
	
	
	public JSONObject getTweetsFromFollowing(int userID) {
		String query = "SELECT u.userName, u.userID, tweet.content, tweet.tweetID, tweet.publishDate, tweet.lang, tweet.retweets, tweet.likes"
				+" FROM tweet INNER" 
				+" JOIN users as u"
				+" ON tweet.userID=u.userID"
				+" WHERE u.userID = ? OR tweet.parentID IS NULL and u.userID IN  ( SELECT  f.followedID"
				+" FROM following as f" 
				+" INNER JOIN users as u" 
				+" ON u.userID=f.id"
				+" WHERE u.userID=?)"
				+ " ORDER BY tweet.publishDate DESC;";
		
		ResultSet result = null;
		PreparedStatement statement= null;
		
		JSONObject tweetsFromFollowing = new JSONObject();
		JSONArray tweets = new JSONArray();
		
		try {
			statement = db.prepareStatement(query);
			
			statement.setInt(1, userID);
			statement.setInt(2, userID);
			
			
			result = statement.executeQuery();
			
			
			while(result.next()) {
				JSONObject tweet = new JSONObject();
				
				String userName = result.getString(1);
				int ID = result.getInt(2);
				String tweetContent = result.getString(3);
				String tweetID = result.getString(4);
				String publishDate = result.getString(5);
				String lang = result.getString(6);
				int retweets = result.getInt(7);
				int likes = result.getInt(8);

				
				tweet.put("user", userName);
				tweet.put("userID", ID);
				tweet.put("tweetContent", tweetContent);
				tweet.put("id", tweetID);
				tweet.put("publishDate", publishDate);
				tweet.put("lang", lang);
				tweet.put("retweets", retweets);
				tweet.put("likes", likes);
				
				tweets.put(tweet);
				
			}
			
			tweetsFromFollowing.put("tweets", tweets);
			
		}catch (SQLException e){
			System.out.println("Failed Requesting Tweets");
			System.out.println(e);
		}
		return tweetsFromFollowing;
	}
	
	public JSONObject getTweets() {
		String query = "SELECT u.userName, u.userID, tweet.content, tweet.tweetID, tweet.publishDate, tweet.lang, tweet.retweets, tweet.likes "
				+ "FROM tweet INNER "
				+ "JOIN users as u "
				+ "ON tweet.userID=u.userID "
				+ "WHERE tweet.parentID IS NULL"
				+ " ORDER BY tweet.publishDate DESC;";
		
		ResultSet result = null;
		PreparedStatement statement= null;
		
		JSONObject allTweets = new JSONObject();
		JSONArray tweets = new JSONArray();
		
		try {
			statement = db.prepareStatement(query);
			
			result = statement.executeQuery();
			
			
			while(result.next()) {
				JSONObject tweet = new JSONObject();
				
				String userName = result.getString(1);
				int ID = result.getInt(2);
				String tweetContent = result.getString(3);
				String tweetID = result.getString(4);
				String publishDate = result.getString(5);
				String lang = result.getString(6);
				int retweets = result.getInt(7);
				int likes = result.getInt(8);

				
				tweet.put("user", userName);
				tweet.put("userID", ID);
				tweet.put("tweetContent", tweetContent);
				tweet.put("id", tweetID);
				tweet.put("publishDate", publishDate);
				tweet.put("lang", lang);
				tweet.put("retweets", retweets);
				tweet.put("likes", likes);
				
				tweets.put(tweet);
			}
			
			allTweets.put("tweets", tweets);
			
		}catch (SQLException e){
			System.out.println("Failed Requesting all Tweets");
			System.out.println(e);
		}
		return allTweets;
	}
	
	//get personal tweets
	public JSONObject getMyTweets(int userID) {
		String query = "SELECT u.userName, tweet.content, tweet.tweetID, tweet.publishDate, tweet.lang, tweet.retweets, tweet.likes "
				+ "FROM tweet, users u "
				+ "WHERE tweet.userID = u.userID "
				+ "AND tweet.parentID IS NULL "
				+ "AND u.userID = ?"
				+ " ORDER BY tweet.publishDate DESC;";

		
		ResultSet result = null;
		PreparedStatement statement= null;
		
		JSONObject personalTweets = new JSONObject();
		JSONArray tweets = new JSONArray();
		
		try {
			statement = db.prepareStatement(query);
			
			statement.setInt(1, userID);

			result = statement.executeQuery();
			
			
			while(result.next()) {
				JSONObject tweet = new JSONObject();
				
				
				String userName = result.getString(1);
				String tweetContent = result.getString(2);
				String tweetID = result.getString(3);
				String publishDate = result.getString(4);
				String lang = result.getString(5);
				int retweets = result.getInt(6);
				int likes = result.getInt(7);

				
				tweet.put("user", userName);
				tweet.put("tweetContent", tweetContent);
				tweet.put("id", tweetID);
				tweet.put("publishDate", publishDate);
				tweet.put("lang", lang);
				tweet.put("retweets", retweets);
				tweet.put("likes", likes);
				
				tweets.put(tweet);
				
			}
			
			personalTweets.put("tweets", tweets);
			
		}catch (SQLException e){
			System.out.println("Failed Requesting personal Tweets");
			System.out.println(e);
		}
		System.out.println(personalTweets);
		return personalTweets;
	}
	
	
	public JSONObject getCommentsFromTweetID(String tweetID) {
		String query = "SELECT u.userName, tweet.content, tweet.tweetID, tweet.publishDate, tweet.lang, tweet.retweets, tweet.likes \n"
				+ "FROM tweet INNER "
				+ "JOIN users as u "
				+ "ON tweet.userID=u.userID "
				+ "WHERE tweet.parentID = ?"
				+ " ORDER BY tweet.publishDate DESC;";
		
		ResultSet result = null;
		PreparedStatement statement= null;
		
		JSONObject comments = new JSONObject();
		JSONArray commentsArr = new JSONArray();
		try {
			statement = db.prepareStatement(query);
			statement.setInt(1, Integer.valueOf(tweetID));
			
			result = statement.executeQuery();
			
			while(result.next()) {
				JSONObject tweet = new JSONObject();
				
				String userName = result.getString(1);
				String tweetContent = result.getString(2);
				String commentID = result.getString(3);
				String publishDate = result.getString(4);
				String lang = result.getString(5);
				int retweets = result.getInt(6);
				int likes = result.getInt(7);
				
				tweet.put("user", userName);
				tweet.put("tweetContent", tweetContent);
				tweet.put("id", commentID);
				tweet.put("publishDate", publishDate);
				tweet.put("lang", lang);
				tweet.put("retweets", retweets);
				tweet.put("likes", likes);
				
				
				commentsArr.put(tweet);
				
			}
			
		}catch(SQLException e){
			System.out.println("Failed Requesting comments");
			System.out.println(e);
		}
		comments.put("tweets", commentsArr);
		
		return comments;
	}
	
	public void publishTweet(String tweetStr) {
		String query = "INSERT INTO tweet (tweetID,userID,publishDate, parentID, content, lang, retweets,likes) VALUES (0,?,?,?,?,?,0,0)";
		
		PreparedStatement statement= null;
		JSONObject tweetJson = new JSONObject(tweetStr);
		JSONObject tweet = tweetJson.getJSONObject("tweet");
		
		int userID = tweet.getInt("userID");
		String lang = tweet.getString("lang");
		int parentID = tweet.getInt("parentID");
		String content = tweet.getString("content");
		
		System.out.println(tweet);
		try {
			//for the date
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date dt = new java.util.Date();
			String currentTime = sdf.format(dt);

			statement = db.prepareStatement(query);
			statement.setInt(1, userID);
			statement.setString(2, currentTime);
			if(parentID == -1) {
				statement.setNull(3, java.sql.Types.NULL);
			}else {
				statement.setInt(3, parentID);
			}
			
			statement.setString(4, content);
			statement.setString(5, lang);
			
			statement.executeUpdate();
		}
		catch(SQLException e){
			System.out.println("Failed publishing tweet");
			System.out.println(e);
		}
	}
	
	// Get all the users except the one logged
	public JSONObject getUsers(String userN) {
		 String query = "SELECT userID,userName FROM users WHERE users.userName != ? ORDER BY userName ASC;";
		 PreparedStatement statement = null;

		 JSONObject users = new JSONObject();
		 JSONArray usersArray = new JSONArray();
			
		 try {
			 statement = db.prepareStatement(query);
			 statement.setString(1, userN);

			 ResultSet result = statement.executeQuery();
			 while (result.next()) {
				 JSONObject user = new JSONObject();

				 String userID = result.getString(1);
				 String userName = result.getString(2);
				 
				 user.put("userID", userID);
				 user.put("userName", userName);
				 
				 usersArray.put(user);
			 }
			 users.put("users", usersArray);
			 result.close();
			 statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return users;
	}
	
	public JSONObject getUnfollowedUsers(int id) {
		String query = "SELECT userID,userName FROM users WHERE userID NOT IN (SELECT userID FROM users,following WHERE userID = followedID AND id = ?) AND userID <> ? ORDER BY userName;";
		 PreparedStatement statement = null;

		 JSONObject users = new JSONObject();
		 JSONArray usersArray = new JSONArray();
			
		 try {
			 statement = db.prepareStatement(query);
			 statement.setInt(1, id);
			 statement.setInt(2, id);

			 ResultSet result = statement.executeQuery();
			 while (result.next()) {
				 JSONObject user = new JSONObject();

				 String userID = result.getString(1);
				 String userName = result.getString(2);
				 
				 user.put("userID", userID);
				 user.put("userName", userName);
				 
				 usersArray.put(user);
			 }
			 users.put("users", usersArray);
			 result.close();
			 statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		System.out.println(users);
		return users;
	}
		
	
	// Follow a user
	public void followUser(Integer userID, Integer followedID) {
		String query = "INSERT INTO following (id, followedID,followingDate) VALUES (?,?,?)";
		PreparedStatement statement = null;
		try {
			statement = db.prepareStatement(query);
			statement.setInt(1,userID);
			statement.setInt(2,followedID);
			statement.setString(3, java.time.LocalDate.now().toString());
			statement.executeUpdate();
			statement.close();
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
		
	// Unfollow a user
	public void unfollowUser(Integer userID, Integer followedID) {
		String query = "DELETE FROM following WHERE id = ? AND followedID = ?";
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
	
	
	public JSONObject getFollowers(Integer userID) {
	 String query = "SELECT userID,userName FROM users,following WHERE userID = id AND followedID = ? ORDER BY userName;";
	 PreparedStatement statement = null;

	 JSONObject users = new JSONObject();
	 JSONArray usersArray = new JSONArray();
		
	 try {
		 statement = db.prepareStatement(query);
		 statement.setInt(1, userID);

		 ResultSet result = statement.executeQuery();
		 while (result.next()) {
			 JSONObject user = new JSONObject();

			 int followerID = result.getInt(1);
			 String userName = result.getString(2);
			 
			 user.put("userID", followerID);
			 user.put("userName", userName);
			 
			 usersArray.put(user);
		 }
		 users.put("users", usersArray);
		 result.close();
		 statement.close();
	} catch (SQLException e) {
		e.printStackTrace();
	} 
	return users;
}
	
	// Delete tweet
	public void deleteTweet(Integer tweetID) {
		String query = "DELETE FROM tweet WHERE tweetID = ?";
		PreparedStatement statement = null;
		try {
			statement = db.prepareStatement(query);
			statement.setInt(1,tweetID);
			statement.executeUpdate();
			statement.close();
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public JSONObject isAdmin(int userID) {
		String query = "SELECT u.userID FROM users AS u WHERE u.isAdmin = \"yes\"; " ;
		PreparedStatement statement = null;
		JSONObject obj = new JSONObject();
		boolean admin = false;
		try {
			statement = db.prepareStatement(query);
				
			
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				int adminID = result.getInt(1);
				if(adminID == userID) {
					admin = true;
				}
			}
			 
			
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		System.out.println(admin);
		obj.put("isAdmin", admin);
		System.out.println(obj);
		return obj;
	}
	
	public void deleteUser(int userID) {
        String query1 = "DELETE FROM tweet WHERE tweet.userID = ?;";
        String query2 = "DELETE FROM following WHERE id = ?;";
        String query3 = "DELETE FROM users WHERE userID = ?;";
        
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        PreparedStatement statement3 = null;
        try {
            statement1 = db.prepareStatement(query1);
            statement2 = db.prepareStatement(query2);
            statement3 = db.prepareStatement(query3);

            statement1.setInt(1,userID);
            statement1.executeUpdate();
            statement1.close();
            
            statement2.setInt(1,userID);
            statement2.executeUpdate();
            statement2.close();
            
            statement3.setInt(1,userID);
            statement3.executeUpdate();
            statement3.close();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 
	
	public void likeTweet(int tweetID) {
		String query = "UPDATE tweet SET likes = likes + 1 WHERE tweetID = ?;";
		PreparedStatement statement = null;
		try {
			statement = db.prepareStatement(query);
			statement.setInt(1,tweetID);
			statement.executeUpdate();
			statement.close();
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void retweet(int tweetID) {
		String query = "UPDATE tweet SET retweets = retweets + 1 WHERE tweetID = ?;";
		PreparedStatement statement = null;
		try {
			statement = db.prepareStatement(query);
			statement.setInt(1,tweetID);
			statement.executeUpdate();
			statement.close();
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
