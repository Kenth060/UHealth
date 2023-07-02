package com.example.u_health.Adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.u_health.R


class PastillasViewHolder(view: View) : RecyclerView.ViewHolder(view)
{
    val Nombre_Pastilla = view.findViewById<TextView>(R.id.txtNombrePastilla)



    fun render(Pill:String)
    {
        Nombre_Pastilla.text=Pill
    }

}