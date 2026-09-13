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

from Google Play Services Location to obtain location information.

🔄 Continuous Live Location

Assignment 6 extends the original GPS functionality by adding a dedicated Live Location screen.

The Live Location screen continuously receives updated location information while the activity is active.

The application uses:

LocationRequest
LocationCallback
FusedLocationProviderClient

Location updates are requested approximately every 2 seconds, with a minimum update interval of approximately 1 second.

The application updates:

Latitude
Longitude
Address
Location status

in real time.

Example status:

Status: LIVE LOCATION ACTIVE

This demonstrates continuous location tracking rather than retrieving the location only when a button is pressed.

🏠 Reverse Geocoding

The Live Location screen also converts geographic coordinates into a readable address.

The application uses Android's:

Geocoder

to perform reverse geocoding.

The process works as follows:

GPS Coordinates
       ↓
Latitude + Longitude
       ↓
Geocoder
       ↓
Readable Address

For example:

Latitude: 43.xxxxxx
Longitude: -79.xxxxxx

Address: Example Street, Ontario, Canada

If an address cannot be determined, the application displays an appropriate fallback message.

🗺️ OpenStreetMap

Assignment 6 also introduces an interactive map using OpenStreetMap.

The application uses the:

osmdroid

library to display OpenStreetMap tiles.

The map uses:

TileSourceFactory.MAPNIK

for the OpenStreetMap tile source.

The map allows the user to visually see geographic locations instead of only viewing latitude and longitude values.

📌 Map Marker

The Map screen uses an OpenStreetMap marker to identify the current location.

The application uses:

GeoPoint
MapView
Marker

to work with geographic coordinates and map locations.

The marker provides a visual representation of the user's location on the map.

☰ Toolbar Navigation

Assignment 6 adds toolbar menu navigation.

The toolbar provides access to:

Compass
Live Location
Map

The menu allows the user to move between the main compass screen and the new location/map features.

Navigation
Main Screen
    │
    ├── Compass
    │
    ├── Live Location
    │
    └── Map

The Live Location screen also includes a back button so the user can return to the previous screen.

🔐 Permissions

The application uses Android location permissions:

ACCESS_FINE_LOCATION
ACCESS_COARSE_LOCATION
INTERNET

Location permissions are requested at runtime when required.

The application checks whether location permission has been granted before requesting location information.

The application also handles cases where the user denies location permission.

🔄 Activity Lifecycle and Location Updates

The Live Location activity manages location updates according to the Android activity lifecycle.

When the Live Location screen becomes active:

onResume()
       ↓
Start location updates

When the screen is no longer active:

onPause()
       ↓
Stop location updates

This prevents unnecessary location updates when the Live Location screen is not being used.

The application uses:

FusedLocationProviderClient.removeLocationUpdates()

to stop continuous location updates.

🛠️ Technologies Used

The project was developed using the following technologies:

Kotlin
Android Studio
Android SDK
Android API 35
Gradle
XML Layouts
AppCompat
Material Components
Android Sensors API
Accelerometer
Magnetic Field Sensor
Google Play Services Location
FusedLocationProviderClient
LocationRequest
LocationCallback
Geocoder
OpenStreetMap
osmdroid
Android Runtime Permissions
📦 Main Dependencies

The project uses the following important dependencies:

AndroidX Core
androidx.core:core-ktx

Provides Kotlin extensions and Android core functionality.

AppCompat
androidx.appcompat:appcompat

Provides compatibility support for Android application components.

Material Components
com.google.android.material:material

Provides Material Design components.

Google Play Services Location
com.google.android.gms:play-services-location:21.3.0

Provides:

FusedLocationProviderClient
LocationRequest
LocationCallback
Location services
osmdroid
org.osmdroid:osmdroid-android:6.1.20

Provides OpenStreetMap map functionality.

📂 Project Structure

The main project structure is:

AndroidApp3/
│
├── app/
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
│           │               │
│           │               ├── LiveLocationActivity.kt
│           │               │
│           │               └── MapActivity.kt
│           │
│           ├── res/
│           │   │
│           │   ├── drawable/
│           │   │   └── ic_compass.xml
│           │   │
│           │   ├── layout/
│           │   │   │
│           │   │   ├── activity_main.xml
│           │   │   │
│           │   │   ├── activity_live_location.xml
│           │   │   │
│           │   │   └── activity_map.xml
│           │   │
│           │   ├── menu/
│           │   │   │
│           │   │   └── main_menu.xml
│           │   │
│           │   └── values/
│           │       │
│           │       └── strings.xml
│           │
│           └── AndroidManifest.xml
│
├── README.md
│
└── build.gradle.kts
🧩 Main Activities
MainActivity

MainActivity contains the original digital compass functionality.

Responsibilities include:

Compass display
Sensor management
Bearing calculation
Direction calculation
Sensor value display
Original GPS functionality
Toolbar menu navigation
LiveLocationActivity

LiveLocationActivity provides continuous location tracking.

Responsibilities include:

Location permission handling
Fused location provider
Continuous location updates
Latitude display
Longitude display
Reverse geocoding
Address display
Location status
Activity lifecycle management
MapActivity

MapActivity provides the OpenStreetMap interface.

Responsibilities include:

OpenStreetMap display
osmdroid configuration
Map tiles
Geographic coordinates
Map marker
Location services
Map interaction
⚙️ How the Compass Works

The application receives data from the accelerometer and magnetic field sensors.

The accelerometer provides information about gravity and device movement.

The magnetic field sensor provides information about the Earth's magnetic field.

Android's:

