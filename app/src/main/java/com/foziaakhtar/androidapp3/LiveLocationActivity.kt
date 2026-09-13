
package com.foziaakhtar.androidapp3

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import java.util.Locale


// =====================================================
// ASSIGNMENT 6 - LIVE LOCATION ACTIVITY
// =====================================================
//
// This screen provides continuous GPS location updates.
//
// Features:
// - Live latitude
// - Live longitude
// - Current address
// - GPS status
// - Back arrow to return to Compass
// - Location permission handling
// - Automatic location updates every 2 seconds
//
// =====================================================

class LiveLocationActivity : AppCompatActivity() {

    // =====================================================
    // LOCATION SERVICES
    // =====================================================

    private lateinit var fusedLocationClient:
            FusedLocationProviderClient

    private lateinit var locationCallback:
            LocationCallback


    // =====================================================
    // SCREEN TEXT VIEWS
    // =====================================================

    private lateinit var latitudeText:
            TextView

    private lateinit var longitudeText:
            TextView

    private lateinit var addressText:
            TextView

    private lateinit var statusText:
            TextView


    // =====================================================
    // LOCATION PERMISSION REQUEST
    // =====================================================

    private val locationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            val fineLocation =
                permissions[
                    Manifest.permission.ACCESS_FINE_LOCATION
                ] ?: false

            val coarseLocation =
                permissions[
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ] ?: false


            if (fineLocation || coarseLocation) {

                statusText.text =
                    "Status: Location permission granted"

                startLocationUpdates()

            } else {

                statusText.text =
                    "Status: Location permission denied"
            }
        }


    // =====================================================
    // ON CREATE
    // =====================================================

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_live_location
        )


        // =================================================
        // BACK ARROW
        // =================================================
        //
        // IMPORTANT:
        // The XML uses a TextView for the arrow.
        //
        // Therefore Kotlin MUST also use TextView.
        //
        // Tapping the arrow closes LiveLocationActivity
        // and returns to the Compass screen.
        //
        // =================================================

        val backButton =
            findViewById<TextView>(
                R.id.liveLocationBackButton
            )

        backButton.setOnClickListener {

            finish()
        }


        // =================================================
        // CONNECT TEXT VIEWS
        // =================================================

        latitudeText =
            findViewById(
                R.id.liveLatitudeText
            )

        longitudeText =
            findViewById(
                R.id.liveLongitudeText
            )

        addressText =
            findViewById(
                R.id.liveAddressText
            )

        statusText =
            findViewById(
                R.id.liveStatusText
            )


        // =================================================
        // INITIALIZE LOCATION CLIENT
        // =================================================

        fusedLocationClient =
            LocationServices
                .getFusedLocationProviderClient(this)


        // =================================================
        // LOCATION CALLBACK
        // =================================================

        locationCallback =
            object : LocationCallback() {

                override fun onLocationResult(
                    locationResult: LocationResult
                ) {

                    for (
                    location
                    in locationResult.locations
                    ) {

                        updateLocationOnScreen(
                            location
                        )
                    }
                }
            }


        // =================================================
        // CHECK LOCATION PERMISSION
        // =================================================

        checkLocationPermission()
    }


    // =====================================================
    // CHECK LOCATION PERMISSION
    // =====================================================

    private fun checkLocationPermission() {

        val fineLocationGranted =
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED


        val coarseLocationGranted =
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED


        if (
            fineLocationGranted ||
            coarseLocationGranted
        ) {

            startLocationUpdates()

        } else {

            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }


    // =====================================================
    // START CONTINUOUS LOCATION UPDATES
    // =====================================================

    private fun startLocationUpdates() {

        val fineLocationGranted =
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED


        val coarseLocationGranted =
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED


        if (
            !fineLocationGranted &&
            !coarseLocationGranted
        ) {

            statusText.text =
                "Status: Location permission required"

            return
        }


        // =================================================
        // LOCATION REQUEST
        // =================================================
        //
        // Request a new location approximately every
        // 2 seconds.
        //
        // =================================================

        val locationRequest =
            LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                2000L
            )
                .setMinUpdateIntervalMillis(
                    1000L
                )
                .build()


        // =================================================
        // REQUEST LIVE LOCATION
        // =================================================

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            mainLooper
        )


        statusText.text =
            "Status: Waiting for live location..."
    }


    // =====================================================
    // UPDATE SCREEN WITH LOCATION
    // =====================================================

    private fun updateLocationOnScreen(
        location: Location
    ) {

        latitudeText.text =
            "Latitude: ${location.latitude}"


        longitudeText.text =
            "Longitude: ${location.longitude}"


        statusText.text =
            "Status: LIVE LOCATION ACTIVE"


        getAddress(
            location.latitude,
            location.longitude
        )
    }


    // =====================================================
    // CONVERT GPS COORDINATES INTO ADDRESS
    // =====================================================

    private fun getAddress(
        latitude: Double,
        longitude: Double
    ) {

        try {

            val geocoder =
                Geocoder(
                    this,
                    Locale.getDefault()
                )


            // =================================================
            // ANDROID 13+
            // =================================================

            if (
                Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.TIRAMISU
            ) {

                geocoder.getFromLocation(
                    latitude,
                    longitude,
                    1
                ) { addresses ->

                    if (
                        addresses.isNotEmpty()
                    ) {

                        val address =
                            addresses[0]
                                .getAddressLine(0)


                        runOnUiThread {

                            addressText.text =
                                "Address: $address"
                        }
                    }
                }

            } else {

                // =================================================
                // OLDER ANDROID
                // =================================================

                @Suppress("DEPRECATION")

                val addresses =
                    geocoder.getFromLocation(
                        latitude,
                        longitude,
                        1
                    )


                if (
                    !addresses.isNullOrEmpty()
                ) {

                    addressText.text =
                        "Address: ${
                            addresses[0]
                                .getAddressLine(0)
                        }"
                }
            }

        } catch (
            exception: Exception
        ) {

            addressText.text =
                "Address: Unable to determine address"
        }
    }


    // =====================================================
    // STOP LOCATION UPDATES WHEN SCREEN PAUSES
    // =====================================================

    override fun onPause() {

        super.onPause()


        if (
            ::fusedLocationClient.isInitialized &&
            ::locationCallback.isInitialized
        ) {

            fusedLocationClient
                .removeLocationUpdates(
                    locationCallback
                )
        }
    }


    // =====================================================
    // RESTART LOCATION UPDATES WHEN SCREEN RESUMES
    // =====================================================

    override fun onResume() {

        super.onResume()


        if (
            ::fusedLocationClient.isInitialized
        ) {

            checkLocationPermission()
        }
    }


    // =====================================================
    // ANDROID BACK BUTTON
    // =====================================================
    //
    // Pressing the emulator/device Back button also
    // returns to the Compass screen.
    //
    // =====================================================

    override fun onBackPressed() {

        finish()

        super.onBackPressed()
    }
}


