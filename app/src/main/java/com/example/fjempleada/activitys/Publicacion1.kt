package com.example.fjempleada.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fjempleada.R
import kotlinx.android.synthetic.main.activity_publicacion1.*
import kotlinx.android.synthetic.main.activity_publicacion1.btnpublicarTra
import kotlinx.android.synthetic.main.activity_publicacion1.txtdescripcion
import kotlinx.android.synthetic.main.activity_publicacion1.txttitulo
import kotlinx.android.synthetic.main.fragment_historial.*

class Publicacion1 : AppCompatActivity() {
    var log:String=""
    var lat:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publicacion1)
        var intent = getIntent().extras
        if (intent != null) {
         lat=intent.getString("lat","")
         log=intent.getString("long","")
        }
        btnpublicarTra.setOnClickListener(){
            if(txttitulo.text.isNotEmpty() && txtdescripcion.text.isNotEmpty() ){
                val intento = Intent(applicationContext, Publicacion3::class.java)
                intento.putExtra("titulo",txttitulo.text.toString())
                intento.putExtra("descrip",txtdescripcion.text.toString())
                intento.putExtra("lat",lat)
                intento.putExtra("long",log)
                startActivity(intento)
            }
            else{
                Toast.makeText(applicationContext,  "Confirma los campos esten llenos", Toast.LENGTH_LONG).show()
            }
        }
    }
}