SensorManager.getRotationMatrix()

is used to combine the sensor information.

The resulting rotation matrix is then converted into orientation values using:

SensorManager.getOrientation()

The azimuth value is converted from radians into degrees to produce the compass bearing.

The bearing is normalized to a range of:

0° - 360°

The numerical bearing is then converted into a readable compass direction.

The compass image is rotated according to the calculated bearing.

📍 How the Original Location Feature Works

When the user presses:

GET LOCATION

the application checks whether location permission has been granted.

If permission has not been granted, Android displays the location permission request.

After permission is granted, the application uses:

FusedLocationProviderClient

to retrieve the most recent available location.

The latitude and longitude are then displayed on the screen.

🔄 How Continuous Location Works

The Live Location screen creates a location request using:

LocationRequest.Builder(
    Priority.PRIORITY_HIGH_ACCURACY,
    2000L
)

A minimum update interval is also configured.

The application registers a:

LocationCallback

with the fused location provider.

Whenever a new location is received, the callback updates the user interface.

The process is:

FusedLocationProviderClient
             ↓
       LocationRequest
             ↓
       LocationCallback
             ↓
       New Location
             ↓
    Latitude / Longitude
             ↓
        Geocoder
             ↓
      Address Display
🗺️ How the Map Works

The Map screen uses the osmdroid library.

The application configures osmdroid and uses the OpenStreetMap Mapnik tile source.

The map works with:

GeoPoint

to represent geographic coordinates.

The application then places a:

Marker

on the map to identify the location.

The map can be used to visually understand the geographic position represented by the latitude and longitude values.

▶️ How to Run the Application
Open the project in Android Studio.
Allow Android Studio to sync the Gradle project.
Connect an Android device or start an Android emulator.
Select the AndroidApp3 run configuration.
Click Run ▶.
Open the Compass application.
Grant location permissions when requested.
Move or rotate the device/emulator to test the compass.
Press GET LOCATION to test the original GPS feature.
Open the toolbar menu.
Select Live Location to test continuous location updates.
Select Map to open the OpenStreetMap screen.
🧪 Testing

The application was tested using an Android emulator.

The following functionality was tested:

Compass Testing
✅ Application launches successfully
✅ Compass image displays
✅ Compass image rotates
✅ Bearing values update
✅ Compass direction updates
✅ Accelerometer values update
✅ Magnetic field values update
Location Testing
✅ Location permission request works
✅ Location permission handling works
✅ Latitude is displayed
✅ Longitude is displayed
✅ GET LOCATION button works
✅ FusedLocationProviderClient works
Assignment 6 Testing
✅ Toolbar menu displays
✅ Live Location screen opens
✅ Continuous location updates are requested
✅ Location values update automatically
✅ Location status updates
✅ Reverse geocoding is implemented
✅ Address information is displayed
✅ Location updates stop when the activity is paused
✅ Location updates restart when the activity resumes
✅ Live Location back button works
✅ Map screen opens
✅ OpenStreetMap tiles load
✅ Map marker functionality is implemented
✅ Application builds successfully
🧪 Emulator Location Testing

The Android emulator can be used to simulate different geographic locations.

For example, a simulated location can be entered through:

Android Studio
      ↓
Emulator
      ↓
Extended Controls
      ↓
Location

This allows the Live Location and Map features to be tested without physically moving the Android device.

📝 Assignment 6 Enhancements

Assignment 6 expanded the original Digital Compass application with additional location and mapping functionality.

Original Application
Digital Compass
       +
GPS Location
Assignment 6
Digital Compass
       +
GPS Location
       +
Continuous Live Location
       +
Reverse Geocoding
       +
OpenStreetMap
       +
Map Marker
       +
Toolbar Navigation

These additions demonstrate the use of Android location APIs and third-party mapping functionality.

🔒 Privacy

The application requests location access only when required by its location features.

Location information is displayed within the application and is not intentionally uploaded to an external server by the application.

The application does not intentionally store the user's location history.

The OpenStreetMap functionality is used to display map information within the application.

📚 Learning Outcomes

This project demonstrates practical Android development concepts including:

Android Activity lifecycle
Kotlin programming
Android sensor APIs
Sensor fusion
Compass orientation
Runtime permissions
GPS location services
FusedLocationProviderClient
Continuous location updates
LocationRequest
LocationCallback
Reverse geocoding
Geocoder
OpenStreetMap
osmdroid
Map markers
Toolbar menus
XML layouts
AndroidManifest configuration
Gradle dependencies
Emulator testing
Git and GitHub version control
📌 Project Status

Status: Complete and Functional ✅

The application successfully demonstrates:

🧭 Digital compass functionality
📡 Android sensor integration
🔄 Sensor fusion
📍 GPS location
🔄 Continuous live location
🏠 Reverse geocoding
🗺️ OpenStreetMap integration
📌 Map markers
☰ Toolbar navigation
🔐 Runtime permissions
🧪 Emulator testing

The project was extended for Assignment 6 to provide continuous location updates and map functionality.

👩‍💻 Author

Fozia Akhtar

Android Development Project

📦 Assignment Information

Project: AndroidApp3
Assignment: Assignment 6
Platform: Android
Language: Kotlin
IDE: Android Studio
Repository: AndroidApp3


### One important thing

Your old README says the project is only a **Digital Compass**, but your current project is now much more than that. The new README correctly documents your **Assignment 6 additions** and matches the files you just pushed in commit `21816f8`.

**Don't push another commit yet unless you want to.** If you want this README on GitHub too, the next step is simply to replace `README.md`, save it, and then commit/push the README change.
