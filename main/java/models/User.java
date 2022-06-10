package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User implements java.io.Serializable {

	/*
	 CREATE TABLE `users` (
 		`usr` varchar(255) NOT NULL,
 		`mail` varchar(255),
 		`pwd` varchar(255) NOT NULL,
 		`gender` varchar(255),
 		`birthDate` varchar(255) NOT NULL,
 		PRIMARY KEY (`usr`),
 		UNIQUE KEY `mail` (`mail`)
	 ); 
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private String user = "";
	private String mail = "";
	private String pwd1 = "";
	private String pwd2 = "";
	private String gender = "";
	private String birthDate = "";
	
	private boolean[] error  = {false,false,false,false,false};
	
	public User() {
		
	}
	
	public String getUser() {
		return this.user;
	}
	
	public void setUser(String user) {
		if(user.length() > 3) { 	 //TODO do comprovation if user exists in DB
			this.user = user;
		}else {
			error[0] = true;
		}
	}
	
	public String getMail() {
		return this.mail;
	}
	
	public void setMail(String mail) { 
		String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(mail);
		if (matcher.matches()) {
			this.mail = mail;
		} else {
			error[1]=true;
		}
		
	}
	
	public String getPwd1() {
		return this.pwd1;
	}
	
	public void setPwd1(String pwd1) {
		/* TODO check restriction with pattern */
		String passwdPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{6,}$";
		Pattern pattern = Pattern.compile(passwdPattern);
		Matcher matcher = pattern.matcher(pwd1);
		if(matcher.matches()) {
			this.pwd1 = pwd1;
		}else {
			error[2]=true;
		}
	}
	
	public String getPwd2() {
		return this.pwd2;
	}
	
	public void setPwd2(String pwd2) {
		/* TODO check restriction with pattern and check if pwd1=pwd2*/
		String passwdPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{6,}$";
		Pattern pattern = Pattern.compile(passwdPattern);
		Matcher matcher = pattern.matcher(pwd2);
		if(matcher.matches()) {
			if(this.pwd1.equals(pwd2)) {
				this.pwd2 = pwd2;
			}else {
				error[3] = true;
			}
		}else {
			error[3] = true;
		}
		
	}
	
	public String getGender() {
		return this.gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getBirthDate() {
		return this.birthDate;
	}
	
	public void setBirthDate(String birthDate) {
		/* TODO check restriction with pattern */
		LocalDate today = LocalDate.now();
		LocalDate birthDay = LocalDate.parse(birthDate);
		
		Period p = Period.between( birthDay , today );
		
		
		if(p.getYears() >= 18) {
			this.birthDate = birthDate;
		}
		else {
			error[4] = true;
		}
	}
	
	public boolean[] getError() {
		return error;
	}
	
	public void setError(int i) {
		error[i] = true;
	}
	
	/* Logic Functions */
	public boolean isComplete(User user) {
	    return(hasValue(user.getUser()) &&
	           hasValue(user.getBirthDate()) &&
	           hasValue(user.getPwd1()) &&
	           hasValue(user.getPwd2()));
	}
	
	public boolean hasValue(String val) {
		return((val != null) && (!val.equals("")));
	}
}
