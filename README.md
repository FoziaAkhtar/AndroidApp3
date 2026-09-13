# AndroidApp3 - Digital Compass & Location App

## 📱 Project Overview

AndroidApp3 is an Android Digital Compass and Location application developed using **Kotlin** and **Android Studio**.

The application demonstrates how Android device sensors, GPS location services, continuous location updates, reverse geocoding, and OpenStreetMap can be combined to create a functional navigation-oriented Android application.

The application provides:

- 🧭 Digital compass functionality
- 📡 Accelerometer and magnetic field sensor readings
- 📍 GPS location
- 🔄 Continuous live location updates
- 🏠 Reverse geocoded address information
- 🗺️ OpenStreetMap integration
- 📌 Map location marker
- ☰ Toolbar navigation
- 🔐 Runtime location permissions

This project was developed as part of an Android development course and was extended for **Assignment 6** to include continuous location tracking and map functionality.

---

# ✨ Features

## 🧭 Digital Compass

The application provides a functional digital compass.

Features include:

- Displays a compass image.
- Compass rotates as the device orientation changes.
- Calculates the device's current bearing from **0° to 360°**.
- Converts the numerical bearing into a readable compass direction.

Supported directions include:

- North
- North-East
- East
- South-East
- South
- South-West
- West
- North-West

---

## 📡 Device Sensors

The application uses Android's built-in sensors to calculate compass orientation.

Sensors used:

- **Accelerometer**
- **Magnetic Field Sensor**

The accelerometer provides information about gravity and device movement.

The magnetic field sensor provides information about the Earth's magnetic field.

The two sensor readings are combined using Android's sensor APIs to calculate the device orientation.

The application also displays live:

- X-axis sensor values
- Y-axis sensor values
- Z-axis sensor values

---

# 📍 GPS Location

The application uses Android location services to retrieve the device's geographic position.

The application displays:

- Latitude
- Longitude

Location access is protected using Android runtime permissions.

The application uses:

```text
FusedLocationProviderClient
# 📂 Project Structure

```text
AndroidApp3/
│
├── app/
│   │
│   ├── build.gradle.kts
│   │
│   └── src/
│       │
│       └── main/
│           │
│           ├── java/
│           │   │
│           │   └── com/
│           │       │
│           │       └── foziaakhtar/
│           │           │
│           │           └── androidapp3/
│           │               │
│           │               ├── MainActivity.kt
│           │               │   └── Digital Compass
│           │               │
│           │               ├── LiveLocationActivity.kt
│           │               │   └── Continuous Live Location
│           │               │
│           │               └── MapActivity.kt
│           │                   └── OpenStreetMap
│           │
│           ├── res/
│           │   │
│           │   ├── drawable/
│           │   │   └── ic_compass.xml
│           │   │
│           │   ├── layout/
│           │   │   │
│           │   │   ├── activity_main.xml
│           │   │   │   └── Compass Screen
│           │   │   │
│           │   │   ├── activity_live_location.xml
│           │   │   │   └── Live Location Screen
│           │   │   │
│           │   │   └── activity_map.xml
│           │   │       └── Map Screen
│           │   │
│           │   ├── menu/
│           │   │   └── main_menu.xml
│           │   │       └── Toolbar Navigation Menu
│           │   │
│           │   └── values/
│           │       └── strings.xml
│           │
│           └── AndroidManifest.xml
│               └── App Components & Permissions
│
├── README.md
│
└── .gitignore

### 📌 Closing / Final Comments

Paste this at the **very end** of your README:

```markdown
---

# 🎓 Final Project Reflection

AndroidApp3 began as a Digital Compass application and was expanded through Assignment 6 to demonstrate additional Android location and mapping functionality.

Throughout the project, the application was developed using Kotlin and Android Studio while working with Android sensors, runtime permissions, GPS services, continuous location updates, reverse geocoding, and OpenStreetMap.

The completed application demonstrates how multiple Android APIs can work together to create a practical location-based application.

The project also provided hands-on experience with:

- Kotlin and Android development
- Sensor integration
- Sensor fusion
- GPS and location services
- Activity lifecycle management
- Runtime permissions
- Continuous location updates
- Reverse geocoding
- OpenStreetMap and osmdroid
- XML user interfaces
- Toolbar navigation
- Android emulator testing
- Git and GitHub version control

The application was tested in Android Studio using an Android emulator, and the major Assignment 6 features were implemented and verified.

---

# ✅ Final Project Status

**AndroidApp3 - Digital Compass & Location App**

**Assignment 6: Complete and Functional ✅**

The final application includes:

🧭 Digital Compass  
📡 Accelerometer & Magnetic Field Sensors  
📍 GPS Location  
🔄 Continuous Live Location  
🏠 Reverse Geocoding  
🗺️ OpenStreetMap  
📌 Map Marker  
☰ Toolbar Navigation  
🔐 Runtime Permissions  
🧪 Emulator Testing  
📦 GitHub Version Control  

---

# 👩‍💻 Author

**Fozia Akhtar**

Android Development Project

**AndroidApp3 - Digital Compass & Location App**

---

## 🙏 Thank You

Thank you for reviewing the AndroidApp3 project.

This project provided valuable hands-on experience in Android application development and demonstrated how sensors, location services, mapping technologies, and user interface components can be integrated into a single functional application.

---
