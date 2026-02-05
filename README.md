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

[Home Page] <img width="1960" height="3796" alt="localhost_8080_ (1)" src="https://github.com/user-attachments/assets/0ac6febf-5900-4b29-8e20-92f8cd013525" />
[Products Page] <img width="1960" height="3974" alt="localhost_8080_shop" src="https://github.com/user-attachments/assets/580eb778-5817-41f1-baaa-d3485c30d8a9" />
[Cart Page] <img width="1960" height="2260" alt="localhost_8080_user_cart (1)" src="https://github.com/user-attachments/assets/d6e41050-c128-4b67-85c4-ab1e0da5b9a5" />
[Product-details Page] <img width="1960" height="3404" alt="localhost_8080_user_orders" src="https://github.com/user-attachments/assets/4eebf696-c230-49fc-8c3b-e4944f0ee6dc" />
[User Dashboard Page] <img width="1960" height="2634" alt="localhost_8080_user_dashboard" src="https://github.com/user-attachments/assets/6706f573-040f-4674-bb07-cfa7739c0fd5" />
[Admin Dashboard] <img width="1960" height="2952" alt="localhost_8080_admin_dashboard" src="https://github.com/user-attachments/assets/51233604-7419-401c-b403-7f42f3d64837" />
[Admin User Control Page] <img width="1960" height="2768" alt="localhost_8080_admin_users" src="https://github.com/user-attachments/assets/93725724-06f1-4ff0-8eb4-7056bbe055fb" />
[Admin Products Management Page] <img width="1960" height="3744" alt="localhost_8080_products-ui_keyword= sortBy=id size=20" src="https://github.com/user-attachments/assets/9a1c951e-e713-4b6b-b56e-8960a737dafb" />
















