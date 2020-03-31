package com.example.fjempleada.activitys

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.fjempleada.R
import com.example.fjempleada.activitys.ui.Publicacion
import com.example.fjempleada.interfaces.ApiService
import com.example.fjempleada.modelo.startApi
import kotlinx.android.synthetic.main.activity_publicacion3.*
import java.time.LocalDate
import java.util.*

class Publicacion3 : AppCompatActivity() {
    var preferencias: SharedPreferences? =null
    var idUsuario:Int=0
    lateinit var progressDialog: ProgressDialog
    val start = startApi()
    lateinit var service: ApiService
    var pu= Publicacion()
    var titulo:String = ""
    var descrip:String = ""
    var log:String=""
    var lat:String=""
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publicacion3)
        val SHAREPRE = "usuario"
        preferencias=getSharedPreferences(SHAREPRE, Context.MODE_PRIVATE)
        idUsuario= preferencias?.getString("id","0")!!.toInt()
        var id:Int=0
        service = start.initApi()
        var fecha= LocalDate.now()

        var intent = getIntent().extras
        if (intent != null) {
            titulo = intent.getString("titulo", "")
            descrip = intent.getString("descrip", "")
            lat = intent.getString("lat","")
            log = intent.getString("long","")
        }
        btnpublicarTra.setOnClickListener() {
            if(txtdispo.text.isNotEmpty() && txtradio.text.isNotEmpty()){
                val intento = Intent(applicationContext, PubliInteres::class.java)
                intento.putExtra("titulo",titulo)
                intento.putExtra("descrip",descrip)
                intento.putExtra("lat",lat)
                intento.putExtra("long",log)
                intento.putExtra("extra",txtextra.text.toString())
                intento.putExtra("tarifa",txttarifa.text.toString())
                intento.putExtra("fecha",fecha.toString())
                intento.putExtra("dispo",txtdispo.text.toString())
                intento.putExtra("radio",txtradio.text.toString())
                startActivity(intento)
            }
            else{
                Toast.makeText(applicationContext, "Algún dato esta incompleto, llenalo, e intentalo de nuevo.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
