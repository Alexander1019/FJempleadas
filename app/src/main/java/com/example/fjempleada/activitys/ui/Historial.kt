package com.example.fjempleada.activitys.ui


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.fjempleada.R
import com.example.fjempleada.activitys.Publicacion3
import com.example.fjempleada.activitys.publicacion2
import kotlinx.android.synthetic.main.fragment_historial.*
import kotlinx.android.synthetic.main.fragment_historial.view.*
//import android.support.v4.app.FragmentActivity


/**
 * A simple [Fragment] subclass.
 */
class Historial : Fragment() {
    lateinit var framentA:Publicacion
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root= inflater.inflate(R.layout.fragment_historial, container, false)
        root.txtdireccion.setOnClickListener(){
         /*   val fragment = Publicacion()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.contaner, fragment)
             transaction.commit()*/
        }
        root.btnpublicarTra.setOnClickListener(){
            if(txttitulo.text.isNotEmpty() && txtdescripcion.text.isNotEmpty() ){
                val intento = Intent(context, Publicacion3::class.java)
                intento.putExtra("titulo",txttitulo.text.toString())
                intento.putExtra("descrip",txtdescripcion.text.toString())
                Toast.makeText(context,  "Se ha tomado tu ubicacion actual para la publicación", Toast.LENGTH_SHORT).show()
                startActivity(intento)
            }
            else{
                Toast.makeText(context,  "Confirma los campos esten llenos", Toast.LENGTH_LONG).show()
            }
        }
        return  root
    }


}
