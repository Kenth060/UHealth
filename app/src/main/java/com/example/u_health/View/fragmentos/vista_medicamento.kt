package com.example.u_health.View.fragmentos

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.u_health.Adapters.MedicamentosProvider
import com.example.u_health.R
import com.example.u_health.databinding.FragmentVistaMedicamentoBinding
import com.example.u_health.model.Medicamentos


class vista_medicamento : Fragment()
{
    private var _binding: FragmentVistaMedicamentoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        _binding = FragmentVistaMedicamentoBinding.inflate(inflater, container, false)
        val view = binding.root

        val toolbar: Toolbar = binding.tbRecordatoriosDetalles
        toolbar.title="Detalles del Recordatorio"
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.navigationIcon?.setTint(R.color.textologin)
        toolbar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigation_recordatorios)
        }

        val Prefencias = context?.getSharedPreferences("Datos_Recordatorio", Context.MODE_PRIVATE)
        val Pastilla = Prefencias?.getString("Pastilla", "")
        val Cantidad = Prefencias?.getString("Cantidad", "")
        val Dosis = Prefencias?.getString("Dosis", "")
        val Hora = Prefencias?.getString("Hora", "")

        val editor = Prefencias?.edit()
        editor?.clear()
        editor?.apply()

        binding.txtPastillaRecordatorio.text=Pastilla
        binding.txtCantidadPills.text="$Cantidad capsula(s) restantes"
        binding.txDosisRecordatorios.text=Dosis
        binding.txHoraRecordatorios.text=Hora

        binding.btnEliminarRecordatorio.setOnClickListener {
            MedicamentosProvider.Recordatorios_Meds.remove(Medicamentos(Pastilla.toString(),Dosis.toString(),Hora.toString(), Cantidad.toString()))
            Navigation.findNavController(view).navigate(R.id.navigation_recordatorios)
        }


        return view
    }

}