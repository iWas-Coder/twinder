<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- ===== Boxicons CSS ===== -->
    <link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>
    <!-- ===== CSS ===== -->
    <link rel="stylesheet" href="css/main.css">
    <title> Twinder </title>
    <!-- Loading the Favicon -->
    <link rel="icon" href="img/favicon.ico">
    <!-- JQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>

<nav class="sidebar close">
    <header>
        <div class="image-text">
            <span class="image">
                <img src="img/logo.png" alt="">
            </span>

            <div class="text logo-text">
                <span class="name">Anonymous</span>
            </div>
        </div>

        <i class='bx bx-chevron-right toggle'></i>
    </header>

    <div class="menu-bar">
        <div class="menu">

            <li id="search" class="search-box">
                <i class='bx bx-search icon'></i>
                <input id="searchInput" type="text" placeholder="Search...">
            </li>

            <ul class="menu-links">
                <li  id="home" class="nav-link">
                    <a href="#">
                        <i class='bx bx-home-alt icon' ></i>
                        <span class="text nav-text">Home</span>
                    </a>
                </li>

                <li id="users" class="nav-link">
                    <a href="#">
                        <i class='bx bx-user icon'></i>
                        <span class="text nav-text">Users</span>
                    </a>
                </li>

            </ul>
        </div>

        <div class="bottom-content">
            <li class="">
                <a href="LogoutController">
                    <i class='bx bx-log-out icon' ></i>
                    <span class="text nav-text">Login</span>
                </a>
            </li>

            <li class="mode">
                <div class="sun-moon">
                    <i class='bx bx-moon icon moon'></i>
                    <i class='bx bx-sun icon sun'></i>
                </div>
                <span class="mode-text text">Dark mode</span>

                <div class="toggle-switch">
                    <span class="switch"></span>
                </div>
            </li>
            
        </div>
    </div>
    
</nav>

<section id="home" class="homeContent mainContent">
    <div class="text">Home</div>
    <div class="container homeContainer">
        <!-- <div class="input-tweet">
            <input type="text" id="postTweet" placeholder="Enter tweet..." name="input-tweet"  maxlength="300">
        </div> 
        <div class="tweetButton">
    	</div>-->	
    </div>	
    
</section>

<section id="users" class="usersContent mainContent">
    <div class="text">Users</div>	
    <div class="container usersContainer">
    </div>	
</section>

<section id="user" class="userContent mainContent">
    <div class="text">User</div>	
    <div class="container userContainer">
    </div>	
</section>

<section id="tweetComments" class="tweetComentsContent mainContent">
    <div class="text">Tweet comments</div>	
    <div class="container tweetCommentsContainer">
    </div>	
</section>

<section id="search" class="searchContent mainContent">
    <div class="text">Search</div>	
    <div class="container searchContainer">
    </div>	
</section>

<script src="js/main.js"></script>
<script src = "js/jqueryFunctions_anon.js"></script>


</body>
</html>