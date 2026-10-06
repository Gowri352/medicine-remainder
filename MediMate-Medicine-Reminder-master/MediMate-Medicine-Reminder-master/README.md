# 💊 MediMate - Medicine Reminder

**MediMate – Your Medicine, On Time**

MediMate is an Android application designed to help users remember to take their medicines at the scheduled time. Users can add, view, edit, and delete their medicines and set daily medicine reminders.

The application uses Firebase Authentication for user login and registration and Firebase Firestore to store medicine details.

---

## 📱 Features

- 🔐 User Registration
- 🔑 User Login
- 🚪 User Logout
- 👤 User Profile
- ➕ Add Medicine
- 💊 Store Medicine Name and Dosage
- ⏰ Select Medicine Time
- 🔔 Daily Medicine Reminder
- 📋 View My Medicines
- ✏️ Edit Medicine Details
- 🗑️ Delete Medicine
- 🔄 Update Medicine Reminder
- ☁️ Firebase Firestore Database
- 🔥 Firebase Authentication

---

## 🛠️ Technologies Used

### Frontend
- Java
- XML
- Android Studio

### Backend / Database
- Firebase Authentication
- Firebase Firestore

### Android Features
- AlarmManager
- BroadcastReceiver
- RecyclerView
- TimePickerDialog
- Android Notifications

---

## 🔄 Application Flow

```text
                ┌───────────────┐
                │     Login     │
                └───────┬───────┘
                        │
              New User? │
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
