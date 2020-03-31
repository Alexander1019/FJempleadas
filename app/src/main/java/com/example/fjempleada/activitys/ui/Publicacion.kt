package com.example.fjempleada.activitys.ui


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.fjempleada.R
import com.example.fjempleada.activitys.Insidencias
import com.example.fjempleada.activitys.Publicacion1
import com.example.fjempleada.modelo.mapaModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_publicacion.view.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class Publicacion : Fragment(), OnMapReadyCallback {

    private var root:View? = null
    private var locationManager:LocationManager?=null
    var criteria:Criteria? =null
    var provider:String? = null
    var mylocation: Location? = null
    var publi= com.example.fjempleada.modelo.Publicacion()
    private var mapa=mapaModel(this)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_publicacion, container, false)
        if (root!!.mapubicacion != null) {
            root!!.mapubicacion.onCreate(null)
            root!!.mapubicacion.onResume()
            root!!.mapubicacion.getMapAsync(this)

        }
        
        return root
    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        criteria = Criteria()
        provider = locationManager?.getBestProvider(criteria, false)
        mylocation = locationManager?.getLastKnownLocation(provider)
        validarGPS()


    }
    private fun validarGPS(){
        if (!locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            mapa.gpsNotEnabled(locationManager!!)
        }
    }

    override fun onMapReady(google: GoogleMap?) {
        try {
            mapa.estiloMapa(google)
            google!!.isMyLocationEnabled=true
            google!!.uiSettings.isMyLocationButtonEnabled=true
            val yo = LatLng(mylocation!!.latitude, mylocation!!.longitude)
            google.addMarker(MarkerOptions().position(yo).title("Mi posición actual").draggable(true))
            google.moveCamera(CameraUpdateFactory.newLatLngZoom(yo,14F))
           publi.lat = mylocation!!.latitude.toString()
            publi.log = mylocation!!.longitude.toString()
            var builder: AlertDialog.Builder  =  AlertDialog.Builder(context)
            builder.setTitle("Posicion para la publicacion")
            builder.setMessage("Esta es su posicion actual ¿desea tomarla como ubicaciond de la publicacion?")
            builder.setPositiveButton("Aceptar"
            ) { dialog, which ->
                var intent= Intent(context, Publicacion1::class.java)
                intent.putExtra("lat", publi.lat )
                intent.putExtra("long",publi.log)
                startActivity(intent)
            }
            builder.show()

        }catch (e:Exception){}

     }



}
