package com.foziaakhtar.androidapp3

// =====================================================
// IMPORTS
// =====================================================

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import kotlin.math.roundToInt


// =====================================================
// MAIN ACTIVITY
// =====================================================

/**
 * MainActivity
 *
 * Assignment 6 - AndroidApp3
 *
 * This activity contains the compass screen.
 *
 * FEATURES:
 *
 * 1. Accelerometer sensor
 * 2. Magnetic field sensor
 * 3. Compass bearing
 * 4. Compass direction
 * 5. Compass image rotation
 * 6. GET LOCATION button
 * 7. Latitude and longitude
 * 8. Toolbar menu navigation
 *
 * TOOLBAR MENU:
 *
 * - Compass
 * - Live Location
 * - Map
 */
class MainActivity : AppCompatActivity(),
    SensorEventListener {


    // =====================================================
    // SENSOR VARIABLES
    // =====================================================

    private lateinit var sensorManager: SensorManager

    private var accelerometer: Sensor? = null

    private var magneticField: Sensor? = null


    // =====================================================
    // SENSOR DATA
    // =====================================================

    private val gravity =
        FloatArray(3)

    private val magnetic =
        FloatArray(3)

    private val rotationMatrix =
        FloatArray(9)

    private val orientation =
        FloatArray(3)


    // =====================================================
    // GPS
    // =====================================================

    private lateinit var fusedLocationClient:
            FusedLocationProviderClient


    // =====================================================
    // SCREEN ELEMENTS
    // =====================================================

    private lateinit var compassImage:
            ImageView

    private lateinit var bearingText:
            TextView

    private lateinit var directionText:
            TextView

    private lateinit var compassStatusText:
            TextView

    private lateinit var accelerometerText:
            TextView

    private lateinit var magneticFieldText:
            TextView

    private lateinit var latitudeText:
            TextView

    private lateinit var longitudeText:
            TextView


    // =====================================================
    // LOCATION PERMISSION
    // =====================================================

    private val locationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            val fineLocationGranted =
                permissions[
                    Manifest.permission.ACCESS_FINE_LOCATION
                ] == true

            val coarseLocationGranted =
                permissions[
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ] == true


            // =================================================
            // PERMISSION GRANTED
            // =================================================

            if (
                fineLocationGranted ||
                coarseLocationGranted
            ) {

                getCurrentLocation()

            }


            // =================================================
            // PERMISSION DENIED
            // =================================================

            else {

                Toast.makeText(
                    this,
                    "Location permission is required.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }


    // =====================================================
    // ON CREATE
    // =====================================================

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)


        // =================================================
        // LOAD MAIN LAYOUT
        // =================================================

        setContentView(
            R.layout.activity_main
        )


        // =================================================
        // TOOLBAR
        // =================================================

        val toolbar =
            findViewById<Toolbar>(
                R.id.toolbar
            )

        setSupportActionBar(toolbar)

        supportActionBar?.title =
            "Compass"


        // =================================================
        // CONNECT SCREEN ELEMENTS
        // =================================================

        compassImage =
            findViewById(
                R.id.compassImage
            )

        bearingText =
            findViewById(
                R.id.bearingText
            )

        directionText =
            findViewById(
                R.id.directionText
            )

        compassStatusText =
            findViewById(
                R.id.compassStatusText
            )

        accelerometerText =
            findViewById(
                R.id.accelerometerText
            )

        magneticFieldText =
            findViewById(
                R.id.magneticFieldText
            )

        latitudeText =
            findViewById(
                R.id.latitudeText
            )

        longitudeText =
            findViewById(
                R.id.longitudeText
            )


        // =================================================
        // GET LOCATION BUTTON
        // =================================================

        val getLocationButton =
            findViewById<Button>(
                R.id.getLocationButton
            )


        getLocationButton.setOnClickListener {

            checkLocationPermission()
        }


        // =================================================
        // SENSOR MANAGER
        // =================================================

        sensorManager =
            getSystemService(
                SENSOR_SERVICE
            ) as SensorManager


        // =================================================
        // ACCELEROMETER
        // =================================================

        accelerometer =
            sensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER
            )


        // =================================================
        // MAGNETIC FIELD SENSOR
        // =================================================

        magneticField =
            sensorManager.getDefaultSensor(
                Sensor.TYPE_MAGNETIC_FIELD
            )


        // =================================================
        // CHECK ACCELEROMETER
        // =================================================

        if (accelerometer == null) {

            Toast.makeText(
                this,
                "Accelerometer sensor is not available.",
                Toast.LENGTH_LONG
            ).show()
        }


        // =================================================
        // CHECK MAGNETIC SENSOR
        // =================================================

        if (magneticField == null) {

            Toast.makeText(
                this,
                "Magnetic field sensor is not available.",
                Toast.LENGTH_LONG
            ).show()
        }


        // =================================================
        // GPS SERVICE
        // =================================================

        fusedLocationClient =
            LocationServices
                .getFusedLocationProviderClient(
                    this
                )


        // =================================================
        // INITIAL COMPASS STATUS
        // =================================================

        compassStatusText.text =
            "Compass status: Starting..."
    }


    // =====================================================
    // CHECK LOCATION PERMISSION
    // =====================================================

    private fun checkLocationPermission() {

        val fineLocation =
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED


        val coarseLocation =
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED


        // =================================================
        // PERMISSION ALREADY GRANTED
        // =================================================

        if (
            fineLocation ||
            coarseLocation
        ) {

            getCurrentLocation()
        }


        // =================================================
        // REQUEST PERMISSION
        // =================================================

        else {

            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }


    // =====================================================
    // GET CURRENT LOCATION
    // =====================================================

    private fun getCurrentLocation() {

        // =================================================
        // DOUBLE CHECK LOCATION PERMISSION
        // =================================================

        if (
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            Toast.makeText(
                this,
                "Location permission is not granted.",
                Toast.LENGTH_LONG
            ).show()

            return
        }


        // =================================================
        // GET LAST KNOWN LOCATION
        // =================================================

        fusedLocationClient.lastLocation

            .addOnSuccessListener {
                    location: Location? ->

                if (location != null) {

                    latitudeText.text =
                        String.format(
                            "Latitude: %.6f",
                            location.latitude
                        )

                    longitudeText.text =
                        String.format(
                            "Longitude: %.6f",
                            location.longitude
                        )

                }


                // =========================================
                // LOCATION NOT AVAILABLE
                // =========================================

                else {

                    latitudeText.text =
                        "Latitude: Location unavailable"

                    longitudeText.text =
                        "Longitude: Location unavailable"

                    Toast.makeText(
                        this,
                        "No GPS location available.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }


            // =================================================
            // LOCATION ERROR
            // =================================================

            .addOnFailureListener {

                latitudeText.text =
                    "Latitude: Error"

                longitudeText.text =
                    "Longitude: Error"

                Toast.makeText(
                    this,
                    "Unable to obtain location.",
                    Toast.LENGTH_LONG
                ).show()
            }
    }


    // =====================================================
    // ON RESUME
    // =====================================================

    override fun onResume() {

        super.onResume()


        // =================================================
        // REGISTER ACCELEROMETER
        // =================================================

        accelerometer?.let {

            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_UI
            )
        }


        // =================================================
        // REGISTER MAGNETIC FIELD SENSOR
        // =================================================

        magneticField?.let {

            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_UI
            )
        }
    }


    // =====================================================
    // ON PAUSE
    // =====================================================

    override fun onPause() {

        super.onPause()


        // =================================================
        // STOP SENSOR LISTENERS
        // =================================================

        sensorManager.unregisterListener(
            this
        )
    }


    // =====================================================
    // SENSOR CHANGED
    // =====================================================

    override fun onSensorChanged(
        event: SensorEvent
    ) {

        // =================================================
        // IDENTIFY SENSOR
        // =================================================

        when (event.sensor.type) {


            // =================================================
            // ACCELEROMETER
            // =================================================

            Sensor.TYPE_ACCELEROMETER -> {

                gravity[0] =
                    event.values[0]

                gravity[1] =
                    event.values[1]

                gravity[2] =
                    event.values[2]


                accelerometerText.text =
                    String.format(
                        "Accelerometer\nX: %.2f\nY: %.2f\nZ: %.2f",
                        gravity[0],
                        gravity[1],
                        gravity[2]
                    )
            }


            // =================================================
            // MAGNETIC FIELD
            // =================================================

            Sensor.TYPE_MAGNETIC_FIELD -> {

                magnetic[0] =
                    event.values[0]

                magnetic[1] =
                    event.values[1]

                magnetic[2] =
                    event.values[2]


                magneticFieldText.text =
                    String.format(
                        "Magnetic Field\nX: %.2f\nY: %.2f\nZ: %.2f",
                        magnetic[0],
                        magnetic[1],
                        magnetic[2]
                    )
            }
        }


        // =====================================================
        // SENSOR FUSION
        // =====================================================

        val success =
            SensorManager.getRotationMatrix(
                rotationMatrix,
                null,
                gravity,
                magnetic
            )


        if (success) {

            // =================================================
            // GET DEVICE ORIENTATION
            // =================================================

            SensorManager.getOrientation(
                rotationMatrix,
                orientation
            )


            // =================================================
            // CALCULATE BEARING
            // =================================================

            var bearing =
                Math.toDegrees(
                    orientation[0].toDouble()
                ).toFloat()


            // =================================================
            // CONVERT NEGATIVE BEARING
            // =================================================

            if (bearing < 0) {

                bearing += 360f
            }


            // =================================================
            // DISPLAY BEARING
            // =================================================

            bearingText.text =
                String.format(
                    "Bearing: %.0f°",
                    bearing
                )


            // =================================================
            // DISPLAY DIRECTION
            // =================================================

            directionText.text =
                getDirection(
                    bearing
                )


            // =================================================
            // UPDATE COMPASS STATUS
            // =================================================

            compassStatusText.text =
                "Compass status: Active"


            // =================================================
            // ROTATE COMPASS IMAGE
            // =================================================

            compassImage.rotation =
                -bearing
        }
    }


    // =====================================================
    // GET COMPASS DIRECTION
    // =====================================================

    private fun getDirection(
        bearing: Float
    ): String {

        val directions =
            arrayOf(
                "North",
                "North-East",
                "East",
                "South-East",
                "South",
                "South-West",
                "West",
                "North-West"
            )


        // =================================================
        // CALCULATE DIRECTION INDEX
        // =================================================

        val index =
            (
                    (bearing + 22.5f) /
                            45f
                    )
                .roundToInt() % 8


        return directions[index]
    }


    // =====================================================
    // SENSOR ACCURACY
    // =====================================================

    override fun onAccuracyChanged(
        sensor: Sensor?,
        accuracy: Int
    ) {

        // No action required.
    }


    // =====================================================
    // TOOLBAR MENU
    // =====================================================

    /**
     * Creates the toolbar menu.
     *
     * Menu options:
     *
     * - Compass
     * - Live Location
     * - Map
     */
    override fun onCreateOptionsMenu(
        menu: Menu
    ): Boolean {

        // =================================================
        // LOAD MENU XML
        // =================================================

        menuInflater.inflate(
            R.menu.main_menu,
            menu
        )


        return true
    }


    // =====================================================
    // MENU ITEM SELECTION
    // =====================================================

    /**
     * Handles toolbar menu selections.
     *
     * Opens the appropriate activity
     * when the user selects an option.
     */
    override fun onOptionsItemSelected(
        item: MenuItem
    ): Boolean {

        return when (item.itemId) {


            // =================================================
            // COMPASS
            // =================================================

            R.id.action_compass -> {

                // =================================================
                // COMPASS IS ALREADY THE MAIN SCREEN
                // =================================================

                // No new activity is required.
                true
            }


            // =================================================
            // LIVE LOCATION
            // =================================================

            R.id.action_live_location -> {

                // =================================================
                // OPEN LIVE LOCATION SCREEN
                // =================================================

                startActivity(
                    Intent(
                        this,
                        LiveLocationActivity::class.java
                    )
                )


                true
            }


            // =================================================
            // MAP
            // =================================================

            R.id.action_map -> {

                // =================================================
                // OPEN MAP SCREEN
                // =================================================

                startActivity(
                    Intent(
                        this,
                        MapActivity::class.java
                    )
                )


                true
            }


            // =================================================
            // OTHER MENU ITEMS
            // =================================================

            else -> {

                super.onOptionsItemSelected(
                    item
                )
            }
        }
    }

}
