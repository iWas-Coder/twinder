package controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import managers.ManageDb;


/**
 * Servlet implementation class TweetsController
 */
@WebServlet("/TweetsController")
public class TweetsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TweetsController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ManageDb db = new ManageDb();
		HttpSession session = request.getSession(false);
		if(session != null) {
			//only allow requests on open session
			//System.out.println("valid session");
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			JSONObject obj = new JSONObject();
			
			String typeOfRequest = (String)request.getParameter("type");
			String userName = (String)request.getParameter("name");
			
			switch(typeOfRequest) {
			case "getTimeline":
				int user = Integer.parseInt(request.getParameter("userID"));
				obj = db.getTweetsFromFollowing(user);
				break;
			case "getComments":
				String tweetID = (String)request.getParameter("id");
				obj = db.getCommentsFromTweetID(tweetID);
				break;
			case "getUsers":
				obj = db.getUsers(userName);
				break;
			case "publishTweet":
				String tweet = (String)request.getParameter("tweet");
				db.publishTweet(tweet);
				break;
			case "getUserId":
				String name = (String)request.getParameter("name");
				obj = db.getIdOfUser(userName);
				break;
			case "getFollowing":
				obj = db.getFollowing(userName);
				break;
			case "unfollowUser":
				int userID = Integer.parseInt(request.getParameter("userID"));
				int followedID = Integer.parseInt(request.getParameter("followedID"));
				db.unfollowUser(userID, followedID);
				break;
			case "followUser":
				userID = Integer.parseInt(request.getParameter("userID"));
				int toFollowID = Integer.parseInt(request.getParameter("toFollowID"));
				db.followUser(userID, toFollowID);
				break;
			case "getFollowers":
				userID = Integer.parseInt(request.getParameter("userID"));
				obj = db.getFollowers(userID);
				break;
			case "getMyTweets":
				userID = Integer.parseInt(request.getParameter("userID"));
				obj = db.getMyTweets(userID);
				break;
			case "deleteTweet":
				int tweetid = Integer.parseInt(request.getParameter("tweetID"));
				db.deleteTweet(tweetid);
				break;
			
			case "getUnfollowedUsers":
				int uid = Integer.parseInt(request.getParameter("userID"));
				obj = db.getUnfollowedUsers(uid);
				break;
			case "isAdmin":
				userID = Integer.parseInt(request.getParameter("userID")); 
				obj = db.isAdmin(userID);
				break;
			case "deleteUser":
				userID = Integer.parseInt(request.getParameter("userID")); 
				db.deleteUser(userID);
				break;
			case "likeTweet":
				tweetid = Integer.parseInt(request.getParameter("tweetID")); 
				db.likeTweet(tweetid);
				break;
			case "retweet":
				tweetid = Integer.parseInt(request.getParameter("tweetID")); 
				db.retweet(tweetid);
				break;
			}
			
			out.print(obj);
			out.flush();
			
			
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
