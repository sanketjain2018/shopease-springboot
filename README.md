# 🛒 ShopEase — Full-Stack E-Commerce Web Application

A production-style e-commerce platform built with **Spring Boot MVC** and **Thymeleaf**, designed to replicate a real-world online shopping workflow — not just another CRUD demo.

The project emphasizes clean architecture, layered security, global error handling, and reusable UI components.

![Java](https://img.shields.io/badge/Java-17-red)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Key Features](#-key-features)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
- [Screenshots](#-screenshots)
- [Roadmap](#-roadmap)
- [Contact](#-contact)

---

## 🚀 Overview

ShopEase demonstrates end-to-end full-stack development — from database schema design through secure REST-free server-rendered UI — using industry-standard Spring Boot conventions.

**Highlights:**
- Built as a production-style Spring Boot MVC application, not a tutorial clone
- Role-based access control with proper authorization boundaries (not just hidden UI links)
- Reusable Thymeleaf layouts and fragments for consistent, maintainable UI
- Centralized exception handling with custom error pages instead of default Spring error responses
- Structured logging for debugging and request traceability

---

## 🔥 Key Features

### 👤 Authentication & Authorization
- User registration and login with encrypted credentials
- Role-based access control (`USER` / `ADMIN`)
- Protected routes enforced via Spring Security filter chain
- Custom `403 – Access Denied` page for unauthorized access attempts

### 🛍 Shopping Workflow
- Product listing with pagination and sorting
- Detailed product view pages
- Add to cart / remove from cart
- Order placement and order history tracking
- Separate dashboards for users and admins

### 🎨 UI & Layout
- Reusable Thymeleaf fragments (header, navbar, footer) for consistent structure across pages
- Fully responsive UI built with Bootstrap 5
- Cohesive color system across header, navbar, content, and footer
- Static informational pages: Home, About, Contact

### ⚠️ Error Handling
- Global exception handling via `@ControllerAdvice`
- Custom error pages for:
  - `404` – Page Not Found
  - `403` – Access Denied
  - `500` – Internal Server Error
- Default Spring Whitelabel error page disabled in favor of branded error views

### 📝 Logging
- SLF4J logging integrated across controllers and services
- Informational and debug-level logs for key operations (auth events, order placement, admin actions)

---

## 🛠 Tech Stack

| Layer | Technologies |
|---|---|
| **Backend** | Java 17, Spring Boot 3.x, Spring MVC, Spring Security, Spring Data JPA (Hibernate) |
| **Frontend** | Thymeleaf, HTML5, CSS3, Bootstrap 5 |
| **Database** | MySQL (production), H2 (development/testing) |
| **Build & Tools** | Maven, Git, GitHub |

---

## 📂 Project Structure

```
src
└── main
    ├── java
    │   └── in.sj
    │       ├── controller     # Handles HTTP requests and view routing
    │       ├── service        # Business logic layer
    │       ├── repository     # Data access layer (Spring Data JPA)
    │       └── exception      # Custom exceptions + @ControllerAdvice handlers
    └── resources
        ├── templates
        │   ├── fragments       # Reusable header, navbar, footer
        │   ├── error           # Custom 403/404/500 pages
        │   └── pages           # Feature pages (products, cart, dashboards)
        └── static
            ├── css
            └── images
```

---

## ⚙️ Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.x (or use the bundled H2 for local testing)

### Setup

```bash
# Clone the repository
git clone https://github.com/<your-username>/ShopEase.git
cd ShopEase

# Configure database credentials
# Edit src/main/resources/application.properties with your MySQL username/password

# Build and run
mvn clean install
mvn spring-boot:run
```

The app will be available at `http://localhost:8080`.

> 💡 To run with the in-memory H2 database instead of MySQL for quick local testing, switch the active Spring profile to `dev` in `application.properties`.

---

## 📸 Screenshots

| Home Page | Products Page |
|---|---|
| ![Home Page](https://github.com/user-attachments/assets/0ac6febf-5900-4b29-8e20-92f8cd013525) | ![Products Page](https://github.com/user-attachments/assets/580eb778-5817-41f1-baaa-d3485c30d8a9) |

| Cart Page | Product Details Page |
|---|---|
| ![Cart Page](https://github.com/user-attachments/assets/d6e41050-c128-4b67-85c4-ab1e0da5b9a5) | ![Product Details](https://github.com/user-attachments/assets/4eebf696-c230-49fc-8c3b-e4944f0ee6dc) |

| User Dashboard | Admin Dashboard |
|---|---|
| ![User Dashboard](https://github.com/user-attachments/assets/6706f573-040f-4674-bb07-cfa7739c0fd5) | ![Admin Dashboard](https://github.com/user-attachments/assets/51233604-7419-401c-b403-7f42f3d64837) |

| Admin User Management | Admin Product Management |
|---|---|
| ![Admin Users](https://github.com/user-attachments/assets/93725724-06f1-4ff0-8eb4-7056bbe055fb) | ![Admin Products](https://github.com/user-attachments/assets/9a1c951e-e713-4b6b-b56e-8960a737dafb) |

---

## 🗺 Roadmap

Planned improvements to further round out the project:

- [ ] Payment gateway integration (Razorpay/Stripe sandbox)
- [ ] Product search with filters (category, price range)
- [ ] Email notifications on order confirmation
- [ ] Unit and integration tests (JUnit + Mockito)
- [ ] Dockerize the application for one-command deployment
- [ ] CI pipeline via GitHub Actions

---

## 📬 Contact

**Sanket Jain**
📧 sanketjfs@gmail.com
📍 Pune, Maharashtra, India

If you found this project useful or have suggestions, feel free to open an issue or connect on LinkedIn.

---

*Built as a hands-on demonstration of full-stack Spring Boot development — architecture, security, and UI included, not just the happy path.*
