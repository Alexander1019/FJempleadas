package com.example.fjempleada.activitys

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.fjempleada.R
import com.example.fjempleada.interfaces.ApiService
import com.example.fjempleada.modelo.Insidencia
import com.example.fjempleada.modelo.startApi
import kotlinx.android.synthetic.main.activity_insidencias.*
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDate

class Insidencias : AppCompatActivity() {

    var insi=Insidencia()
    val start = startApi()
    lateinit var service: ApiService

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insidencias)
        service = start.initApi()
        var intent = getIntent().extras
        if (intent != null) {
            insi.usuarioRepor=intent.getInt("idusuario")
            insi.idusuario=intent.getInt("idemp")
            txtusuario.setText(insi.usuarioRepor.toString())
            insi.estatus=2
            insi.tipo=1
            var fecha= LocalDate.now()
            insi.fecha=fecha.toString()
        }
        btnreportar.setOnClickListener(){
            service.Addinsidencia(txtMensaje.text.toString(),insi.idusuario,insi.usuarioRepor,insi.estatus,insi.tipo,insi.fecha).enqueue(object:retrofit2.Callback<String>{
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        "Algo salio mal",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Toast.makeText(
                        applicationContext,
                        "Se reportado al usuario",
                        Toast.LENGTH_LONG
                    ).show()
                    var intento= Intent(applicationContext, MainActivity::class.java)
                    startActivity(intento)
                }

            })
        }
    }
}
