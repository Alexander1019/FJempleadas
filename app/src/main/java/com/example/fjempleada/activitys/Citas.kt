package com.example.fjempleada.activitys

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fjempleada.R
import com.example.fjempleada.interfaces.ApiService
import com.example.fjempleada.modelo.Solicitud
import com.example.fjempleada.modelo.startApi
import com.example.fjempleada.modelo.validationModel
import kotlinx.android.synthetic.main.activity_citas.*
import retrofit2.Call
import retrofit2.Response


class Citas : AppCompatActivity() {
    val start = startApi()
    lateinit var service: ApiService
    var preferencias: SharedPreferences?=null
    var soli= Solicitud()
    var validacion=validationModel()
    var empleada=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citas)
        validacion.hideButton(btnAceptar)
        service = start.initApi()
        validacion.context = this
        validacion.preferencias_user = getSharedPreferences(validacion.PREF_USUARIO, Context.MODE_PRIVATE)
       empleada= validacion.preferencias_user!!.getString("id","")!!.toInt()
        var intent = getIntent().extras
        if (intent != null) {
            soli.id=intent.getInt("idsoli")
            soli.idusuario=intent.getInt("idusuario")
            soli.estatus=intent.getInt("estatus")
            soli.titulo=intent.getString("titulo","").toString()
            soli.descripcion=intent.getString("descripcion","").toString()
            soli.fecha=intent.getString("fecha","").toString()
            soli.usuario=intent.getString("nombre","").toString()
            soli.apellido=intent.getString("apellido","").toString()
            soli.fcita=intent.getString("fecha_cita","").toString()
            soli.ftrabajo=intent.getString("fecha_tra","").toString()
            txtTitulo.setText(soli.titulo)
            txtnombreU.setText(soli.usuario+" "+soli.apellido)
            txtdescripcion.setText(soli.descripcion)
            txtfecha.setText(soli.fecha)
            txtCita.setText(soli.fcita)
            txtfechatra.setText(soli.ftrabajo)
            if(soli.estatus==1){
                btnAceptar.visibility=View.INVISIBLE
            }
            if(soli.estatus==2){
                btnsolicitar.visibility=View.INVISIBLE
                btnAceptar.visibility=View.VISIBLE
            }
            if(soli.estatus==3){
                btnsolicitar.visibility=View.INVISIBLE
                btnAceptar.visibility=View.INVISIBLE
                btnCancelar.visibility=View.INVISIBLE
            }

        }
        btnsolicitar.setOnClickListener(){
            soli.estatus=2
          service.updateEstatus(soli.id,soli.estatus).enqueue(object: retrofit2.Callback<String>{
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
                      "Se ha notificado al usuario que aceptado su cita",
                      Toast.LENGTH_LONG
                  ).show()
                  var intento= Intent(applicationContext, MainActivity::class.java)
                  startActivity(intento)
              }
          })


        }
        btnAceptar.setOnClickListener(){
            soli.estatus=3
            service.updateEstatus(soli.id,soli.estatus).enqueue(object: retrofit2.Callback<String>{
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
                        "Se ha notificado al usuario que aceptado el trabajo",
                        Toast.LENGTH_LONG
                    ).show()
                    var intento= Intent(applicationContext, MainActivity::class.java)
                    startActivity(intento)
                }

            })
        }
        btnCancelar.setOnClickListener(){
            soli.estatus=4//cancelado
            service.updateEstatus(soli.id,soli.estatus).enqueue(object: retrofit2.Callback<String>{
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        "Algo salio mal",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    var builder: AlertDialog.Builder  =  AlertDialog.Builder(this@Citas)
                    builder.setTitle("Reportar insidencia")
                    builder.setMessage("Porfavor se ha amable de explicar su desinterés")
                    builder.setPositiveButton("Aceptar"
                    ) { dialog, which ->
                        var intent=Intent(applicationContext,Insidencias::class.java)
                        intent.putExtra("idusuario", soli.idusuario)
                        intent.putExtra("idemp",empleada)
                        startActivity(intent)
                    }
                    builder.show()
                }

            })

        }
    }
}
