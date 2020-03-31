package com.example.fjempleada.activitys.ui


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.fjempleada.R
import com.example.fjempleada.activitys.MainActivity
import com.example.fjempleada.activitys.publicacion2
import com.example.fjempleada.interfaces.ApiService
import com.example.fjempleada.modelo.fotoModel
import com.example.fjempleada.modelo.startApi
import com.example.fjempleada.modelo.usuarioModel
import com.example.fjempleada.modelo.validationModel
import com.google.gson.Gson
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.fragment_perfil.*
import kotlinx.android.synthetic.main.fragment_perfil.view.*
import okhttp3.MultipartBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.Exception

class Perfil : Fragment(), View.OnClickListener {

    private var foto:fotoModel?=null
    private var root:View?=null
    val start = startApi()
    private lateinit var service: ApiService
    private var validacion = validationModel()
    var usu=usuarioModel()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_perfil, container, false)
        service = start.initApi()
        foto= fotoModel(root!!.context)

        root!!.imgPerfil.setOnClickListener(this)
        validacion.preferencias_user=root!!.context.getSharedPreferences(validacion.PREF_USUARIO,Context.MODE_PRIVATE)
        usu.idusu=validacion.preferencias_user!!.getString("id","")!!.toInt()
        cargarperfil(usu.idusu)
        root!!.btnTerminar.setOnClickListener(){
            Toast.makeText(context, "La imagen se aguardado con exito", Toast.LENGTH_LONG).show()
            var intento=Intent(context,publicacion2::class.java)
            startActivity(intento)
        }

        return root
    }


    override fun onClick(v: View?) {
        if (root!!.imgPerfil==v){
            foto!!.abrirGaleria()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            if (resultCode== Activity.RESULT_OK){
                if (requestCode==10) {
                    var uri = data?.data
                    val bitmap=foto!!.reducirResolucion(uri.toString(),850,1100)
                    val uriImage = foto?.reducirPeso(bitmap)
                    val imagen = foto?.getImagenURL(uriImage)
                    val file = File(imagen)
                    val filePart = foto?.multiPart(file)
                    root!!.imgPerfil.setImageBitmap(bitmap)
                    uploadImage(filePart!!)
                }
            }
        }catch (e: Exception){}
    }
    fun uploadImage(file: MultipartBody.Part){
        service.loadImage(file).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val json = Gson().toJson(response?.body())
                addfotoperfil(json.replace("\"", ""), validacion.preferencias_user!!.getString("id", "")!!.toInt())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }
    fun addfotoperfil (img:String, id:Int){
        service.Updatefotoperfil(img,id).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val json = Gson().toJson(response?.body())
                Toast.makeText(context, json.toString(), Toast.LENGTH_LONG).show()
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }
    fun cargarperfil(id:Int){
        service.GetPersona(id).enqueue(object:Callback<JsonArray>{
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {

            }

            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                val json = Gson().toJson(response?.body())//asi vas desglosando los datos que traen
                val array = JSONArray(json)
                for (i in 0 until array.length()) {
                    val row = array.getJSONObject(i)
                    usu.nombre=row.getString("nombre").toString()
                    usu.apellido=row.getString("apellido").toString()
                    usu.telefono=row.getString("telefono").toString()
                    usu.fotoperfil=row.getString("fotoperfil").toString()
                    txtusuario.setText(usu.nombre+""+usu.apellido)
                    txtcorreo.setText("lu1@gmail.com")
                    txttel.setText(usu.telefono)

                }
            }

        })

    }
}
