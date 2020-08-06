package com.example.myapplication

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.trace
import kotlinx.android.synthetic.main.activity_maps.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import com.nifcloud.mbaas.core.NCMB
import com.nifcloud.mbaas.core.NCMBException
import com.nifcloud.mbaas.core.NCMBObject

import java.util.Locale

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    //private lateinit var mMap: GoogleMap
    private var mMap: GoogleMap? = null
    private var location: LatLng? = null
    var objlocation= NCMBObject("GPSinformation")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        NCMB.initialize(
            this.applicationContext,
            "9d7360c45809fb8de237cd6a66fd7d041e1a7093a3db547115d3193256311325",
            "c39345676c30089f40499227d61a45e73461374dd3d1837c71ab93c61ec25980"

        )
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // 皇居辺りの緯度経度
        location = LatLng(35.68, 139.76)

        /*NCMB.initialize(
            this.applicationContext,
            "9d7360c45809fb8de237cd6a66fd7d041e1a7093a3db547115d3193256311325",
            "c39345676c30089f40499227d61a45e73461374dd3d1837c71ab93c61ec25980")

         */


        //var objlocation = NCMBObject("testloc")

        // marker 追加
        mMap!!.addMarker(MarkerOptions().position(location!!).title("Tokyo"))
        // camera 移動
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))

        // タップした時のリスナーをセット
        mMap!!.setOnMapClickListener { tapLocation -> // tapされた位置の緯度経度
            location = LatLng(tapLocation.latitude, tapLocation.longitude)
            var str = String.format(Locale.US, "%.2f, %.2f", tapLocation.latitude, tapLocation.longitude)
            mMap!!.addMarker(MarkerOptions().position(location!!).title(str))
            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14f))

            println("step1")
           try {
                println("step2")
                objlocation.put("latitude", tapLocation.latitude)
                objlocation.put("longitude",tapLocation.longitude)
            }catch (e:NCMBException) {
                println("step3")
                e.printStackTrace()
            }
            objlocation.saveInBackground {}//{e-> }


        }

        // 長押しのリスナーをセット
        mMap!!.setOnMapLongClickListener { longpushLocation ->
            val newlocation = LatLng(longpushLocation.latitude, longpushLocation.longitude)
            mMap!!.addMarker(MarkerOptions().position(newlocation).title("" + longpushLocation.latitude + " :" + longpushLocation.longitude))
            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(newlocation, 14f))
        }
    }


}