package org.tat.sdksample.recommendedroute.map

import android.graphics.Color
import android.os.BadParcelableException
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import org.tat.sdksample.R
import org.th.tatsdk.common.TATRouteDay
import org.th.tatsdk.util.TATPathCompressor
import java.util.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private var map: GoogleMap? = null
    private var routeDay: TATRouteDay? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        if (intent != null) {
            routeDay = intent.getParcelableExtra("STOP")
        }
        setupToolbar()
        initInstances()
    }

    private fun initInstances() {
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.mapView) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun setupToolbar() {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val c = CameraUpdateFactory.newLatLngZoom(LatLng(13.09803, 99.2034776), 5.5f)
        map!!.moveCamera(c)
        routeDay?.let { day ->
            //Create map marker
            val marker = MarkerOptions()
            val builder = LatLngBounds.Builder()

            day.stops?.let {
                it.forEachIndexed { index, tatStop ->

                    tatStop?.geoLocation?.let {
                        val point = LatLng(it.latitude, it.longitude)
                        marker.position(point).title((index + 1).toString() + ". " + tatStop.name)
                        builder.include(point)
                        //draw marker
                        map!!.addMarker(marker)

                        try {
                            //Decode compress path to get list of latitude and longitude of route path
                            val points = TATPathCompressor.Decode(tatStop.compressedPath)

                            //Create polyline
                            val line = PolylineOptions()
                            when (tatStop.travelBy) {
                                "C" -> line.color(Color.RED)
                                "P" -> line.color(Color.BLUE)
                                "W" -> line.color(Color.GREEN)
                                else -> line.color(Color.MAGENTA)
                            }

                            line.width(4f)

                            for (tPoint in points) {
                                val p = LatLng(tPoint.latitude, tPoint.longitude)
                                line.add(p)
                                builder.include(p)
                            }
                            //draw line
                            map!!.addPolyline(line)

                        } catch (e: InputMismatchException) {
                            //in the case of latitude and longitude is out of range
                            e.printStackTrace()

                        }
                    }
                }
                val bounds = builder.build()
                val cu = CameraUpdateFactory.newLatLngBounds(bounds, 36)
                map!!.setOnMapLoadedCallback { map!!.animateCamera(cu) }

            }

        }


    }
}
