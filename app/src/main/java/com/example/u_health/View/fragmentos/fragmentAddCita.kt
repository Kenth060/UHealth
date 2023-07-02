package com.example.u_health.View.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.u_health.Adapters.MedicamentosProvider
import com.example.u_health.R
import com.example.u_health.databinding.FragmentAddCitaBinding
import com.example.u_health.model.Citas

class fragmentAddCita : Fragment()
{
    private var _binding: FragmentAddCitaBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCitaBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnCancelar.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigation_citas)
        }
        binding.fecha.setOnClickListener {
            showDatePickerDialog()
        }
        binding.hora.setOnClickListener {
            showTimePicker()
        }


        binding.btnSave.setOnClickListener {
            var Titulo = binding.txtEspecialidad.text.toString()
            var Doctor = binding.txtDoctor.text.toString()
            var Fecha_Cita=binding.txtFecha.text.toString()
            var Hora = binding.txtHora.text.toString()
            var Detalles = binding.txtDetalles.text.toString()

            MedicamentosProvider.Recordatorios_Citas.add(Citas(Titulo,Doctor,Fecha_Cita,Hora,Detalles))
            Navigation.findNavController(view).navigate(R.id.navigation_citas)
        }


        return view
    }

    private fun showDatePickerDialog()
    {
        val datePicker = DatePickerFragment { dia, mes, year -> currentDate(dia, mes, year) }
        datePicker.show(requireFragmentManager(), "datepicker")
    }
    private fun currentDate(dia: Int, mes: Int, year: Int) {
        binding.txtFecha.text = "$dia / $mes / $year"
    }
    private fun showTimePicker(){
        val timepicker = TimePickerFragment{onTimeSelected(it)}
        timepicker.show(requireFragmentManager(),"timepicker")
    }

    private fun onTimeSelected(time : String) {
        binding.txtHora.text = "$time"
    }
}