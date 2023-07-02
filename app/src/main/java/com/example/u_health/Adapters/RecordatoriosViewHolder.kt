package com.example.u_health.Adapters

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.u_health.R
import com.example.u_health.model.Medicamentos

class RecordatoriosViewHolder (view: View) : RecyclerView.ViewHolder(view)
{
    val Nombre_Pastilla = view.findViewById<TextView>(R.id.txtNombrePastilla)
    val Frecuencia_Pastilla =view.findViewById<TextView>(R.id.txtFrecuenciaPills)
    val Cantidad_Pastilla = view.findViewById<TextView>(R.id.txtCantidadPills)

    fun render(meds: Medicamentos)
    {
        Nombre_Pastilla.text=meds.Pastilla
        Frecuencia_Pastilla.text="${meds.Dosis} - ${meds.Hora}"
        Cantidad_Pastilla.text="${meds.Cantidad} capsula(s) restantes"
    }
}
