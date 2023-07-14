package com.example.u_health.View.fragmentos

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.u_health.Adapters.AdapterCitas
import com.example.u_health.Adapters.AdapterRecordatorios
import com.example.u_health.Adapters.CitasListener
import com.example.u_health.Adapters.MedicamentosProvider
import com.example.u_health.R
import com.example.u_health.databinding.FragmentCitasBinding
import com.example.u_health.model.Citas
import com.example.u_health.model.databaseHelper
import com.google.android.material.snackbar.Snackbar


class FragmentCitas : Fragment(),CitasListener
{

    private var _binding: FragmentCitasBinding? = null
    private val binding get() = _binding!!
    private lateinit var AdapterCitas: AdapterCitas

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
        AdapterCitas= AdapterCitas(databaseHelper(requireContext()).getRecordatorios_Citas(),this)
        rv.adapter= AdapterCitas
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

    override fun EliminarCita(C: Citas)
    {
        val builder= AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.strDialogTitulos))
            .setPositiveButton(getString(R.string.strAceptar)) { dialogInterface, i ->
                if (databaseHelper(requireContext()).deleteRecordatorios_Citas(C))
                {
                    AdapterCitas.Delete(C)
                    Snackbar.make(binding.root, "Se elimino la cita", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(binding.root, "Error al eliminar", Snackbar.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton(getString(R.string.strCancelar),null)
        builder.create().show()
    }

    override fun EditarCita(C: Citas)
    {
        view?.let { Navigation.findNavController(it).navigate(R.id.editar_citas) }
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