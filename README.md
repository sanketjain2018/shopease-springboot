🛒 ShopEase – E-Commerce Web Application

ShopEase is a full-stack e-commerce web application built using Spring Boot MVC and Thymeleaf, designed to replicate a real-world online shopping workflow.

The project focuses on clean architecture, security, global error handling, and reusable UI design, rather than just basic CRUD operations.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

🚀 Project Highlights

Built as a production-style Spring Boot MVC application

Focus on maintainability, security, and user experience

Uses reusable layouts and fragments for consistent UI

Implements proper exception handling and custom error pages

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

🔥 Key Features
👤 Authentication & Authorization

User registration and login

Role-based access control (USER / ADMIN)

Protected routes using Spring Security

Custom 403 – Access Denied page

🛍 Shopping Workflow

Product listing with pagination and sorting

Product details view

Add to cart / remove from cart

Place orders and view order history

User dashboard and admin dashboard

🎨 UI & Layout

Thymeleaf layout with reusable fragments
(Header, Navbar, Footer)

Responsive UI using Bootstrap 5

Balanced color system for header, navbar, content, and footer

Static pages: Home, About, Contact

⚠ Error Handling

Global exception handling using @ControllerAdvice

Custom error pages:

404 – Page Not Found

403 – Access Denied

500 – Internal Server Error

Default Whitelabel error page disabled

📝 Logging

SLF4J logging for request tracking

Informational and debug logs for key operations   

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

🛠 Tech Stack
Backend

Java 17

Spring Boot 3.x

Spring MVC

Spring Security

Spring Data JPA (Hibernate)

Frontend

Thymeleaf

HTML5 / CSS3

Bootstrap 5

Database

MySQL (main database)

H2 (development/testing)

Tools

Maven

Git & GitHub

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

📂 Project Structure (Simplified)
src
 └── main
     ├── java
     │   └── in.sj
     │       ├── controller
     │       ├── service
     │       ├── repository
     │       └── exception
     └── resources
         ├── templates
         │   ├── fragments
         │   ├── error
         │   └── pages
         └── static
             ├── css
             └── images

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

📸 Screenshots

![Home Page] <img width="1960" height="3796" alt="localhost_8080_ (1)" src="https://github.com/user-attachments/assets/0ac6febf-5900-4b29-8e20-92f8cd013525" />















