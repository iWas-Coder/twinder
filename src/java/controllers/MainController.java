package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MainController
 */
@WebServlet("/MainController")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		RequestDispatcher dispatcher = null;
		
		if (session==null || session.getAttribute("user")==null) {
			System.out.println("MainController: NO active session has been found,");
			// request.setAttribute("menu","login_register.jsp");
			// request.setAttribute("content","login_register.jsp");
			dispatcher = request.getRequestDispatcher("index.jsp");
		}
		else {
			System.out.println("Main Controller: active session has been found for " + session.getAttribute("user"));
			request.setAttribute("user",session.getAttribute("user"));
			dispatcher = request.getRequestDispatcher("main.jsp");
		}
		
		
		dispatcher.forward(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

