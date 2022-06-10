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
</head>
<body>

<nav class="sidebar close">
    <header>
        <div class="image-text">
            <span class="image">
                <img src="img/favicon.ico" alt="">
            </span>

            <div class="text logo-text">
                <span class="name">Twinder</span>
                <span class="profession">@Username</span>
            </div>
        </div>

        <i class='bx bx-chevron-right toggle'></i>
    </header>

    <div class="menu-bar">
        <div class="menu">

            <li class="search-box">
                <i class='bx bx-search icon'></i>
                <input type="text" placeholder="Search...">
            </li>

            <ul class="menu-links">
                <li class="nav-link">
                    <a href="#">
                        <i class='bx bx-home-alt icon' ></i>
                        <span class="text nav-text">Home</span>
                    </a>
                </li>

                <li class="nav-link">
                    <a href="#">
                        <i class='bx bx-heart icon' ></i>
                        <span class="text nav-text">Likes</span>
                    </a>
                </li>

                <li class="nav-link">
                    <a href="#">
                        <i class='bx bx-bell icon'></i>
                        <span class="text nav-text">Following</span>
                    </a>
                </li>

                <li class="nav-link">
                    <a href="#">
                        <i class='bx bx-user-pin icon'></i>
                        <span class="text nav-text">Followers</span>
                    </a>
                </li>

                <li class="nav-link">
                    <a href="#">
                        <i class='bx bx-user icon' ></i>
                        <span class="text nav-text">My Profile</span>
                    </a>
                </li>

            </ul>
        </div>

        <div class="bottom-content">
            <li class="">
                <a href="LogoutController">
                    <i class='bx bx-log-out icon' ></i>
                    <span class="text nav-text">Logout</span>
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

<section class="home">
    <div class="text">Home</div>
</section>

<script src="js/main.js"></script>

</body>
</html>