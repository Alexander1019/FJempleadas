package com.example.fjempleada.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.fjempleada.R
import com.example.fjempleada.activitys.Citas
import com.example.fjempleada.interfaces.ApiService
import com.example.fjempleada.modelo.Solicitud
import com.example.fjempleada.modelo.startApi
import com.google.gson.Gson
import kotlinx.android.synthetic.main.item_solicitud.view.*
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Response

class AdSolicitud(context: Context, lista:ArrayList<Solicitud>): RecyclerView.Adapter<AdSolicitud.MyViewHolder>() {
    val contex: Context? = context
    val lista: List<Solicitud> = lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val vista: View
        val layout: LayoutInflater = LayoutInflater.from(contex)
        vista=layout.inflate(R.layout.item_solicitud,parent,false)
        return MyViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if(lista.get(position).estatus==1) {

            holder.pendiente.visibility = View.INVISIBLE
            holder.aceptado.visibility = View.INVISIBLE
            holder.titulo.setText(lista.get(position).titulo)
            holder.nombre.setText(lista.get(position).nombre)
            holder.usuario.setText(lista.get(position).usuario)
            holder.apellido.setText(lista.get(position).apellido)

        }

        if(lista.get(position).estatus==2){//estatus 1=en busqueda 2=en cita 3=aceptado
            holder.vista.visibility = View.INVISIBLE
            holder.titulo.visibility = View.INVISIBLE
            holder.nombre.visibility = View.INVISIBLE
            holder.usuario.visibility = View.INVISIBLE
            holder.apellido.visibility = View.INVISIBLE
            holder.pendiente.visibility = View.VISIBLE
            holder.aceptado.visibility = View.INVISIBLE

        }
        if(lista.get(position).estatus==3){//estatus 1=en busqueda 2=en cita 3=aceptado
            holder.vista.visibility = View.INVISIBLE
            holder.titulo.visibility = View.INVISIBLE
            holder.nombre.visibility = View.INVISIBLE
            holder.usuario.visibility = View.INVISIBLE
            holder.apellido.visibility = View.INVISIBLE
            holder.pendiente.visibility = View.INVISIBLE
            holder.aceptado.visibility = View.VISIBLE
        }
        if(lista.get(position).estatus==4){//estatus 1=en busqueda 2=en cita 3=aceptado
            holder.vista.visibility = View.INVISIBLE
            holder.titulo.visibility = View.INVISIBLE
            holder.nombre.visibility = View.INVISIBLE
            holder.usuario.visibility = View.INVISIBLE
            holder.apellido.visibility = View.INVISIBLE
            holder.pendiente.visibility = View.INVISIBLE
            holder.aceptado.visibility = View.INVISIBLE
        }


        holder.btnSoli.setOnClickListener() {
            var intento = Intent(contex, Citas::class.java)
            intento.putExtra("idsoli",lista.get(position).id)
            intento.putExtra("idusuario",lista.get(position).idusuario)
            intento.putExtra("estatus",lista.get(position).estatus)
            intento.putExtra("titulo", lista.get(position).titulo)
            intento.putExtra("descripcion", lista.get(position).descripcion)
            intento.putExtra("nombre", lista.get(position).usuario)
            intento.putExtra("apellido", lista.get(position).apellido)
            intento.putExtra("fecha", lista.get(position).fecha)
            intento.putExtra("fecha_cita", lista.get(position).fcita)
            intento.putExtra("fecha_tra", lista.get(position).ftrabajo)
            contex!!.startActivity(intento)
        }

    }
    class MyViewHolder(vista: View): RecyclerView.ViewHolder(vista){
        val titulo=vista.txtTitulo
        val nombre=vista.txtnombre
        val usuario=vista.txtusu
        val apellido=vista.txtapellido
        var btnSoli:Button=vista.btnsolicitud
        var pendiente=vista.txtPendiente
        var aceptado=vista.txtaceptado
        var vista=vista.txtvista

    }

}