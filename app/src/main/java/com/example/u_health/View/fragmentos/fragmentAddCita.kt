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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.u_health.Adapters.MedicamentosProvider
import com.example.u_health.R
import com.example.u_health.databinding.FragmentAddCitaBinding
import com.example.u_health.model.Citas
import com.example.u_health.model.databaseHelper
import java.util.Calendar
import java.util.Date

class fragmentAddCita : Fragment()
{
    private var _binding: FragmentAddCitaBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCitaBinding.inflate(inflater, container, false)
        val view = binding.root

        val sharedPreferences = context?.getSharedPreferences("datos_fecha", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.clear()
        editor?.apply()

        val MisPreferencias = context?.getSharedPreferences("Id's", Context.MODE_PRIVATE)
        val Edit = MisPreferencias?.edit()
        var Id_cita=MisPreferencias?.getInt("Id_Citas",0)
        Id_cita = Id_cita!! + 1

        val toolbar: Toolbar = binding.tbCitas
        toolbar.title = getString(R.string.Cita)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.navigationIcon?.setTint(R.color.textologin)
        toolbar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigation_citas) }

        binding.fecha.setOnClickListener {
            showDatePickerDialog()
        }
        binding.hora.setOnClickListener {
            showTimePicker()
        }

        createNotificacionChannnel()
        binding.btnSave.setOnClickListener {
            if(binding.txtEspecialidad.text.toString()!=""&&binding.txtDoctor.text.toString()!=""&&binding.txtFecha.text.toString()!=""&&
                binding.txtHora.text.toString()!="")
            {
                val Titulo = binding.txtEspecialidad.text.toString()
                val Doctor = binding.txtDoctor.text.toString()
                val Fecha_Cita=binding.txtFecha.text.toString()
                val Hora = binding.txtHora.text.toString()
                val Detalles = binding.txtDetalles.text.toString()

                scheduleNotification()

                val cita = Citas()
                cita.Id = Id_cita?.toLong()!!
                cita.Titulo = Titulo
                cita.Medico = Doctor
                cita.Fecha = Fecha_Cita
                cita.Hora = Hora
                cita.Detalles = Detalles
                databaseHelper(requireContext()).insertarRecordatorios_Citas(cita)
                Navigation.findNavController(view).navigate(R.id.navigation_citas)
                Edit?.putInt("Id_Citas",Id_cita)
                Edit?.apply()
            }else
                Toast.makeText(requireContext(), "Rellene los datos", Toast.LENGTH_SHORT).show()
        }

        binding.btnCancelCita.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigation_citas)
        }

        return view
    }
    private fun scheduleNotification() {
        val intent = Intent(requireContext(), Notification::class.java)
        val title = binding.txtEspecialidad.text.toString()
        val message = binding.txtDetalles.text.toString()
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
        val dia = sharedPreferences?.getString("dia", "")
        val mes = sharedPreferences?.getString("mes", "")
        val year = sharedPreferences?.getString("year", "")
        val horas = sharedPreferences?.getString("horas","")
        val minutos = sharedPreferences?.getString("minutos","")
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
        binding.txtFecha.text = "$dia / ${mes+1} / $year"
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
                binding.txtHora.text = "$selectedHour : $selectedMinute"
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