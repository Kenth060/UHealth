package com.example.u_health.View.fragmentos

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doOnTextChanged
import androidx.navigation.Navigation
import com.example.u_health.R
import com.example.u_health.databinding.FragmentImcBinding
import com.example.u_health.databinding.FragmentTratamientoBinding
import com.example.u_health.model.Medicamentos
import com.example.u_health.model.databaseHelper
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import java.util.Calendar
import java.util.Date

private var fbinding: FragmentTratamientoBinding? = null
private val binding get() = fbinding!!
private var Id_Recordatorio=0
private var Frecuencia=""
private var Tipo=""
class Tratamiento : Fragment()
{

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        fbinding = FragmentTratamientoBinding.inflate(inflater, container, false)
        val view: View = binding.root

        val toolbar: Toolbar = binding.tbTratamiento
        toolbar.title = getString(R.string.tratamiento)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.navigationIcon?.setTint(R.color.textologin)
        toolbar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigation_recordatorios) }

        val FrecuencyList = resources.getStringArray(R.array.FrecuenciaList)
        val AdapterFrecuency = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,FrecuencyList)
        binding.cbFrecuenciaMed.apply {
            setAdapter(AdapterFrecuency)

            doOnTextChanged{ SelectedItem,_,_,_ ->
                Frecuencia= SelectedItem.toString()
            }
        }

        val TipoList = resources.getStringArray(R.array.TipoList)
        val AdapterTipo = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,TipoList)
        binding.cbTipoMeds.apply {
            setAdapter(AdapterTipo)
            doOnTextChanged{ SelectedItem,_,_,_ ->
                Tipo= SelectedItem.toString()
            }
        }

        val MisPreferencias = context?.getSharedPreferences("Ids", Context.MODE_PRIVATE)
        val Edit = MisPreferencias?.edit()
        Id_Recordatorio= MisPreferencias?.getInt("Id_Recordatorios",0)!!
        Id_Recordatorio = Id_Recordatorio!! + 1

        binding.linearLayoutHora.setOnClickListener {
            showTimePicker()
        }

        createNotificacionChannnel()
        binding.btnSaveMed.setOnClickListener {
            if(!binding.txtHoraMed.text.equals("00:00"))
            {
                scheduleNotification()
                val Medicamento = binding.txtMedicamento.text.toString()
                val Hora= binding.txtHoraMed.text.toString()
                val Cantidad = binding.txtCantidadPastillasT.text.toString().toInt()

                var med= Medicamentos()
                med.Id=Id_Recordatorio.toLong()
                med.Pastilla=Medicamento
                med.Tipo=Tipo
                med.Frecuencia= Frecuencia
                med.Hora=Hora
                med.Cantidad=Cantidad
                databaseHelper(requireContext()).insertarRecordatorios_Medicamentos(med)

                Edit?.putInt("Id_Recordatorios",Id_Recordatorio)
                Edit?.apply()

                Toast.makeText(requireContext(), "Guardado", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view).navigate(R.id.navigation_recordatorios)
            }
            else
            {
                Toast.makeText(requireContext(), "Rellene los datos", Toast.LENGTH_SHORT).show()
            }
        }


        binding.btnCancelMed.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigation_recordatorios)
        }

        return view
    }

    private fun showTimePicker() {
        val timePicker = TimePickerFragment { hour, minute ->
            onTimeSelected(hour, minute as Int)
        }
        timePicker.show(requireFragmentManager(), "timePicker")
    }

    private fun onTimeSelected(hour: Int, minute: Int) {
        binding.txtHoraMed.text = "$hour : $minute"
        val sharedPreferences = context?.getSharedPreferences("mi_pref", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString("hora", hour.toString())
        editor?.putString("minuto", minute.toString())
        editor?.apply()
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
        val Medicamento= binding.txtMedicamento.text.toString()
        val message = "Es hora de tomar su medicamento"
        val hora =  binding.txtHoraMed.text.toString()
        val Cantidad = binding.txtCantidadPastillasT.text.toString()


        intent.putExtra(titleExtraRecordatorio,Medicamento)
        intent.putExtra(messageExtraRecordatorio,message)
        intent.putExtra("Id",Id_Recordatorio.toString())
        intent.putExtra("Pastilla",Medicamento)
        intent.putExtra("Tipo",Tipo)
        intent.putExtra("Frecuencia",Frecuencia)
        intent.putExtra("Hora",hora)
        intent.putExtra("Cantidad",Cantidad)


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
        mensajeAlerta(time,Medicamento,message)
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


}