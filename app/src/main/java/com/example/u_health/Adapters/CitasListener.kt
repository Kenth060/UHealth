package com.example.u_health.Adapters

import com.example.u_health.model.Citas
import com.example.u_health.model.Medicamentos


interface CitasListener
{
    fun onCitaClicked(C: Citas)

    fun EliminarCita(C: Citas)

    fun EditarCita(C: Citas)

}