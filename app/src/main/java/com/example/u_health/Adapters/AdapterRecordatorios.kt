package com.example.u_health.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.u_health.R
import com.example.u_health.model.Medicamentos

class AdapterRecordatorios(var recordatoriosList: MutableList<Medicamentos>, val recordatoriosListener: RecordatoriosListener) : RecyclerView.Adapter<RecordatoriosViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordatoriosViewHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)

        return  RecordatoriosViewHolder(layoutInflater.inflate(R.layout.items_recordatorio, parent, false))
    }

    override fun onBindViewHolder(holder: RecordatoriosViewHolder, position: Int)
    {
        val item=recordatoriosList[position]
        holder.render(item)

        holder.itemView.setOnClickListener {
           recordatoriosListener.onRecordatorioClicked(item)
        }
    }

    override fun getItemCount(): Int = recordatoriosList.size

}