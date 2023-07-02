package com.example.u_health.Adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.u_health.R
import com.example.u_health.model.Citas

class CitasViewHolder (view: View) : RecyclerView.ViewHolder(view)
{

    val Titulo = view.findViewById<TextView>(R.id.txtTituloCita)
    val Fecha = view.findViewById<TextView>(R.id.txtFechaCita)

    fun render(cita:Citas)
    {
        Titulo.text="${cita.Titulo} / Dr(a).${cita.Medico}"
        Fecha.text="Proxima cita: ${cita.Fecha} a las ${cita.Hora}"
    }
}