package com.example.friends

//import android.Manifest
//import android.content.pm.PackageManager
//import android.location.Location
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import androidx.core.app.ActivityCompat
//
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//import com.example.friends.databinding.ActivityMapsBinding
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.location.LocationServices
//
//class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
//    var currentLocation: Location? = null
//    var fusedLocationProviderClient: FusedLocationProviderClient? = null
//    val REQUEST_CODE = 101
//    private lateinit var binding: ActivityMapsBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityMapsBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
//        fetchLocation()
//    }
//
//    private fun fetchLocation() {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE
//            )
//            return
//        }
//        val task = fusedLocationProviderClient!!.lastLocation
//        task.addOnSuccessListener { location ->
//            if (location != null) {
//                currentLocation = location
//                val supportMapFragment =
//                    (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)
//                supportMapFragment!!.getMapAsync(this@MapsActivity)
//            }
//        }
//
//    }
//
//
//    override fun onMapReady(googleMap: GoogleMap) {
//        var latLng = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
//        var markerOptions = MarkerOptions().position(latLng).title("I AM HERE")
//        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
//        googleMap.addMarker(markerOptions)
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        when (requestCode) {
//            REQUEST_CODE -> {
////                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                if (grantResults.size>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                    fetchLocation()
//                }
//            }
//        }
//
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }
//}