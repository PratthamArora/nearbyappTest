package com.example.nearbyapp.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nearbyapp.databinding.ActivityMainBinding
import com.example.nearbyapp.ui.adapter.CityAdapter
import com.example.nearbyapp.utils.Resource
import com.example.nearbyapp.viewmodel.CityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), LocationListener {

    private var longitude: Double = 0.0
    private var latitude: Double = 0.0
    private val viewModel by viewModels<CityViewModel>()
    private val cityAdapter by lazy { CityAdapter() }
    private lateinit var binding: ActivityMainBinding
    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // Initialize LocationManager
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        setupRecyclerView()
        addObservers()
        checkForLocationPermission()

    }


    private fun fetchLocation() {
        // Check if location services are enabled
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        ) {
            try {
                // Request location updates
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    1000, // Minimum time interval between updates (in milliseconds)
                    1.0f, // Minimum distance between updates (in meters)
                    this
                )
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        } else {

            Toast.makeText(this, "Location services are not enabled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkForLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        } else {
            // Permissions are already granted
            fetchLocation()
        }
    }

    private fun addObservers() {
        viewModel.cachedLocation.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    //                    hide ̰Progressbar

                    response.data?.let {
                        cityAdapter.addData(it, false, false)
                    }
                }

                is Resource.Error -> {
                    response.message?.let {
                        // show error mssg
                        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                    }
                }

                is Resource.Loading -> {
                    //                    showProgressbar
                }
            }

        }

        viewModel.searchCity.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    //                    hideProgressbar

                    response.data?.let {
                        it.venues?.let { it1 -> cityAdapter.addData(it1, false, true) }
                    }
                }

                is Resource.Error -> {
                    response.message?.let {
                        // show error mssg
                        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                    }
                }

                is Resource.Loading -> {
                    //                    showProgressbar
                }
            }


        }
    }


    private fun setupRecyclerView() {
        cityAdapter.setListener(object : CityAdapter.LoadMoreData {
            override fun onLastItemReached() {
                viewModel.searchCity(latitude, longitude)
            }

        })
        binding.cityRecyclerView.apply {
            adapter = cityAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }
    }

    companion object {
        private const val locationPermissionCode = 1
    }

    override fun onLocationChanged(location: Location) {
        // Handle location updates here
        latitude = location.latitude
        longitude = location.longitude
        viewModel.searchCity(latitude, longitude)

        // Stop requesting location updates if needed
        locationManager.removeUpdates(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, fetch location
                fetchLocation()
            } else {
                // Permission denied, handle accordingly
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

}