# FlashLearn - README

## Overview

FlashLearn is a Spring Boot application designed for studying and organizing learning materials using flashcards. The application features user authentication, role management, and CRUD operations for flashcard topics and individual flashcards. FlashLearn is built with a MongoDB backend for data persistence.

## User Stories

- As a user, I want to register an account so that I can access the application's features.
- As a user, I want to log in to my account to access my data and flashcards.
- As a user, I want to create new flashcard sets to organize my learning materials.
- As a user, I want to edit flashcard sets to update their content.
- As a user, I want to study flashcards in random order to improve my learning process.
- As a user, I want to change my password to ensure my account's security.

## Identification of Two Target Audiences

- Students preparing for exams.
- Professionals learning new skills or terminology.

## Two Potential Benefits for Users

- Improved learning efficiency by organizing materials into flashcard sets.
- Flexibility to study anytime and anywhere with a simple interface.

## Features

- **User Management:** Registration, login, and password change functionality.
- **Flashcards Management:** Create, delete, and study flashcard sets and individual flashcards.
- **Topics Dashboard:** View and manage topics and their associated flashcards.
- **Dynamic Study Mode:** Shuffle flashcards for effective learning.

## Project Structure

### Controllers

- `AuthController`: Handles authentication-related endpoints like login, registration, and password change.
- `FlashcardController`: Manages CRUD operations for flashcard sets and flashcards.
- `StaticResourceController`: Serves static resources like CSS files.

### Services

- `UserService`: Handles business logic for user authentication, registration, and password management.
- `FlashcardService`: Manages flashcard sets and individual flashcards.

### Repositories

- `UserRepository`: Interface for accessing user data in MongoDB.
- `FlashcardSetsRepository`: Interface for accessing flashcard sets in MongoDB.

### Models

- `User`: Represents application users with fields for username, password, and role.
- `FlashcardSet`: Represents a flashcard set containing a title, description, and list of flashcards.
- `Flashcard`: Represents an individual flashcard with a name and description.

### Views (HTML Templates)

- `login.html`: User login page.
- `register.html`: Registration page.
- `dashboard.html`: Displays all flashcard sets.
- `form.html`: Form for creating or editing flashcard sets.
- `topic.html`: View and manage individual flashcards in a topic.
- `study.html`: Study interface for flashcards.
- `password_change.html`: Page for changing user password.

### Configuration

- `application.properties`: Contains application-level configurations, such as MongoDB connection details.
- `SecurityConfig`: Configures Spring Security, including URL access rules and CSRF settings.

### Static Resources

- `styles.css`: Custom styling for the application.

## Prerequisites

- Java 21 or higher 
- MongoDB

## Installation

1. Clone the repository:
   ```bash
   git clone <repository_url>
   cd FlashLearn
   ```
2. Set up MongoDB:
    - Ensure MongoDB is running on `localhost:27017` with the database name `flashlearn` (as defined in `application.properties`).
3. Build the project:
   ```bash
   ./gradlew build
   ```
4. Run the application:
   ```bash
   ./gradlew bootRun
   ```
5. Open http://localhost:8080/ in a web browser

## Endpoints

### Public Endpoints

- `GET /login`: Login page.
- `POST /login`: Submit login credentials.
- `GET /register`: Registration page.
- `POST /register`: Register a new user.

### Protected Endpoints

- `GET /flashcards`: View all flashcard sets.
- `POST /flashcards`: Create a new flashcard set.
- `GET /flashcards/topic/{title}`: View details of a flashcard set.
- `POST /flashcards/topic/{title}/add`: Add a new flashcard to a set.
- `POST /password_change`: Change the user's password.
- `POST /logout`: Log out of the application.

## Testing

Unit tests are located in `FlashLearnApplicationTests.java` and cover various scenarios, including:

- Redirects for unauthenticated users.
- Feedback during registration.
- Access control for protected pages.

Run the tests with:

```bash
./gradlew test
```

## Technologies Used

- **Backend:** Spring Boot, Spring Security, Spring Data MongoDB
- **Database:** MongoDB
- **Frontend:** Thymeleaf, Bootstrap, Custom CSS
- **Testing:** JUnit, MockMvc

## Future Enhancements

- Add support for admin roles to manage users.
- Enhance flashcard study mode with advanced algorithms (e.g., spaced repetition).
- Provide user statistics for learning progress.

## Authors

Developed by Rafał Musiałowski 154141, Paweł Ćwik 149954, and Dawid Włodarczyk 152249.

