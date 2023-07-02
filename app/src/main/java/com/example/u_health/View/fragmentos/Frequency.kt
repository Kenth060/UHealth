package com.example.u_health.View.fragmentos

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import com.example.u_health.Adapters.AdapterRecordatorios
import com.example.u_health.Adapters.MedicamentosProvider
import com.example.u_health.R
import com.example.u_health.databinding.FragmentFrequencyBinding
import com.example.u_health.databinding.VistaFrecuenciaBinding
import com.example.u_health.databinding.VistaFrecuenciaDosisBinding
import com.example.u_health.model.Medicamentos


class Frequency : Fragment() {

    private lateinit var cantidad : String
    private lateinit var popupWindow: PopupWindow
    private lateinit var adapter_recordatorios : AdapterRecordatorios
    private var _binding: FragmentFrequencyBinding? = null
    private val binding get() = _binding!!

    private var _bindingVF: VistaFrecuenciaBinding? = null
    private val bindingVF get() = _bindingVF!!
    private var _bindingVFD: VistaFrecuenciaDosisBinding? = null
    private val bindingVFD get() = _bindingVFD!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFrequencyBinding.inflate(inflater, container, false)
        _bindingVF = VistaFrecuenciaBinding.inflate(inflater, container, false)
        _bindingVFD = VistaFrecuenciaDosisBinding.inflate(inflater, container, false)
        val view = binding.root


        valida()
        bindingVFD.btnAceptar.setOnClickListener {
            cantidad = bindingVFD.cantidadCapsules.text.toString()
            binding.txtDosis.setText("$cantidad")
            popupWindow.dismiss()
        }

        binding.btnSiguiente.setOnClickListener {
            if(!binding.txtHora.text.equals("Hora")&&
                !binding.txtDosis.text.equals("Dosis"))
            {

                val sharedPreferences = context?.getSharedPreferences("mi_pref", Context.MODE_PRIVATE)
                val medicamentoSeleccionado = sharedPreferences?.getString("selectedItem", "")
                val frecuenciaDatosSeleccionado = sharedPreferences?.getString("frecuenciaDato", "")
                val hora =  binding.txtHora.text.toString()
                val dosis = binding.txtDosis.text.toString()

                if (medicamentoSeleccionado != null && frecuenciaDatosSeleccionado != null)
                { MedicamentosProvider.Recordatorios_Meds.add( Medicamentos(medicamentoSeleccionado,frecuenciaDatosSeleccionado,hora,dosis)) }

                Toast.makeText(requireContext(), "Guardado", Toast.LENGTH_SHORT).show()


                //luego de guardar los datos lo limpiamos el shared por si agrega
                //otro recordatorio y no queden guardados los mismos datos
                val editor = sharedPreferences?.edit()
                editor?.clear()
                editor?.apply()
                Navigation.findNavController(view).navigate(R.id.navigation_recordatorios)
            }
            else
            {
                Toast.makeText(requireContext(), "Rellene los datos", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
    private fun valida() {
        actividadHora(
            binding.txtHora,
            bindingVF.alturaVista
        )
        dosis()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun dosis(){
        binding.txtDosis.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val touchX = event.x.toInt() // Posición X del evento táctil
                val imageWidth = binding.txtDosis.compoundDrawablesRelative[2]?.bounds?.width() ?: 0 // Ancho de la imagen

                // Ajustar las coordenadas para la nueva posición del TextView
                val textViewEndPosition = binding.txtDosis.width
                val touchThreshold = textViewEndPosition - imageWidth

                // Verificar si el evento táctil ocurrió dentro de la región de la imagen
                if (touchX >= binding.txtDosis.width - touchThreshold) {
                    showWindowFloat(0.89,bindingVFD.root)
                }
            }
            true
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun showWindowFloat(width: Double, popupView: View) {
        val widthPixels = (resources.displayMetrics.widthPixels * width).toInt()
        val heightPixels = (resources.displayMetrics.heightPixels * 0.4).toInt()
        //condicion para verificar si el popupView tiene una vsta asignada
        if (popupView.parent != null) {
            (popupView.parent as ViewGroup).removeView(popupView)
        }
        popupWindow = PopupWindow(popupView, widthPixels, heightPixels, true)
        popupWindow.showAtLocation(popupView.rootView, Gravity.CENTER, 0, 0)
    }
    @SuppressLint("ClickableViewAccessibility")
    private fun actividadHora(TextViewActividadHora: TextView
                              , vistaHora: ConstraintLayout
    ) {

        TextViewActividadHora.setOnTouchListener { _, event ->
            codeClick(vistaHora,event,TextViewActividadHora)
        }
    }
    private fun codeClick(vista: ConstraintLayout, event: MotionEvent,
                          TextViewActividad: TextView
    ): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val touchX = event.x.toInt() // Posición X del evento táctil
            val imageWidth = TextViewActividad.compoundDrawablesRelative[2]?.bounds?.width() ?: 0 // Ancho de la imagen

            // Ajustar las coordenadas para la nueva posición del TextView
            val textViewEndPosition = TextViewActividad.width
            val touchThreshold = textViewEndPosition - imageWidth

            // Verificar si el evento táctil ocurrió dentro de la región de la imagen
            if (touchX >= TextViewActividad.width - touchThreshold) {
                vista.visibility = View.VISIBLE
                showTimePicker()
            }
        }
        return true
    }
    private fun showTimePicker(){
        val timepicker = TimePickerFragment{currentDate(it)}
        val fragmentManager = requireActivity().supportFragmentManager
        timepicker.show(fragmentManager, "timepicker")
    }

    private fun currentDate(time : String) {
        binding.txtHora.text = "$time"

    }

    fun Add_Recordatorio()
    {}

}