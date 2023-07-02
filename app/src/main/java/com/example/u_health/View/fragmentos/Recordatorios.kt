package com.example.u_health.View.fragmentos

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.u_health.Adapters.AdapterRecordatorios
import com.example.u_health.Adapters.MedicamentosProvider
import com.example.u_health.Adapters.RecordatoriosListener
import com.example.u_health.R
import com.example.u_health.databinding.FragmentRecordatoriosBinding
import com.example.u_health.model.Medicamentos


class Recordatorios : Fragment(),RecordatoriosListener
{
    private var _binding: FragmentRecordatoriosBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        _binding = FragmentRecordatoriosBinding.inflate(inflater, container, false)
        val view = binding.root

        initRecyclerView()
        
        
        binding.btnAdd.setOnClickListener {

            Navigation.findNavController(view).navigate(R.id.fragmentDatosSearch2)

            binding.btnAdd.visibility = View.INVISIBLE
        }
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView()
    {
        val rv = binding.rvRecordatorios
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter=AdapterRecordatorios(MedicamentosProvider.Recordatorios_Meds,this)
    }

    override fun onRecordatorioClicked(M: Medicamentos)
    {
        view?.let { Navigation.findNavController(it).navigate(R.id.vista_medicamento) }
        val Preferencias: SharedPreferences? = context?.getSharedPreferences("Datos_Recordatorio", Context.MODE_PRIVATE)
        val editor = Preferencias?.edit()
        editor?.putString("Pastilla", M.Pastilla)
        editor?.putString("Cantidad", M.Cantidad)
        editor?.putString("Dosis", M.Dosis)
        editor?.putString("Hora", M.Hora)
        editor?.apply()
    }

}