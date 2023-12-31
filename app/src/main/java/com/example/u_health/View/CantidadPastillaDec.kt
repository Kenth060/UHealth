package com.example.u_health.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.u_health.databinding.ActivityCantidadPastillaDecBinding
import com.example.u_health.model.Medicamentos
import com.example.u_health.model.databaseHelper


class CantidadPastillaDec : AppCompatActivity()
{
    private lateinit var binding: ActivityCantidadPastillaDecBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityCantidadPastillaDecBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var Id=intent.getStringExtra("Id")
        var Pastilla=intent.getStringExtra("Pastilla")
        var Tipo=intent.getStringExtra("Tipo")
        var Frecuencia=intent.getStringExtra("Frecuencia")
        var Hora=intent.getStringExtra("Hora")
        var Cantidad= (intent.getStringExtra("Cantidad"))?.toInt()



        binding.txtCantidadPastillas.text=Cantidad.toString()


        binding.btnDecrementar.setOnClickListener {

            if(Id != null && Pastilla != null  && Tipo != null && Frecuencia != null  && Hora != null && Cantidad != null  )
            {
                var med = Medicamentos(Id.toLong(), Pastilla ,Tipo, Frecuencia, Hora, Cantidad)
                databaseHelper(this).actualizarRecordatorios_Medicamentos(med)

                binding.txtCantidadPastillas.text=(Cantidad-1).toString()
                Toast.makeText(this,"Excelente! Siga Tomando su tratamiento :)",Toast.LENGTH_SHORT)
                binding.txtFelicitacion.text="Excelente! Siga Tomando su tratamiento :)"
            }
        }




    }
}