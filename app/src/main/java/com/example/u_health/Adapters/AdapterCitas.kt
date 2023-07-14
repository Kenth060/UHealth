package com.example.u_health.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.u_health.R
import com.example.u_health.model.Citas
import com.example.u_health.model.Medicamentos


class AdapterCitas (var citasList: MutableList<Citas>, val citasListener: CitasListener) : RecyclerView.Adapter<CitasViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitasViewHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)

        return  CitasViewHolder(layoutInflater.inflate(R.layout.items_citas, parent, false))
    }

    override fun onBindViewHolder(holder: CitasViewHolder, position: Int)
    {
        val item=citasList[position]
        holder.render(item)

        holder.itemView.setOnClickListener {
            citasListener.onCitaClicked(item)
        }

        holder.itemView.setOnLongClickListener {
            citasListener.EliminarCita(item)
            return@setOnLongClickListener false
        }

        holder.BotonEditar.setOnClickListener {
            citasListener.EditarCita(item)
        }

    }

    override fun getItemCount(): Int = citasList.size

    fun Delete(C: Citas)
    {
        citasList.remove(C)
        notifyDataSetChanged()
    }

}