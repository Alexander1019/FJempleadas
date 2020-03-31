package com.example.fjempleada.activitys.ui


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.fjempleada.R
import com.example.fjempleada.activitys.Solicitudes
import com.example.fjempleada.adapter.AdPublicacion
import com.example.fjempleada.interfaces.ApiService
import com.example.fjempleada.modelo.VerPubli
import com.example.fjempleada.modelo.startApi
import com.example.fjempleada.modelo.validationModel
import com.google.gson.Gson
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_post.*
import kotlinx.android.synthetic.main.item_post.view.*
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class Home : Fragment() {
    val start = startApi()
    lateinit var service: ApiService
    var preferencias: SharedPreferences?=null
    var publi= VerPubli()

    var validacion= validationModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var id:Int=0
        var SHAREPRE="usuario"
        preferencias = context!!.getSharedPreferences(SHAREPRE, Context.MODE_PRIVATE)
        id=preferencias?.getString("id","0")!!.toInt()
        service=start.initApi()
        Mispublicaciones(id)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root= inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }
    fun Mispublicaciones(id:Int){

        service.getPublicacion(id).enqueue(object: retrofit2.Callback<JsonArray>{
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {

            }

            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                var rcv: RecyclerView? = null
                var adp: AdPublicacion? = null
                var lista=ArrayList<VerPubli>()
                val json = Gson().toJson(response?.body())//asi vas desglosando los datos que traen
                val array = JSONArray(json)
                for (i in 0 until array.length()) {
                    val row = array.getJSONObject(i)
                    publi.idpublicacion=row.getInt("idpublicacion")
                    publi.titulo=row.getString("Titulo").toString()
                    publi.descripcion=row.getString("descripcion").toString()
                    publi.fecha=row.getString("fecha").toString()
                    publi.tarifa=row.getString("tarifa").toString()
                    publi.nombre=row.getString("empleada").toString()
                    lista.add(VerPubli(publi.idpublicacion,publi.titulo,publi.descripcion,publi.tarifa,publi.fecha,publi.nombre))
                    rcv = rcvPublicaciones
                    adp = context?.let { AdPublicacion(it,lista)  }

                }
                rcv?.layoutManager = GridLayoutManager(context, 1)
                rcv?.adapter = adp
            }

        })
    }

}
