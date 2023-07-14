package com.example.u_health.Adapters

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.u_health.R
import com.example.u_health.model.Citas

class CitasViewHolder (view: View) : RecyclerView.ViewHolder(view)
{

    val Titulo: TextView = view.findViewById<TextView>(R.id.txtTituloCita)
    private val Fecha: TextView = view.findViewById<TextView>(R.id.txtFechaCita)
    val BotonEditar: ImageView = view.findViewById<ImageView>(R.id.btnEditarCita)

    @SuppressLint("SetTextI18n")
    fun render(cita:Citas)
    {
        Titulo.text="${cita.Titulo} / Dr(a).${cita.Medico}"
        Fecha.text="Proxima cita: ${cita.Fecha} a las ${cita.Hora}"
    }
}