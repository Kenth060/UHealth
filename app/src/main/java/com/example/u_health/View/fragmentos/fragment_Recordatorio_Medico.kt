package com.example.u_health.View.fragmentos

import android.annotation.SuppressLint
import android.content.Context
import android.media.tv.TimelineRequest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import com.example.u_health.R
import com.example.u_health.databinding.FragmentCitasBinding
import com.example.u_health.databinding.FragmentRecordatorioMedicoBinding
import com.example.u_health.model.Citas
import com.example.u_health.model.Medicamentos
import com.example.u_health.model.databaseHelper

class fragment_Recordatorio_Medico : Fragment()
{


    private var _binding: FragmentRecordatorioMedicoBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment
        _binding = FragmentRecordatorioMedicoBinding.inflate(inflater, container, false)
        val view = binding.root


        val toolbar: Toolbar = binding.tbCitasDetalles
        toolbar.title="Detalles de la cita"
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.navigationIcon?.setTint(R.color.textologin)
        toolbar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigation_citas)
        }


        val Prefencias = context?.getSharedPreferences("Datos_Citas", Context.MODE_PRIVATE)
        val Id = Prefencias?.getString("Id", "")
        val Titulo = Prefencias?.getString("Titulo", "")
        val Medico = Prefencias?.getString("Medico", "")
        val Fecha = Prefencias?.getString("Fecha", "")
        val Hora = Prefencias?.getString("Hora", "")
        val Detalles = Prefencias?.getString("Detalles", "")

        val editor = Prefencias?.edit()
        editor?.clear()
        editor?.apply()

        binding.txtInfoDr.text=Medico
        binding.txtTitulo.text=Titulo
        binding.txtFechaCitaDet.text=Fecha
        binding.txtHoraDet.text=Hora
        binding.txtCitaInfo.text=Detalles

        binding.btnEliminarCita.setOnClickListener {
            if (Id != null && Titulo != null && Medico != null && Fecha != null && Hora != null && Detalles != null)
            {
                var cita= Citas()
                cita.Id=Id.toLong()
                cita.Titulo=Titulo
                cita.Medico=Medico
                cita.Fecha=Fecha
                cita.Hora=Hora
                cita.Detalles=Detalles
                databaseHelper(requireContext()).deleteRecordatorios_Citas(cita)
            }
            Navigation.findNavController(view).navigate(R.id.navigation_citas)
        }


        return view
    }
}