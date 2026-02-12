### Perfil do GitHub

<h1 align="center">Hi 👋, I'm Gleysson Flavio</h1>
<h3 align="center">Service Management System Developer</h3>

- 🔭 I’m currently working on the **Service Management System**
- 🌱 I’m currently learning **Spring Boot, Java**
- 💬 Ask me about **Spring Boot**
- 📫 How to reach me: **GLEYSSON_FLAVIO@HOTMAIL.COM**

<h3 align="left">Connect with me:</h3>
<p align="left">
    </p>

<h3 align="left">Languages and Tools:</h3>
<p align="left">
    <a href="https://www.w3schools.com/css/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/css3/css3-original-wordmark.svg" alt="css3" width="40" height="40"/> </a>
    <a href="https://www.figma.com/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/figma/figma-icon.svg" alt="figma" width="40" height="40"/> </a>
    <a href="https://git-scm.com/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/git-scm/git-scm-icon.svg" alt="git" width="40" height="40"/> </a>
    <a href="https://www.w3.org/html/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/html5/html5-original-wordmark.svg" alt="html5" width="40" height="40"/> </a>
    <a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/> </a>
    <a href="https://www.postgresql.org" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/postgresql/postgresql-original-wordmark.svg" alt="postgresql" width="40" height="40"/> </a>
    <a href="https://spring.io/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="40" height="40"/> </a>
</p>


---

### README do Projeto

# Service Management System (SMS)

This project is a service management system developed with Spring Boot, focused on providing an intuitive and efficient solution for controlling customer interactions. It allows for user registration, detailed logging of service calls, and report generation for data analysis.

## Technologies Used

* **Spring Boot**: Main framework for Java application development.
* **Spring Security**: For authentication and user authorization control.
* **Spring Data JPA**: For data persistence in the database.
* **Thymeleaf**: Template engine for web pages.
* **PostgreSQL**: Relational database.
* **Maven**: Project build automation tool.
* **HTML, CSS, JavaScript**: For building user interfaces.

## Environment Setup

To run this project on your machine, follow the steps below:

### Prerequisites

* **Java Development Kit (JDK) 17 or higher**.
* **Maven 3.x**.
* **PostgreSQL**: Ensure you have a running instance of PostgreSQL.

### Database Configuration

1. Create a PostgreSQL database named `gestao_db`.
2. In the `src/main/resources/application.properties` file, configure your database credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gestao_db
spring.datasource.username=postgres
spring.datasource.password=your_postgres_password # Change to your password
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.thymeleaf.cache=false

```

* `spring.jpa.hibernate.ddl-auto=update` will allow Hibernate to automatically create and update database tables based on your Java entities.

### Running the Application

1. Navigate to the project's root folder in your terminal.
2. Compile the project using Maven:

```bash
mvn clean install

```

3. Run the Spring Boot application:

```bash
mvn spring-boot:run

```

The application will be available at `http://localhost:8080`.

## Features Overview

The Service Management application consists of six main screens, following a design with three shades of blue and white. Colors are configured in a main CSS file (`_variables.css` or `main.css` depending on your structure).

### 1. Login Screen

* **Access:** `http://localhost:8080/login` or `http://localhost:8080/`.
* **Functionality:** Allows users to access the system.
* **Fields:** Login ID and Password.
* **Options:** "Forgot Password?" (redirects to password reset), "Remember Me" (checkbox), "Login" button, "Back" button.
* **Authentication Flow:** Upon entering credentials and clicking "Login", the system validates them. On success, the user is redirected to the "Main Screen". In case of invalid credentials, an error message is displayed.
* **Access Rules:** Regular users must have a prior registration created by an ADMIN user on the User Registration Screen.

### 2. Password Reset Screen

* **Access:** Exclusively via the "Forgot Password?" link on the Login Screen.
* **Functionality:** Allows users to reset their password.
* **Fields:** Login ID and New Password.
* **Action:** After entering the data and clicking "Save", the password is updated, and the user is redirected back to the Login Screen.

### 3. Main Screen (Home)

* **Access:** After successful login.
* **Functionality:** Central dashboard with navigation options and BI information.
* **Menu:** Three buttons with hover color effects:
* **User Registration**: Access to user management.
* **Service Registration**: Access to service entry logs.
* **Service Reports**: Access to reports and analytics.


* **BI Dashboard:** Displays the number of service calls (monthly and daily), Top 10 Customers, and Top 10 Companies.
* **"Logout" Button:** Logs out of the application and returns to the Login Screen.

### 4. User Registration Screen

* **Access:** Via the "User Registration" button on the Main Screen.
* **Functionality:** System user management (Create, Update, Delete).
* **Fields:** Login ID, Full Name, Password, Department (dropdown), Role (dropdown).
* **Departments:** Sales, Logistics, Technical, Finance, Tax Issuance, Tax Settlement, Tax Inbound, Purchasing, Others.
* **Roles:** Attendant, Analyst, Supervisor, Manager.
* **Actions:** "Save", "Delete", "Update" buttons. Success messages are displayed after each operation.
* **Permissions:** Only users with **ADMIN** authority can modify or delete data from other users.
* **Navigation:** "Back to Main Menu" button.

### 5. Service Registration Screen

* **Access:** Via the "Service Registration" button on the Main Screen.
* **Functionality:** Logging and managing service calls.
* **Fields:** Customer Company, Customer Name, Date/Time, Contact Reason, Provided Solution, CRS Number, Phone, AnyDesk, Service Channel (dropdown).
* **Service Channels:** Phone, Mkon WhatsApp, E-mail.
* **Actions:** "Save", "Update", "Delete" buttons.

### 6. Service Reports Screen

* **Access:** Via the "Service Reports" button on the Main Screen.
* **Functionality:** View and export service data with filters.
* **Filters:** Customer Name, Customer Company, Service Period (start and end date). Reports update dynamically.
* **Data Displayed:** Company, Name, Date/Time, Reason, Solution, CRS, Phone, AnyDesk, Channel.
* **Sorting:** Sortable by Customer Name or Service Date.
* **Export:** "Print", "Generate PDF", "Export to Excel" buttons.

---

**Developer Notes:**

* The `SecurityConfig.java` class handles security rules, allowing public access to login pages and static assets (`css`, `js`, `img`), while requiring authentication for all other requests.
* `BCryptPasswordEncoder` is used to encrypt user passwords, ensuring high security.
* `CadastroUsuarioController.java` uses `@PreAuthorize("hasAuthority('ADMIN')")` annotations to ensure only admins can edit or delete users.

---
