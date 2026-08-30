# AndroidApp3 - Digital Compass

## 📱 Project Overview

AndroidApp3 is an Android Digital Compass application developed using **Kotlin** and **Android Studio**.

The application demonstrates how Android device sensors and location services can be used together to create a functional compass application.

The app displays the user's current direction, compass bearing, sensor readings, and GPS location.

---

## ✨ Features

### 🧭 Digital Compass

* Displays a compass image.
* Compass rotates as the device orientation changes.
* Calculates the device's current bearing from **0° to 360°**.
* Converts the numerical bearing into a readable compass direction.

Supported directions include:

* North
* North-East
* East
* South-East
* South
* South-West
* West
* North-West

### 📡 Device Sensors

The application uses:

* **Accelerometer sensor**
* **Magnetic Field sensor**

The two sensors are combined using Android's sensor fusion functionality to calculate the compass orientation.

The application also displays the live X, Y, and Z sensor values.

### 📍 GPS Location

The application uses the Android location services to retrieve the device's location.

It displays:

* Latitude
* Longitude

The application requests the required location permissions before accessing location information.

### 🔐 Permissions

The application uses:

```text
ACCESS_FINE_LOCATION
ACCESS_COARSE_LOCATION
```

Location access is requested at runtime using Android's permission system.

---

## 🛠️ Technologies Used

* **Kotlin**
* **Android Studio**
* **Android SDK**
* **Android Sensors API**
* **Accelerometer**
* **Magnetic Field Sensor**
* **Google Play Services Location**
* **FusedLocationProviderClient**
* **Android Runtime Permissions**
* **XML Layouts**
* **AppCompat**

---

## 📂 Project Structure

```text
AndroidApp3/
│
├── app/
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/
│           │       └── foziaakhtar/
│           │           └── androidapp3/
│           │               └── MainActivity.kt
│           │
│           ├── res/
│           │   ├── drawable/
│           │   │   └── ic_compass.xml
│           │   │
│           │   ├── layout/
│           │   │   └── activity_main.xml
│           │   │
│           │   ├── mipmap/
│           │   │   ├── ic_launcher
│           │   │   └── ic_launcher_round
│           │   │
│           │   └── values/
│           │       └── strings.xml
│           │
│           └── AndroidManifest.xml
│
└── README.md
```

---

## ⚙️ How the Compass Works

The application receives data from the accelerometer and magnetic field sensors.

The accelerometer provides information about gravity and device movement, while the magnetic field sensor provides information about the Earth's magnetic field.

Android's:

```kotlin
SensorManager.getRotationMatrix()
```

is used to combine the sensor information.

The resulting rotation matrix is then converted into orientation values using:

```kotlin
SensorManager.getOrientation()
```

The azimuth value is converted from radians into degrees to produce the compass bearing.

The compass image is then rotated using the calculated bearing.

---

## 📍 How Location Works

When the user presses:

```text
GET LOCATION
```

the application checks whether location permission has been granted.

If permission has not been granted, Android displays the location permission request.

After permission is granted, the application uses:

```kotlin
FusedLocationProviderClient
```

to retrieve the most recent available location.

The latitude and longitude are then displayed on the screen.

---

## ▶️ How to Run the Application

1. Open the project in **Android Studio**.
2. Allow Android Studio to sync the Gradle project.
3. Connect an Android device or start an Android emulator.
4. Select the **AndroidApp3** run configuration.
5. Click **Run ▶**.
6. Open the **Compass** application.
7. Move or rotate the device/emulator to test the compass.
8. Press **GET LOCATION** to display the location.

---

## 🧪 Testing

The application was tested using an Android emulator.

The following functionality was verified:

* [x] Application launches successfully
* [x] Compass image displays
* [x] Compass rotates
* [x] Bearing values update
* [x] Compass direction updates
* [x] Accelerometer values update
* [x] Magnetic field values update
* [x] Location permission works
* [x] Latitude is displayed
* [x] Longitude is displayed
* [x] GET LOCATION button works
* [x] Application builds successfully

---

## 🔒 Privacy

The application requests location access only when required by the GET LOCATION functionality.

Location information is displayed within the application and is not intentionally uploaded to an external server.

---

## 👩‍💻 Author

**Fozia Akhtar**

Android Development Project

---

## 📄 Project Status

**Status: Complete and Functional ✅**

The application successfully demonstrates Android sensor integration, sensor fusion, compass orientation, runtime permissions, and GPS location services.
