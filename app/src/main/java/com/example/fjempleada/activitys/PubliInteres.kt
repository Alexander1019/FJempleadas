package com.example.fjempleada.activitys

import android.content.*
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fjempleada.R
import com.example.fjempleada.activitys.ui.Home
import com.example.fjempleada.activitys.ui.Publicacion
import com.example.fjempleada.adapter.adpInteres
import com.example.fjempleada.interfaces.ApiService
import com.example.fjempleada.modelo.interesModel
import com.example.fjempleada.modelo.startApi
import com.example.fjempleada.modelo.validationModel
import com.google.gson.Gson
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.activity_publi_interes.*
import kotlinx.android.synthetic.main.activity_registrointeres.*
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PubliInteres : AppCompatActivity() {
    val start = startApi()
    lateinit var service: ApiService
    var publi= com.example.fjempleada.modelo.Publicacion()
    val interes = interesModel()
    var validacion= validationModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publi_interes)
        service=start.initApi()
        getCategorias()
        validacion.context = this
        validacion.preferencias_user = getSharedPreferences(validacion.PREF_USUARIO, Context.MODE_PRIVATE)
        publi.idusuario = validacion.preferencias_user!!.getString("id","")!!.toInt()
        var intent = getIntent().extras
        if (intent != null) {
            publi.titulo =intent.getString("titulo","")
            publi.descripcion = intent.getString("descrip","")
            publi.extra = intent.getString("extra","")
            publi.tarifa = intent.getString("tarifa","").toInt()
            publi.fecha = intent.getString("fecha","")
            publi.dispo = intent.getString("dispo","")
            publi.radio=intent.getString("radio","")
            publi.estatus=1
            publi.log=intent.getString("long","")
            publi.lat=intent.getString("lat","")

        }
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(mensajeRecibido, IntentFilter("mensaje-id"))

        btnFinalizarPublicacion.setOnClickListener(){
            validacion.showProgress(progressBar)
            validacion.hideButton(btnFinalizarPublicacion)
            if (publi.idcategoria != 0) {
                AddPublicacion(publi,validacion.preferencias_user!! )
            } else {
                Toast.makeText(
                    applicationContext,
                    "Selececione algguna categoria de preferencia.",
                    Toast.LENGTH_SHORT
                ).show()
                validacion.hideProgress(progressBar)
                validacion.showButton(btnFinalizarPublicacion)
            }

        }

    }
    fun AddPublicacion(publi:com.example.fjempleada.modelo.Publicacion, preferencias: SharedPreferences){
        service.Addpublicacion(publi.idcategoria,publi.idusuario,publi.descripcion,publi.fecha,publi.tarifa,publi.extra,publi.estatus,
            publi.dispo,publi.log,publi.lat,publi.titulo,publi.radio).enqueue(object : retrofit2.Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                validacion.hideProgress(progressBar)
                validacion.showButton(btnFinalizarPublicacion)
                Toast.makeText(
                    applicationContext,
                    "Error Faltan datos por llenar",
                    Toast.LENGTH_SHORT
                ).show()

            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                var intento= Intent(applicationContext, MainActivity::class.java)
                startActivity(intento)
            }

        })
    }

    var mensajeRecibido: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            publi.idcategoria = intent.getStringExtra("id").toInt()
        }
    }
    fun getCategorias() {
        service.getCategorias().enqueue(object : Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                var rcv: RecyclerView? = null
                var adp: adpInteres? = null
                val lista = ArrayList<interesModel>()
                val json = Gson().toJson(response?.body())
                val array = JSONArray(json)
                for (i in 0 until array.length()) {
                    val row = array.getJSONObject(i)
                    interes.id = row.getString("id").toString()
                    interes.nombre = row.getString("nombre").toString()
                    interes.icono = row.getString("icono")
                    lista.add(interesModel(interes.id, interes.nombre, interes.icono))
                    rcv = rcvInteres2
                    adp = adpInteres(applicationContext, lista)
                }
                rcv?.layoutManager = GridLayoutManager(applicationContext, 2)
                rcv?.adapter = adp
            }
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {

            }


        })
    }
}
