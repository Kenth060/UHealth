package com.example.u_health.View.fragmentos

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class DatePickerFragment(val listener : (dia: Int, mes : Int, año : Int) -> Unit) : DialogFragment(),
DatePickerDialog.OnDateSetListener{
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener(dayOfMonth, month, year)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val dia = c.get(Calendar.DAY_OF_MONTH)
        val mes = c.get(Calendar.MONTH)
        val año = c.get(Calendar.YEAR)
        val picker = DatePickerDialog(activity as Context, this, año, mes, dia)
        return picker
    }

    companion object{

    }
}