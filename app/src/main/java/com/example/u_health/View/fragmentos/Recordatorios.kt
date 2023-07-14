package com.example.u_health.View.fragmentos

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
import com.example.u_health.model.databaseHelper
import com.google.android.material.snackbar.Snackbar


class Recordatorios : Fragment(),RecordatoriosListener
{
    private var _binding: FragmentRecordatoriosBinding? = null
    private val binding get() = _binding!!

    private lateinit var AdapterRecordatorios:AdapterRecordatorios


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

            Navigation.findNavController(view).navigate(R.id.tratamiento)

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
        AdapterRecordatorios= AdapterRecordatorios(databaseHelper(requireContext()).getRecordatorios_Medicamentos(),this)
        rv.adapter=AdapterRecordatorios
    }

    override fun EliminarRecordatorio(M: Medicamentos)
    {
        val builder=AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.strDialogTitulo))
            .setPositiveButton(getString(R.string.strAceptar)) { dialogInterface, i ->
                if (databaseHelper(requireContext()).deleteRecordatorios_Medicamentos(M))
                {
                    AdapterRecordatorios.Delete(M)
                    Snackbar.make(binding.root, "Se elimino el Recordatorio", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(binding.root, "Error al eliminar", Snackbar.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton(getString(R.string.strCancelar),null)
        builder.create().show()

    }

        override fun EditarRecordatorio(M: Medicamentos)
        {
            view?.let { Navigation.findNavController(it).navigate(R.id.vista_medicamento) }
            val Preferencias: SharedPreferences? = context?.getSharedPreferences("Datos_Recordatorio", Context.MODE_PRIVATE)
            val editor = Preferencias?.edit()
            editor?.putString("Id", M.Id.toString())
            editor?.putString("Pastilla", M.Pastilla)
            editor?.putString("Tipo", M.Tipo)
            editor?.putString("Frecuencia", M.Frecuencia)
            editor?.putString("Cantidad", M.Cantidad.toString())
            editor?.putString("Hora", M.Hora)
            editor?.apply()
        }



}