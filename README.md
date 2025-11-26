# Airline Management System

A robust Java console-based application designed to manage flights, passengers, and bookings.

This project demonstrates the use of:

- DAO Pattern (Data Access Object)
- Service Layer Architecture
- JDBC for MySQL connectivity
- Layered MVC Structure

## Features

### Flight Management

- Add new flights
- View all flight schedules
- Search flights by Origin and Destination

### Booking System

- Book tickets (automatic seat validation)
- Cancel tickets (restores seat availability)

### Passenger Management

- Add and track passenger information

### Admin Tools

- Reset database (drop + recreate tables)
- Insert sample data automatically

## Tech Stack

| Component | Technology |
| --------- | ---------- |
| Language | Java (JDK 8+) |
| Database | MySQL |
| Driver | MySQL Connector/J |
| Architecture | MVC + DAO + Service Layers |
| Connectivity | JDBC |

## Project Structure

```bash
AirlineManagementSystem/
├── libs/                            
│   └── mysql-connector-j-9.5.0/     
├── src/                              
│   ├── Main.java                     
│   ├── database/                     
│   ├── model/                        
│   ├── dao/                          
│   ├── service/                      
│   └── utils/                        
└── bin/
```

## Setup & Installation

### 1. Prerequisites

- JDK installed
- MySQL Server running
- MySQL Connector/J placed in `/libs`

### 2. Database Setup

Create the database:

```sql
CREATE DATABASE airline_system;
```

Edit your DB credentials inside `src/database/DBConnection.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3308/airline_system";
private static final String USER = "root";
private static final String PASSWORD = "your_password";
```

⚠️ Change port to 3306 or 3308 depending on your MySQL installation.

## How to Run the Project

### ✅ Recommended Method: Clean Compile

Navigate to the src folder:

```bash
cd src
```

Compile everything to `bin/`:

```bash
mkdir -p ../bin
javac -d ../bin Main.java database/*.java model/*.java dao/*.java service/*.java utils/*.java
```

Run the application:

```bash
java -cp "../bin:../libs/mysql-connector-j-9.5.0/mysql-connector-j-9.5.0.jar" Main
```

### ⚡ Quick Run (Compiles inside src)

Navigate:

```bash
cd src
```

Compile:

```bash
javac -d . Main.java database/*.java model/*.java dao/*.java service/*.java utils/*.java
```

Run:

```bash
java -cp ".:../libs/mysql-connector-j-9.5.0/mysql-connector-j-9.5.0.jar" Main
```

## 🎮 Usage Guide

### First Run

Use the admin action:

```bash
[9] Reset Database & Seed Data
```

This will:

- Drop existing tables
- Recreate schema
- Insert test data

### Booking a Ticket

1. Choose Option 3
2. Enter valid Flight ID (get it using Option 1)

### Cancel a Ticket

1. Choose Option 4
2. Enter Ticket ID

## 🧹 Cleanup Commands

Delete all `.class` files:

```bash
find . -name "*.class" -type f -delete
```
