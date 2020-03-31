package com.example.fjempleada.activitys

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fjempleada.R
import com.example.fjempleada.interfaces.ApiService
import com.example.fjempleada.modelo.documento
import com.example.fjempleada.modelo.startApi
import com.example.fjempleada.modelo.validationModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_publicacion2.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class publicacion2 : AppCompatActivity() {
    var documento:documento?=null
    val READ_REQUEST_CODE: Int = 0

    val start = startApi()
    private lateinit var service: ApiService
    private var validacion = validationModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publicacion2)
        service = start.initApi()
        validacion.preferencias_user = getSharedPreferences(validacion.PREF_USUARIO, Context.MODE_PRIVATE)
        txtdocumentos.setOnClickListener{
            var intento: Intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intento.addCategory(Intent.CATEGORY_OPENABLE)
            intento.setType("application/pdf")
            startActivityForResult(intento,READ_REQUEST_CODE)

        }
        btnver.setOnClickListener(){
           var builder:AlertDialog.Builder  =  AlertDialog.Builder(this)
            builder.setTitle("Documentos Solicitados")
            builder.setMessage("1.INE" + "\n" + "2.Acta de nacimiento"+" \n" +"3.Antecedentes no penales")
            builder.show()
        }
        btndocumento.setOnClickListener(){

        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
             var uri = data?.data


        }
    }

}
