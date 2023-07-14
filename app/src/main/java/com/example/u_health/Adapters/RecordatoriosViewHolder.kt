package com.example.u_health.Adapters

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.u_health.R
import com.example.u_health.model.Medicamentos

class RecordatoriosViewHolder (view: View) : RecyclerView.ViewHolder(view)
{
    private val Nombre_Pastilla: TextView = view.findViewById<TextView>(R.id.txtNombrePastilla)
    private val Frecuencia_Pastilla: TextView =view.findViewById<TextView>(R.id.txtFrecuenciaPills)
    private val Cantidad_Pastilla: TextView = view.findViewById<TextView>(R.id.txtCantidadPills)
    val BotonEditar: ImageView = view.findViewById<ImageView>(R.id.btnEditarRecordatorio)

    @SuppressLint("SetTextI18n")
    fun render(meds: Medicamentos)
    {
        Nombre_Pastilla.text= "${meds.Pastilla } ${meds.Tipo}"
        Frecuencia_Pastilla.text="${meds.Frecuencia} - ${meds.Hora}"
        Cantidad_Pastilla.text="${meds.Cantidad} capsula(s) restantes"
    }
}
