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
@WebServlet("/TweetsAnonController")
public class TweetsAnonController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TweetsAnonController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ManageDb db = new ManageDb();
		//only allow requests on open session
		//System.out.println("valid session");
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		JSONObject obj = new JSONObject();
		
		String typeOfRequest = (String)request.getParameter("type");
		
		switch(typeOfRequest) {
		
		case "getTimelineAnon":
			obj = db.getTweets();
			break;
		case "getUsers":
			obj = db.getUsers("");  //will get all the users
			break;
		case "getUserProfile":
			int userID = Integer.parseInt(request.getParameter("userID"));
			obj = db.getMyTweets(userID);
			break;
		case "getComments":
			String tweetID = (String)request.getParameter("id");
			obj = db.getCommentsFromTweetID(tweetID);
			break;
		}
		out.print(obj);
		out.flush();
	}
		
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}

