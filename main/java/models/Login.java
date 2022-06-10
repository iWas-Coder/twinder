package models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import managers.ManageUsers;

public class Login {

	private String user = "";
	private String password = "";
	private ManageUsers manager;
	private int[] error = {0,0};
	
	public Login(ManageUsers manager) {
		this.manager = manager;
	}

	public String getUser(){
		return user;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setUser(String user){
		if(user.length() > 3 && manager.userInDB(user)) {   
			this.user = user;
			
		}else {
			error[0] = 1;
		}
	}
	
	public void setPassword(String password){
		String passwdPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{6,}$";
		Pattern pattern = Pattern.compile(passwdPattern);
		Matcher matcher = pattern.matcher(password);
		if(matcher.matches() && manager.isPwdInDB(this.user, password)) {							
			this.password = password;
			
		}else {
			error[1]= 1;
		}	
	}
	
	public int[] getError() {
		return error;
	}
	
	public boolean isComplete() {
	    return(hasValue(getUser()) &&
	    		hasValue(getPassword()));
	}
	
	
	private boolean hasValue(String val) {
		return((val != null) && (!val.equals("")));
	}
	
}