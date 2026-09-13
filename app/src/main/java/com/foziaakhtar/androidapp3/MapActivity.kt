package com.foziaakhtar.androidapp3


// =====================================================
// IMPORTS
// =====================================================

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

import java.util.Locale


// =====================================================
// MAP ACTIVITY
// =====================================================

/**
 * MapActivity
 *
 * Assignment 6 - AndroidApp3
 *
 * Displays an OpenStreetMap map using osmdroid.
 *
 * FEATURES:
 *
 * 1. OpenStreetMap
 * 2. Current GPS location
 * 3. Continuous location updates
 * 4. Location marker
 * 5. Zoom controls
 * 6. Toolbar navigation
 */
class MapActivity : AppCompatActivity() {


    // =====================================================
    // MAP
    // =====================================================

    private lateinit var mapView: MapView

    private lateinit var locationMarker: Marker


    // =====================================================
    // GPS
    // =====================================================

    private lateinit var fusedLocationClient:
            FusedLocationProviderClient


    // =====================================================
    // LOCATION REQUEST
    // =====================================================

    private val locationRequest =
        LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            2000L
        )
            .setMinUpdateIntervalMillis(1000L)
            .build()


    // =====================================================
    // LOCATION CALLBACK
    // =====================================================

    private val locationCallback =
        object : LocationCallback() {

            override fun onLocationResult(
                locationResult: LocationResult
            ) {

                for (location in locationResult.locations) {

                    updateMapLocation(
                        location
                    )
                }
            }
        }


    // =====================================================
    // ON CREATE
    // =====================================================

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(
            savedInstanceState
        )


        // =================================================
        // OPENSTREETMAP CONFIGURATION
        // =================================================

        Configuration.getInstance()
            .load(
                this,
                getSharedPreferences(
                    "osmdroid",
                    MODE_PRIVATE
                )
            )


        // =================================================
        // LOAD MAP LAYOUT
        // =================================================

        setContentView(
            R.layout.activity_map
        )


        // =================================================
        // TOOLBAR
        // =================================================

        val toolbar =
            findViewById<Toolbar>(
                R.id.toolbarMap
            )

        setSupportActionBar(
            toolbar
        )

        supportActionBar?.title =
            "Map"

        supportActionBar?.setDisplayHomeAsUpEnabled(
            true
        )


        // =================================================
        // CONNECT MAP
        // =================================================

        mapView =
            findViewById(
                R.id.mapView
            )


        // =================================================
        // MAP SETTINGS
        // =================================================

        mapView.setTileSource(
            TileSourceFactory.MAPNIK
        )

        mapView.setMultiTouchControls(
            true
        )

        mapView.setBuiltInZoomControls(
            true
        )


        // =================================================
        // DEFAULT MAP LOCATION
        // =================================================

        val defaultLocation =
            GeoPoint(
                43.2557,
                -79.8711
            )


        mapView.controller.setZoom(
            15.0
        )

        mapView.controller.setCenter(
            defaultLocation
        )


        // =================================================
        // CREATE LOCATION MARKER
        // =================================================

        locationMarker =
            Marker(
                mapView
            )

        locationMarker.title =
            "Current Location"

        locationMarker.setAnchor(
            Marker.ANCHOR_CENTER,
            Marker.ANCHOR_BOTTOM
        )


        // =================================================
        // ADD MARKER TO MAP
        // =================================================

        mapView.overlays.add(
            locationMarker
        )


        // =================================================
        // GPS SERVICE
        // =================================================

        fusedLocationClient =
            LocationServices
                .getFusedLocationProviderClient(
                    this
                )
    }


    // =====================================================
    // ON RESUME
    // =====================================================

    override fun onResume() {

        super.onResume()


        // =================================================
        // RESUME MAP
        // =================================================

        mapView.onResume()


        // =================================================
        // START LOCATION UPDATES
        // =================================================

        startLocationUpdates()
    }


    // =====================================================
    // ON PAUSE
    // =====================================================

    override fun onPause() {

        // =================================================
        // STOP LOCATION UPDATES
        // =================================================

        stopLocationUpdates()


        // =================================================
        // PAUSE MAP
        // =================================================

        mapView.onPause()


        super.onPause()
    }


    // =====================================================
    // START LOCATION UPDATES
    // =====================================================

    private fun startLocationUpdates() {

        // =================================================
        // CHECK LOCATION PERMISSION
        // =================================================

        if (
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            Toast.makeText(
                this,
                "Location permission is required.",
                Toast.LENGTH_LONG
            ).show()

            return
        }


        // =================================================
        // REQUEST GPS UPDATES
        // =================================================

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }


    // =====================================================
    // STOP LOCATION UPDATES
    // =====================================================

    private fun stopLocationUpdates() {

        fusedLocationClient.removeLocationUpdates(
            locationCallback
        )
    }


    // =====================================================
    // UPDATE MAP LOCATION
    // =====================================================

    private fun updateMapLocation(
        location: Location
    ) {

        // =================================================
        // CREATE GEO POINT
        // =================================================

        val currentLocation =
            GeoPoint(
                location.latitude,
                location.longitude
            )


        // =================================================
        // UPDATE MARKER POSITION
        // =================================================

        locationMarker.position =
            currentLocation


        locationMarker.title =
            String.format(
                Locale.US,
                "Current Location\n%.6f, %.6f",
                location.latitude,
                location.longitude
            )


        // =================================================
        // MOVE MAP TO CURRENT LOCATION
        // =================================================

        mapView.controller.animateTo(
            currentLocation
        )


        // =================================================
        // REFRESH MAP
        // =================================================

        mapView.invalidate()
    }


    // =====================================================
    // TOOLBAR MENU
    // =====================================================

    override fun onCreateOptionsMenu(
        menu: Menu
    ): Boolean {

        menuInflater.inflate(
            R.menu.main_menu,
            menu
        )

        return true
    }


    // =====================================================
    // MENU ITEM SELECTION
    // =====================================================

    override fun onOptionsItemSelected(
        item: MenuItem
    ): Boolean {

        return when (item.itemId) {


            // =============================================
            // COMPASS
            // =============================================

            R.id.action_compass -> {

                finish()

                true
            }


            // =============================================
            // LIVE LOCATION
            // =============================================

            R.id.action_live_location -> {

                finish()

                true
            }


            // =============================================
            // MAP
            // =============================================

            R.id.action_map -> {

                true
            }


            // =============================================
            // OTHER MENU ITEMS
            // =============================================

            else -> {

                super.onOptionsItemSelected(
                    item
                )
            }
        }
    }


    // =====================================================
    // BACK BUTTON
    // =====================================================

    override fun onSupportNavigateUp(): Boolean {

        finish()

        return true
    }

}