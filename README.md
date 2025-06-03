# Air-Gym

**Air-Gym** is a JavaFX desktop application designed for comprehensive gym membership management. It leverages the Model-View-Controller (MVC) architecture alongside the Data Access Object (DAO) pattern to ensure a modular and maintainable codebase. The application interfaces with a Microsoft SQL Server backend, utilizing JDBC for database operations.

📺 Demo Video
🎥 Watch Air-Gym in action on YouTube: https://youtu.be/diQB5B5d1SY

## Features

### Member Functionality

- **Authentication**: Members log in using their phone number and password.
- **Membership Management**:
  - Purchase new memberships.
  - Freeze, unfreeze, cancel, extend, or renew existing memberships.
- **Profile Management**: Update login credentials.
- **Session Booking**:
  - View available sessions retrieved from the database.
  - Book sessions and manage existing bookings, including cancellations.

### Admin Functionality

- **Dashboard**:
  - View insights on active members and trainers.
  - Visualize revenue and expenses through charts.
- **Management Operations**:
  - View and update member information.
  - Perform CRUD operations on trainers, branches, equipment, membership types, and sessions.
  - Track and manage payments.

## Architecture

The application is structured using the MVC pattern:

- **Model**: Plain Old Java Objects (POJOs) representing entities such as `Member`, `Trainer`, `Session`, etc.
- **View**: FXML files defining the UI layout, paired with corresponding controllers handling user interactions.
- **Controller**: The main controller orchestrates interactions between the DAO layer, FXML views, and their controllers.

The DAO layer handles all database interactions using JDBC, executing SQL scripts, stored procedures, and functions to perform CRUD operations.

## Database Schema

The backend is powered by Microsoft SQL Server. The database schema includes tables for:

- Users
- Member
- Trainer
- Branch
- Equipment
- MembershipType
- Session
- Booking
- Payment

SQL scripts are provided to create the necessary tables, stored procedures, and functions that facilitate the application's operations.


