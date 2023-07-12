package com.example.u_health.View.fragmentos

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
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
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import com.example.u_health.Adapters.AdapterRecordatorios
import com.example.u_health.Adapters.MedicamentosProvider
import com.example.u_health.R
import com.example.u_health.databinding.FragmentFrequencyBinding
import com.example.u_health.databinding.VistaFrecuenciaBinding
import com.example.u_health.databinding.VistaFrecuenciaDosisBinding
import com.example.u_health.model.Medicamentos
import com.example.u_health.model.databaseHelper
import java.util.Calendar
import java.util.Date
import kotlin.properties.Delegates

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

    private var Id_Recordatorio=0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFrequencyBinding.inflate(inflater, container, false)
        _bindingVF = VistaFrecuenciaBinding.inflate(inflater, container, false)
        _bindingVFD = VistaFrecuenciaDosisBinding.inflate(inflater, container, false)
        val view = binding.root

        val MisPreferencias = context?.getSharedPreferences("Ids", Context.MODE_PRIVATE)
        val Edit = MisPreferencias?.edit()
        Id_Recordatorio= MisPreferencias?.getInt("Id_Recordatorios",0)!!
        Id_Recordatorio = Id_Recordatorio!! + 1

        valida()
        bindingVFD.btnAceptar.setOnClickListener {
            cantidad = bindingVFD.cantidadCapsules.text.toString()
            binding.txtDosis.setText("$cantidad")
            popupWindow.dismiss()
        }

        createNotificacionChannnel()
        binding.btnSiguiente.setOnClickListener {
            if(!binding.txtHora.text.equals("Hora")&&
                !binding.txtDosis.text.equals("Dosis"))
            {
                scheduleNotification()
                val sharedPreferences = context?.getSharedPreferences("mi_pref", Context.MODE_PRIVATE)
                val medicamentoSeleccionado = sharedPreferences?.getString("selectedItem", "")
                val frecuenciaDatosSeleccionado = sharedPreferences?.getString("frecuenciaDato", "")
                val hora =  binding.txtHora.text.toString()
                val dosis = binding.txtDosis.text.toString()

               /* val MisPreferencias = context?.getSharedPreferences("Id's", Context.MODE_PRIVATE)
                val edit = MisPreferencias?.edit()
                edit?.putInt("Id_Recordatorios",1)
                edit?.apply()
                var Id= MisPreferencias?.getInt("Id_Recordatorios",0)*/

                if (medicamentoSeleccionado != null && frecuenciaDatosSeleccionado != null)
                {
                    var med=Medicamentos()
                    med.Id=Id_Recordatorio.toLong()
                    med.Pastilla=medicamentoSeleccionado
                    med.Dosis=frecuenciaDatosSeleccionado
                    med.Hora=hora
                    med.Cantidad=dosis.toInt()
                    databaseHelper(requireContext()).insertarRecordatorios_Medicamentos(med)

                    Edit?.putInt("Id_Recordatorios",Id_Recordatorio)
                    Edit?.apply()

                   /* Id++
                    edit?.putInt("Id_Recordatorios",Id)
                    edit?.apply()*/
                }

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
    private fun showTimePicker() {
        val timePicker = TimePickerFragment { hour, minute ->
            onTimeSelected(hour, minute as Int)
        }
        timePicker.show(requireFragmentManager(), "timePicker")
    }

    private fun onTimeSelected(hour: Int, minute: Int) {
        binding.txtHora.text = "$hour : $minute"
        val sharedPreferences = context?.getSharedPreferences("mi_pref", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString("hora", hour.toString())
        editor?.putString("minuto", minute.toString())
        editor?.apply()
    }
    private fun mensajeAlerta(time: Long, title: String, message: String) {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(requireContext())
        val timeFormat = android.text.format.DateFormat.getTimeFormat(requireContext())

        AlertDialog.Builder(requireContext())
            .setTitle("Notificacion")
            .setMessage(
                "Titulo: " + title+
                        "\nMensaje: "+message+
                        "\nHora: "+dateFormat.format(date) + " "+timeFormat.format(date))
            .setPositiveButton("Confirmar"){_,_ ->}
            .setNegativeButton("Cancelar"){_,_ ->}
            .show()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificacionChannnel() {
        val name = "Notif channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelRecordatorioID, name, importance)
        channel.description = desc

        val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
    private fun scheduleNotification()
    {
        val intent = Intent(requireContext(), Notificacion_recordatorio::class.java)
        val sharedPreferences = context?.getSharedPreferences("mi_pref", Context.MODE_PRIVATE)
        val medicamentoSeleccionado = sharedPreferences?.getString("selectedItem", "")
        val message = "Es hora de tomar su pastilla"

        val frecuenciaDatosSeleccionado = sharedPreferences?.getString("frecuenciaDato", "")
        val hora =  binding.txtHora.text.toString()
        val dosis = binding.txtDosis.text.toString()


        intent.putExtra(titleExtraRecordatorio,medicamentoSeleccionado.toString())
        intent.putExtra(messageExtraRecordatorio,message)
        intent.putExtra("Id",Id_Recordatorio.toString())
        intent.putExtra("Pastilla",medicamentoSeleccionado)
        intent.putExtra("Dosis",frecuenciaDatosSeleccionado)
        intent.putExtra("Hora",hora)
        intent.putExtra("Cantidad",dosis)


        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            notificationRecordatorioID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        mensajeAlerta(time,medicamentoSeleccionado.toString(),message)
    }
    private fun getTime(): Long {
        val sharedPreferences = context?.getSharedPreferences("mi_pref", Context.MODE_PRIVATE)
        val horas = sharedPreferences?.getString("hora","")
        val minutos = sharedPreferences?.getString("minuto","")
        val calendar = Calendar.getInstance()
        val yeaar = calendar.get(Calendar.YEAR)
        val mees = calendar.get(Calendar.MONTH)
        val diaa = calendar.get(Calendar.DAY_OF_MONTH)

        calendar.set(yeaar!!.toInt(), mees!!.toInt(),diaa!!.toInt(),horas!!.toInt(),minutos!!.toInt())
        return calendar.timeInMillis
    }

}