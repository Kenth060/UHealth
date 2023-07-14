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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.u_health.Adapters.MedicamentosProvider
import com.example.u_health.R
import com.example.u_health.databinding.FragmentVistaMedicamentoBinding
import com.example.u_health.model.Medicamentos
import com.example.u_health.model.databaseHelper
import java.util.Calendar
import java.util.Date


class vista_medicamento : Fragment()
{
    private var _binding: FragmentVistaMedicamentoBinding? = null
    private val binding get() = _binding!!
    private var Frecuencia=""
    private var Tipo=""
    private lateinit var Id:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        _binding = FragmentVistaMedicamentoBinding.inflate(inflater, container, false)
        val view = binding.root

        val toolbar: Toolbar = binding.tbRecordatoriosDetalles
        toolbar.title="Actualizar Tratamiento"
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.navigationIcon?.setTint(R.color.textologin)
        toolbar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigation_recordatorios)
        }


        val FrecuencyList = resources.getStringArray(R.array.FrecuenciaList)
        val AdapterFrecuency = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,FrecuencyList)
        binding.cbFrecuenciaMedAct.apply {
            setAdapter(AdapterFrecuency)

            doOnTextChanged{ SelectedItem,_,_,_ ->
                Frecuencia= SelectedItem.toString()
            }
        }

        val TipoList = resources.getStringArray(R.array.TipoList)
        val AdapterTipo = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,TipoList)
        binding.cbTipoMedsAct.apply {
            setAdapter(AdapterTipo)
            doOnTextChanged{ SelectedItem,_,_,_ ->
                Tipo= SelectedItem.toString()
            }
        }


        val Prefencias = context?.getSharedPreferences("Datos_Recordatorio", Context.MODE_PRIVATE)
        Id = Prefencias?.getString("Id", "").toString()
        var Pastilla = Prefencias?.getString("Pastilla", "")
        Tipo = Prefencias?.getString("Tipo", "").toString()
        Frecuencia = Prefencias?.getString("Frecuencia", "").toString()
        val Cantidad = Prefencias?.getString("Cantidad", "")
        var Hora = Prefencias?.getString("Hora", "")

        val editor = Prefencias?.edit()
        editor?.clear()
        editor?.apply()

        binding.txtMedicamentoAct.setText(Pastilla)
        binding.bckTiposMedsAct.hint=Tipo
        binding.bckFrecuenciaMedAct.hint=Frecuencia
        binding.txtHoraMedAct.text = Hora
        binding.txtCantidadPastillasTAct.setText(Cantidad)

        binding.linearLayoutHoraAct.setOnClickListener {
            showTimePicker()
        }

        createNotificacionChannnel()
        binding.btnActMed.setOnClickListener {
            scheduleNotification()
            Pastilla = binding.txtMedicamentoAct.text.toString()
            Hora= binding.txtHoraMedAct.text.toString()
            val Cant = binding.txtCantidadPastillasTAct.text.toString().toInt()


            if (Id != null && Pastilla != null && Hora != null)
            {
                val med=Medicamentos()
                med.Id=Id.toLong()
                med.Pastilla= Pastilla as String
                med.Tipo=Tipo
                med.Frecuencia=Frecuencia
                med.Hora= Hora as String
                med.Cantidad=Cant


                databaseHelper(requireContext()).actualizarRecordatorio_Medicamento(med)
                Navigation.findNavController(view).navigate(R.id.navigation_recordatorios)
            }
            else
            {
                Toast.makeText(requireContext(),"Rellene los datos",Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCancelMedAct.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigation_recordatorios)
        }

        return view
    }

    private fun showTimePicker() {
        val timePicker = TimePickerFragment { hour, minute ->

            var hora=""
            var minuto=""

            if(hour in 0..9)
            { hora="0$hour" }
            else
            { hora=hour.toString() }

            if(minute in 0..9)
            { minuto="0$minute" }
            else
            { minuto=minute.toString() }


            onTimeSelected(hora, minuto)


        }

        timePicker.show(requireFragmentManager(), "timePicker")
    }

    @SuppressLint("SetTextI18n")
    private fun onTimeSelected(hour: String, minute: String) {
        binding.txtHoraMedAct.text = "$hour : $minute"
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
        val Medicamento= binding.txtMedicamentoAct.text.toString()
        val message = "Es hora de tomar su medicamento"
        val hora =  binding.txtHoraMedAct.text.toString()
        val Cantidad = binding.txtCantidadPastillasTAct.text.toString()


        intent.putExtra(titleExtraRecordatorio,Medicamento)
        intent.putExtra(messageExtraRecordatorio,message)
        intent.putExtra("Id",Id)
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