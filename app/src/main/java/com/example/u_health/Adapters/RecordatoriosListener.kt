package com.example.u_health.Adapters

import com.example.u_health.model.Medicamentos

interface RecordatoriosListener
{
    //fun onRecordatorioClicked(M:Medicamentos)

    fun EliminarRecordatorio(M: Medicamentos)

    fun EditarRecordatorio(M: Medicamentos)
}