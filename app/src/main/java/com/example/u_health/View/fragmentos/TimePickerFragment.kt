package com.example.u_health.View.fragmentos

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.example.u_health.R
import java.util.Calendar

class TimePickerFragment(val listener : (String) -> Unit) :
    DialogFragment(), TimePickerDialog.OnTimeSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val horas = calendar.get(Calendar.HOUR_OF_DAY)
        val minutos = calendar.get(Calendar.MINUTE)
        val dialog = TimePickerDialog(activity as Context,
            R.style.Timepicker, this, horas, minutos,true)
        return dialog
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        listener("$hourOfDay:$minute")
    }

}