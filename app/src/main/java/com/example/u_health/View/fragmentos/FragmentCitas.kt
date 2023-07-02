package com.example.u_health.View.fragmentos

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.u_health.Adapters.AdapterCitas
import com.example.u_health.Adapters.CitasListener
import com.example.u_health.Adapters.MedicamentosProvider
import com.example.u_health.R
import com.example.u_health.databinding.FragmentCitasBinding
import com.example.u_health.model.Citas
import com.example.u_health.model.databaseHelper


class FragmentCitas : Fragment(),CitasListener
{

    private var _binding: FragmentCitasBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCitasBinding.inflate(inflater, container, false)
        val view = binding.root


        binding.btnAddCita.setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.fragmentAddCita)

            binding.btnAddCita.visibility = View.INVISIBLE
        }

        initRecyclerView()

        return view
    }

    private fun initRecyclerView()
    {
        val rv = binding.rvCitas
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter= AdapterCitas(databaseHelper(requireContext()).getRecordatorios_Citas(),this)
    }

    override fun onCitaClicked(C: Citas)
    {
        view?.let { Navigation.findNavController(it).navigate(R.id.fragment_Recordatorio_Medico) }
        val Preferencias: SharedPreferences? = context?.getSharedPreferences("Datos_Citas", Context.MODE_PRIVATE)
        val editor = Preferencias?.edit()
        editor?.putString("Id", C.Id.toString())
        editor?.putString("Titulo", C.Titulo)
        editor?.putString("Medico",C.Medico)
        editor?.putString("Fecha", C.Fecha)
        editor?.putString("Hora", C.Hora)
        editor?.putString("Detalles", C.Detalles)
        editor?.apply()
    }


}