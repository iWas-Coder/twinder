<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- ===== Iconscout CSS ===== -->
    <link rel="stylesheet" href="https://unicons.iconscout.com/release/v4.0.0/css/line.css">
    <!-- ===== CSS ===== -->
    <link rel="stylesheet" href="css/login_register.css">
    <title> Twinder </title>
    <!-- Loading the Favicon -->
    <link rel="icon" href="img/favicon.ico">
</head>
<body>

<div class="container">
  <div class="forms">
    <!-- Login Form -->
    <div class="form login">
      <span class="title">Login</span>

      <form method="post" action="LoginController">
        <div class="input-field">
          <!-- Username -->
          <input type="text" placeholder="Username" name="user" value="${login.user}" required minlength="3">
          <i class="uil uil-user icon"></i>
        </div>
        <div class="input-field">
          <!-- Password -->
          <input type="password" class="password" placeholder="Password" name="password" value="${login.password}" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{6,}$" required>
          <i class="uil uil-lock icon"></i>
          <i class="uil uil-eye-slash showHidePw"></i>
        </div>
        <div class="input-field button">
          <!-- Login button -->
          <input type="submit" id="button" name="sumbit" value="Submit">
        </div>
      </form>

      <div class="login-signup">
        <!-- Link to Registration form -->
        <span class="text">Not registered yet?
          <a href="#" class="text signup-link">Register now</a>
        </span>
      </div>
    </div>

    <!-- Registration Form -->
    <div class="form signup">
      <span class="title">Register now</span>

      <form method="post" action="RegisterController">
        <div class="input-field">
          <!-- Username -->
          <input type="text" placeholder="Username" id="user" name="user" value="${user.user}" required minlength="3">
          <i class="uil uil-user icon"></i>
        </div>
        <div class="input-field">
          <!-- Email (optional) -->
          <input type="text" placeholder="Email (optional)" id="mail" name="mail" value = "${user.mail}">
          <i class="uil uil-envelope icon"></i>
        </div>
        <div class="input-field">
          <!-- Create password -->
          <input type="password" class="password" placeholder="Create Password" id="create_pw" name="pwd1" value="${user.pwd1}" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{6,}$" required>
          <i class="uil uil-lock icon"></i>
          <i class="uil uil-eye-slash showHidePw" id="createPw_showHidePw"></i>
        </div>
        <div class="indicator">
          <div class="icon-text">
            <i class="uil uil-exclamation-circle error_icon"></i>
            <span class="strength-text"></span>
          </div>
        </div>
        <div class="input-field">
          <!-- Confirm password -->
          <input type="password" class="password" placeholder="Confirm Password" id="confirm_pw" name="pwd2" value="${user.pwd2}"  required disabled>
          <i class="uil uil-lock icon"></i>
          <i class="uil uil-eye-slash showHidePw"></i>
        </div>
        <div class="alert">
          <i class="uil uil-exclamation-circle alert-error"></i>
          <span class="alert-text"></span>
        </div>
        <div class="input-field">
          <!-- Gender selector -->
          <select name="gender">
            <option value="default" value="${user.gender}">Gender (optional)</option>
            <option value="male" value="${user.gender}">Male</option>
            <option value="female" value="${user.gender}">Female</option>
            <option value="other" value="${user.gender}">Others</option>
          </select>
          <i class="uil uil-mars icon"></i>
          <i class="uil uil-venus icon female"></i>
        </div>
        <div class="input-field">
          <!-- Date of birth -->
          <input type="date" placeholder="Date of birth" id="birthDate" name="birthDate" value="${user.birthDate}" required max="2004-11-05">
          <i class="uil uil-calender icon"></i>
        </div>
        <div class="input-field button">
          <!-- Register button -->
          <input type="submit" name="sumbit" value="Submit" id="submit-reg-button">
        </div>
      </form>

      <div class="login-signup">
        <!-- Link to Login form -->
        <span class="text">Already have an account?
          <a href="#" class="text login-link">Login</a>
        </span>
      </div>
    </div>

  </div>
</div>

<script src="js/login_register.js"></script>

</body>
</html>