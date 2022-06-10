package controllers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import managers.ManageUsers;
import models.Login;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("LoginController: ");
		ManageUsers manager = new ManageUsers();

		Login login = new Login(manager);
		
	    try {
			
	    	BeanUtils.populate(login, request.getParameterMap());
			
	    	if (login.isComplete()) {
		    	
	    		System.out.println("login OK, forwarding to ViewLoginDone ");
		    	HttpSession session = request.getSession();
		    	session.setAttribute("user",login.getUser());
		    	RequestDispatcher dispatcher = request.getRequestDispatcher("main.jsp");
			    dispatcher.forward(request, response);
			    
		    }else {
		     
				System.out.println("user not in db or bad pwd ");
			    request.setAttribute("login",login);
			    RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			    dispatcher.forward(request, response);
		    	
		    }
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	    
	}
		
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

