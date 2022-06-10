package controllers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import models.User;
import managers.ManageUsers;
/**
 * Servlet implementation class FormController
 */
@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	   System.out.print("RegisterController: ");
	   ManageUsers manager = new ManageUsers();
	   
	   try {
	
		   User user = new User();
		   BeanUtils.populate(user, request.getParameterMap());
		   
		   System.out.println("User: " + user.getUser());
		   System.out.println("mail: " + user.getMail());
		   System.out.println("pass: " + user.getPwd1());
		   System.out.println("gender: " + user.getGender());
		   System.out.println("birth date: " + user.getBirthDate());
		   
		   if (user.isComplete(user)) {
			   System.out.println(" user ok, forwarding to ViewLoginForm");
			   try {
				   if(user.hasValue(user.getMail()) && user.hasValue(user.getGender())) {
					   manager.addUser(user.getUser(), user.getMail(), user.getPwd1(), user.getGender(), user.getBirthDate());
				   }else if(user.hasValue(user.getMail())) {
					   manager.addUser(user.getUser(), user.getMail(), user.getPwd1(), "", user.getBirthDate());
				   }else if(user.hasValue(user.getGender())) {
					   manager.addUser(user.getUser(), "", user.getPwd1(), user.getGender(), user.getBirthDate());
				   }else {
						manager.addUser(user.getUser(), "", user.getPwd1(), "", user.getBirthDate());
				   }
				   manager.finalize();
				   RequestDispatcher dispatcher = request.getRequestDispatcher("login_register.jsp");
				   dispatcher.forward(request, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					user.setError(0);
					System.out.println("error creating user");
					System.out.println(" forwarding to ViewRegisterForm");
					request.setAttribute("user",user);
					RequestDispatcher dispatcher = request.getRequestDispatcher("login_register.jsp");
					dispatcher.forward(request, response);
				}
			   
		   
		   } 
		   else {
		
			   System.out.println(" forwarding to ViewRegisterForm");
			   request.setAttribute("user",user);
			   RequestDispatcher dispatcher = request.getRequestDispatcher("login_register.jsp");
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
