# 💊 MediMate - Medicine Reminder

**MediMate – Your Medicine, On Time**

MediMate is an Android application designed to help users remember to take their medicines at the scheduled time.

Users can add, view, edit, and delete their medicines and set daily medicine reminders.

The application uses **Firebase Authentication** for user login and registration and **Firebase Firestore** to store medicine details.

## 📱 Features

🔐 User Registration
🔑 User Login
🚪 User Logout
👤 User Profile
➕ Add Medicine
💊 Store Medicine Name and Dosage
⏰ Select Medicine Time
🔔 Daily Medicine Reminder
📋 View My Medicines
✏️ Edit Medicine Details
🗑️ Delete Medicine
🔄 Update Medicine Reminder
☁️ Firebase Firestore Database
🔥 Firebase Authentication

## 🛠️ Technologies Used

### Frontend

* Java
* XML
* Android Studio

### Backend / Database

* Firebase Authentication
* Firebase Firestore

### Android Features

* AlarmManager
* BroadcastReceiver
* RecyclerView
* TimePickerDialog
* Android Notifications

## 🔄 Application Flow

```text
                ┌───────────────┐
                │     Login     │
                └───────┬───────┘
                        │
                New User?
                        │
                        ▼
                ┌───────────────┐
                │   Register    │
                └───────┬───────┘
                        │
                        ▼
                ┌───────────────┐
                │     Home      │
                └───────┬───────┘
                        │
        ┌───────────────┼────────────────┐
        │               │                │
        ▼               ▼                ▼
   Add Medicine    My Medicines       Profile
        │               │                │
        ▼               │                ▼
    Firestore           │             Logout
        │               │
        ▼               ▼
      Alarm          Edit / Delete
        │
        ▼
 Medicine Reminder
```

## 💊 Medicine Management

Users can add medicine details such as:

* Medicine Name
* Dosage
* Medicine Time

The medicine details are stored in **Firebase Firestore**.

Users can also:

* View their medicines
* Edit medicine details
* Delete medicines
* Update medicine reminder times

## 🔔 Medicine Reminder

MediMate uses Android's **AlarmManager** to schedule medicine reminders.

When the scheduled time is reached:

```text
Medicine Time
      ↓
AlarmManager
      ↓
BroadcastReceiver
      ↓
Android Notification
      ↓
💊 Take Your Medicine
```

This helps users remember to take their medicines on time.

## 🔐 Authentication

MediMate uses **Firebase Authentication** for user account management.

Users can:

* Create a new account
* Login using their account
* Access their profile
* Logout securely

## ☁️ Firebase Firestore

Firebase Firestore is used to store medicine information.

```text
Firebase
   │
   ├── Authentication
   │      ├── Register
   │      ├── Login
   │      └── Logout
   │
   └── Firestore
          │
          └── Medicine Details
                 ├── Medicine Name
                 ├── Dosage
                 └── Medicine Time
```

## 🎯 Project Objective

The main objective of MediMate is to help users remember to take their medicines at the correct time.

The application provides a simple way to manage medicines and receive daily reminders through Android notifications.

## 🚀 Main Functionalities

### 1. User Registration

New users can create an account using Firebase Authentication.

### 2. User Login

Registered users can log in and access the application.

### 3. Add Medicine

Users can enter the medicine name, dosage, and scheduled time.

### 4. View Medicines

Users can view all their saved medicines.

### 5. Edit Medicine

Users can update their medicine information.

### 6. Delete Medicine

Users can remove medicines that are no longer required.

### 7. Medicine Reminder

Users can set a specific time for their medicine reminder.

### 8. Notifications

The application displays an Android notification when it is time to take the medicine.

## 🧰 Tools Used

* Android Studio
* Java
* XML
* Firebase Console
* Firebase Authentication
* Firebase Firestore
* Git
* GitHub

## 📌 Future Enhancements

* Multiple reminders per medicine
* Weekly medicine schedules
* Medicine stock tracking
* Missed medicine notifications
* Doctor information
* Medicine history
* Reminder sound customization

## 👩‍💻 Project

**MediMate – Medicine Reminder**

**Developed using:** Java, XML, Android Studio and Firebase

