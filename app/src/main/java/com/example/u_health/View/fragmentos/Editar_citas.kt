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
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import com.example.u_health.R
import com.example.u_health.databinding.FragmentEditarCitasBinding
import com.example.u_health.model.Citas
import com.example.u_health.model.databaseHelper
import java.util.Calendar
import java.util.Date


private var _binding: FragmentEditarCitasBinding? = null
private val binding get() = _binding!!

class Editar_citas : Fragment()
{

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        _binding = FragmentEditarCitasBinding.inflate(inflater, container, false)
        val view = binding.root

        val toolbar: Toolbar = binding.tbActualizarcitas
        toolbar.title="Actualizar Citas"
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.navigationIcon?.setTint(R.color.textologin)
        toolbar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigation_citas)
        }

        val Prefencias = context?.getSharedPreferences("Datos_Citas", Context.MODE_PRIVATE)
        val Id = Prefencias?.getString("Id", "")
        var Titulo = Prefencias?.getString("Titulo", "")
        var Medico = Prefencias?.getString("Medico", "")
        var Fecha = Prefencias?.getString("Fecha", "")
        var Hora = Prefencias?.getString("Hora", "")
        var Detalles = Prefencias?.getString("Detalles", "")

        val editor = Prefencias?.edit()
        editor?.clear()
        editor?.apply()

        binding.txtEspecialidadActualizar.setText(Titulo)
        binding.txtDoctorActualizar.setText(Medico)
        binding.txtFechaActualizar.text=Fecha
        binding.txtHoraActualizar.text=Hora
        binding.txtDetallesActualizar.setText(Detalles)

        binding.fechaActualizar.setOnClickListener {
            showDatePickerDialog()
        }
        binding.horaActualizar.setOnClickListener {
            showTimePicker()
        }

        createNotificacionChannnel()
        binding.btnSaveActualizar.setOnClickListener {

                Titulo = binding.txtEspecialidadActualizar.text.toString()
                Medico = binding.txtDoctorActualizar.text.toString()
                Fecha=binding.txtFechaActualizar.text.toString()
                Hora = binding.txtHoraActualizar.text.toString()
                Detalles = binding.txtDetallesActualizar.text.toString()

                if(Id != null && Hora != null && Fecha != null)
                {
                    scheduleNotification()

                    val cita = Citas()
                    cita.Id = Id?.toLong()!!
                    cita.Titulo = Titulo as String
                    cita.Medico = Medico as String
                    cita.Fecha = Fecha as String
                    cita.Hora = Hora as String
                    cita.Detalles = Detalles as String

                    databaseHelper(requireContext()).actualizarRecordatorio_Citas(cita)
                    Navigation.findNavController(view).navigate(R.id.navigation_citas)
                }
        }

        binding.btnCancelCitaActualizar.setOnClickListener { Navigation.findNavController(view).navigate(R.id.navigation_citas) }

        return view
    }

    private fun scheduleNotification() {
        val intent = Intent(requireContext(), Notification::class.java)
        val title = binding.txtEspecialidadActualizar.text.toString()
        val message = binding.txtDetallesActualizar.text.toString()
        intent.putExtra(titleExtra,title)
        intent.putExtra(messageExtra,message)

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            notificationID,
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
        mensajeAlerta(time,title,message)
        val sharedPreferences = context?.getSharedPreferences("datos_fecha", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.remove("horas")
        editor?.remove("minutos")
        editor?.remove("dia")
        editor?.remove("mes")
        editor?.remove("year")
        editor?.apply()
    }

    private fun getTime(): Long {
        val sharedPreferences = context?.getSharedPreferences("datos_fecha", Context.MODE_PRIVATE)
        val dia = sharedPreferences?.getString("dia", "14")
        val mes = sharedPreferences?.getString("mes", "6")
        val year = sharedPreferences?.getString("year", "2023")
        val horas = sharedPreferences?.getString("horas","9")
        val minutos = sharedPreferences?.getString("minutos","30")
        val calendar = Calendar.getInstance()

        calendar.set(year!!.toInt(), mes!!.toInt(),dia!!.toInt(),horas!!.toInt(),minutos!!.toInt())


        return calendar.timeInMillis

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificacionChannnel() {
        val name = "Notif channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc

        val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
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

    private fun showDatePickerDialog()
    {
        val datePicker = DatePickerFragment { dia, mes, year -> currentDate(dia, mes, year) }
        datePicker.show(requireFragmentManager(), "datepicker")
    }
    private fun currentDate(dia: Int, mes: Int, year: Int) {
        binding.txtFechaActualizar.text = "$dia / ${mes+1} / $year"
        val sharedPreferences = context?.getSharedPreferences("datos_fecha", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString("dia", dia.toString())
        editor?.putString("mes",mes.toString())
        editor?.putString("year",year.toString())
        editor?.apply()
    }
    private fun showTimePicker(){
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(requireContext(),
            TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                binding.txtHoraActualizar.text = "$selectedHour : $selectedMinute"
                val sharedPreferences = context?.getSharedPreferences("datos_fecha", Context.MODE_PRIVATE)
                val editor = sharedPreferences?.edit()
                editor?.putString("horas", selectedHour.toString())
                editor?.putString("minutos",selectedMinute.toString())
                editor?.apply()
            },
            hour, minute, false)

        timePickerDialog.show()
    }

}