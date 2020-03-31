package com.example.fjempleada.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.fjempleada.R
import com.example.fjempleada.activitys.Solicitudes
import com.example.fjempleada.activitys.publicacion2
import com.example.fjempleada.modelo.VerPubli
import kotlinx.android.synthetic.main.item_post.view.*

class AdPublicacion(context: Context, lista:ArrayList<VerPubli>): RecyclerView.Adapter<AdPublicacion.MyViewHolder>() {


    val contex: Context? = context
    val lista: List<VerPubli> = lista


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val vista: View
        val layout: LayoutInflater = LayoutInflater.from(contex)
        vista = layout.inflate(R.layout.item_post, parent, false)

        return MyViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.titulo.setText(lista.get(position).titulo)
        holder.descripcion.setText(lista.get(position).descripcion)
        holder.tarifa.setText(lista.get(position).tarifa)
        holder.fecha.setText(lista.get(position).fecha)
        holder.nombre.setText(lista.get(position).nombre)
        holder.btnsolicitar.setOnClickListener(){
            var intento=Intent(contex,Solicitudes::class.java)
            intento.putExtra("id",lista.get(position).idpublicacion)
            intento.putExtra("titulo",lista.get(position).titulo)
            intento.putExtra("descrip",lista.get(position).descripcion)
            contex!!.startActivity(intento)
        }
    }

    class MyViewHolder(vista: View) : RecyclerView.ViewHolder(vista) {
        val titulo = vista.txtTitulo
        val categoria = vista.txtCategoria
        val descripcion = vista.txtdescripcion
        val fecha = vista.txtpost_fecha
        val tarifa = vista.txtTarifa
        val nombre = vista.txtnombre
       var btnsolicitar:Button=vista.btnsolicitar
    }
}
