# 🎬 CinéBook – Movie Ticket Booking System

CinéBook is a Java-based desktop application developed using Java Swing for managing movie ticket bookings. The application provides a simple and interactive interface through which users can register, log in, select movies and show details, choose seats, calculate the ticket amount, select a payment method, and view their booking history.

The project demonstrates the practical implementation of Core Java and Object-Oriented Programming concepts along with Java Swing, event handling, collections, file handling, input validation, and exception handling.

## ✨ Features

- User registration and login
- Movie selection
- Date and show-time selection
- Interactive seat selection
- Automatic ticket fare calculation
- Multiple payment options
- Booking confirmation with booking ID
- Booking history
- File-based data storage
- Graphical User Interface using Java Swing

## 🛠️ Technologies Used

- **Programming Language:** Java
- **GUI:** Java Swing
- **Data Storage:** Text-file based file handling
- **IDE:** Visual Studio Code
- **Version Control:** Git and GitHub

## 📚 Java Concepts Used

The project applies several important Java programming concepts:

- Object-Oriented Programming
- Classes and Objects
- Encapsulation
- ArrayList and Collections
- Event Handling
- Exception Handling
- File Handling
- Input Validation
- GUI Development using Swing
- CardLayout for screen navigation

## 🔄 Application Flow

```text
Login / Registration
        ↓
     Home Page
        ↓
   Select Movie
        ↓
 Select Date & Time
        ↓
    Select Seats
        ↓
 Calculate Total Fare
        ↓
 Select Payment Method
        ↓
 Booking Confirmation
        ↓
   Booking History

💾 Data Storage

CinéBook uses file handling instead of an external database.

The application uses:

users.txt – stores registered user information locally.
bookings.txt – stores booking records locally.

These files are generated and maintained locally by the application and are excluded from the public GitHub repository for privacy and security.

🎫 Booking Details

Each successful booking generates a unique booking ID and stores details such as:

Booking ID
Movie name
Date
Show time
Selected seats
Total amount

CineBook-Movie-Ticket-Booking-System/
│
├── src/
│   └── cinematiketbookingsystem/
│       └── Java source files
│
├── img/
│   └── Application images
│
├── lib/
│   └── Project libraries
│
├── nbproject/
│   └── Project configuration
│
├── .gitignore
├── build.xml
└── manifest.mf

▶️ How to Run
Clone or download the repository.
Open the project in Visual Studio Code.
Make sure Java/JDK is installed.
Open the Java source file.
Compile and run the application.
Register a user or use an existing local account.
Follow the booking workflow to create a movie ticket.


👩‍💻 Developed By

Asavari Adawadkar
Electronics and Telecommunication Engineering
Pillai College of Engineering
Academic Year 2026–27
