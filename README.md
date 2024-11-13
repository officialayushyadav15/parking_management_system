# ParkSmart - Parking Management System

ParkSmart is a dynamic Parking Management System built with Java, designed to streamline parking operations for employees and members. The system features dual login functionality, secure OTP verification, real-time slot updates, automated notifications, and more, ensuring efficient and reliable parking management.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Installation](#installation)
- [Usage](#usage)
- [Database Schema](#database-schema)
- [Future Improvements](#future-improvements)
- [Contributing](#contributing)

---

## Overview

ParkSmart simplifies parking management by allowing users to book slots near their location, manage bookings, and receive real-time updates. Employees can oversee slot availability, verify entry via OTP, and report overstaying vehicles. Members can book slots within 24 hours of arrival, make payments, receive confirmations via WhatsApp and email, and rate parking experiences.

## Features

- **Dual Login System**: Separate login for employees and members.
- **Real-Time Updates**: Shows slot availability and upcoming bookings for employees.
- **Booking and Payment Options**: Members can choose between payment on arrival or pre-payment.
- **Notifications**: Automated notifications sent via Twilio’s WhatsApp API and JavaMail.
- **Discount for New Users**: First-time users receive a 10% discount on their booking.
- **Booking Cancellation and Refund**: Flexible cancellation policies with automated refund processing.

## Tech Stack

- **Frontend**: Java Swing, AWT
- **Backend**: Java, JDBC
- **Database**: MySQL
- **APIs**:
  - **Twilio WhatsApp API**: For sending OTPs and notifications
  - **JavaMail API**: For email confirmations and receipts

## Installation

### Prerequisites

- Java Development Kit (JDK 19)
- MySQL Database
- Twilio Account (for WhatsApp API)
- JavaMail API setup
- Git

### Steps

1. **Clone the repository**:
   ```bash
   git clone https://github.com/officialayushyadav15/parking_management_system.git
   cd parking_management_system

   ```

2. **Configure the Database**:
   - Create a MySQL database and tables using the provided schema in `src/resources/parkingsystem.sql`.
   - Update the database credentials in the application code (JDBC connection URL, username, and password).

3. **Set up Twilio API**:
   - Register for a Twilio account and obtain your API credentials.
   - Add your Twilio SID, Auth Token, and WhatsApp number in the appropriate configuration files.

4. **Compile and Run**:
   - Compile the project in your IDE (NetBeans, Eclipse, or IntelliJ) or using command-line tools.
   - Run the main class to start the application.

## Usage

1. **Login**:
   - Employees and members can log in using their respective credentials. New users can register via the registration page.

2. **Member Actions**:
   - **Book Slot**: Search for parking slots near your location (within 1 km) and book for up to 4 hours.
   - **Payment Options**: Choose between paying on arrival or pre-payment.
   - **Receive Notifications**: Receive booking confirmation and slot details via WhatsApp and email.

3. **Employee Actions**:
   - **Manage Slots**: View available slots and manage vehicle check-ins and departures.
   - **Overstay Reporting**: Report vehicles that overstay and initiate additional fee processing.

4. **Review and Feedback**:
   - Members can leave reviews of parking slots, viewable by others, and report any issues with employees.

## Database Schema

The database schema includes tables for:
- **User Login**: Stores user and employee login details.
- **Parking Lot**: Stores information on parking slots and their availability.
- **Booking and Records**: Tracks booking details and vehicle check-in/out times.
- **Reviews**: Stores user reviews for parking lots.
- **Payments**: Stores payment details and status for each booking.

Refer to `src/resources/parkingsystem.sql` for the complete schema.

## Future Improvements

- **Mobile App**: Develop a mobile version for easier access on smartphones.
- **AI-based Slot Prediction**: Use historical data to predict slot availability trends.
- **Advanced Payment Integration**: Add support for more payment gateways.

## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your feature or bug fix.

