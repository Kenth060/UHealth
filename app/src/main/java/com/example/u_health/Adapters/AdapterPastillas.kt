package com.example.u_health.Adapters

import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.u_health.R


class AdapterPastillas (private val pastillasList:List<String>, private val pastillasListener: PastillasListener): RecyclerView.Adapter<PastillasViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastillasViewHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)

        return  PastillasViewHolder(layoutInflater.inflate(R.layout.item_pastillas, parent, false))
    }

    override fun onBindViewHolder(holder: PastillasViewHolder, position: Int)
    {
        val item=pastillasList[position]
        holder.render(item)

        holder.itemView.setOnClickListener {
            pastillasListener.onPastillaClicked(holder.Nombre_Pastilla.text.toString())
        }
    }

    override fun getItemCount(): Int = pastillasList.size


}




