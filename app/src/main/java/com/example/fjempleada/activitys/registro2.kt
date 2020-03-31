package com.example.fjempleada.activitys

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import com.example.fjempleada.R
import com.example.fjempleada.activitys.ui.Perfil
import com.example.fjempleada.interfaces.ApiService
import com.example.fjempleada.modelo.fotoModel
import com.example.fjempleada.modelo.startApi
import com.example.fjempleada.modelo.usuarioModel
import com.example.fjempleada.modelo.validationModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_registro2.*
import kotlinx.android.synthetic.main.activity_registro2.view.*
import kotlinx.android.synthetic.main.fragment_perfil.view.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.Exception
import java.util.*

class registro2 : AppCompatActivity() {
    val start = startApi()//con esto inicias la conexion con la base de datos o webservices
    var c: Calendar = Calendar.getInstance()
    var year=c.get(Calendar.YEAR)
    var month=c.get(Calendar.MONTH)
    var day=c.get(Calendar.DAY_OF_MONTH)
    lateinit var service: ApiService
    val usuario = usuarioModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro2)

        txtfecha.setOnClickListener(){
            val dp= DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view: DatePicker?, myear: Int, mmonth: Int, dayOfMonth: Int ->
                txtfecha.setText("" + dayOfMonth + "/" + mmonth + "/" + myear )
            },year,month,day)
            dp.show()
        }
        var intent = getIntent().extras
        if (intent != null) {
            usuario.nombre = intent.getString("nombre","")
            usuario.apellido = intent.getString("apellido","")
            usuario.email = intent.getString("email","")
            usuario.pass = intent.getString("pass","")
            usuario.telefono =intent.getString("telefono","")
        }

        btnregistrar.setOnClickListener() {
            if (  txtcurp.text.isNotEmpty() && txtfecha.text.isNotEmpty()) {
                val intento = Intent(applicationContext, registrointeres::class.java)
                intento.putExtra("nombre",usuario.nombre)
                intento.putExtra("apellido",usuario.apellido)
                intento.putExtra("email",usuario.email)
                intento.putExtra("pass",usuario.pass)
                intento.putExtra("telefono",usuario.telefono)
                intento.putExtra("curp",txtcurp.text.toString())
                intento.putExtra("fecha",txtfecha.text.toString())
                startActivity(intento)
            }
            else{
                Toast.makeText(applicationContext, "Algún dato esta incompleto, llenalo, e intentalo de nuevo.", Toast.LENGTH_SHORT).show()
            }
        }
    }





}


