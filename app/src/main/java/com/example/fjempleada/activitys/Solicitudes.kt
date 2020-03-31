package com.example.fjempleada.activitys

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fjempleada.R
import com.example.fjempleada.adapter.AdSolicitud
import com.example.fjempleada.interfaces.ApiService
import com.example.fjempleada.modelo.Solicitud
import com.example.fjempleada.modelo.startApi
import com.google.gson.Gson
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.activity_solicitudes.*
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Response

class Solicitudes : AppCompatActivity() {
    val start = startApi()
    lateinit var service: ApiService
    var preferencias: SharedPreferences?=null
    var soli= Solicitud()
    var lista=ArrayList<Solicitud>()
    var idpu:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitudes)
        var id:Int=0
        var SHAREPRE="usuario"
        service = start.initApi()
        preferencias = getSharedPreferences(SHAREPRE, Context.MODE_PRIVATE)
        id=preferencias?.getString("id","0")!!.toInt()
        soli.nombre=preferencias?.getString("nombre","")!!.toString()
        var intent = getIntent().extras
        if (intent != null) {
            idpu = intent.getInt("id",0)
            var titulo = intent.getString("titulo", "")
            var descri = intent.getString("descrip", "")
            txtTitulo.setText(titulo)
            txtDescrip.setText(descri)
        }

        MisSolicitudes(idpu)
    }
    fun MisSolicitudes(id:Int){

        service.getSolicitud(id).enqueue(object: retrofit2.Callback<JsonArray>{
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {

            }

            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                var rcv: RecyclerView? = null
                var adp: AdSolicitud? = null
                val json = Gson().toJson(response?.body())//asi vas desglosando los datos que traen
                val array = JSONArray(json)
                for (i in 0 until array.length()) {
                    val row = array.getJSONObject(i)
                    soli.id=row.getInt("idsoli")
                    soli.idusuario=row.getInt("idusuario")
                    soli.titulo=row.getString("titulo").toString()
                    soli.descripcion=row.getString("descripcion").toString()
                    soli.usuario=row.getString("nombre").toString()
                    soli.apellido=row.getString("apellido")
                    soli.fecha=row.getString("fecha").toString()
                    soli.fcita=row.getString("fecha_cita").toString()
                    soli.ftrabajo=row.getString("fecha_tra")
                    soli.estatus=row.getInt("estatus")
                    soli.lat=row.getString("log").toString()
                    soli.log=row.getString("lat").toString()
                    lista.add(soli)
                    rcv = rcvSolicitudes
                    adp =  AdSolicitud(applicationContext,lista)

                }
                rcv?.layoutManager = GridLayoutManager(applicationContext, 1)
                rcv?.setAdapter(adp)
            }

        })
    }
}
