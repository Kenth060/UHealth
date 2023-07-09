package com.example.u_health.View.fragmentos

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.u_health.R
import com.example.u_health.databinding.FragmentPesoDetalleBinding
import com.example.u_health.databinding.FragmentUsuarioBinding
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


class Peso_Detalle : Fragment()
{


    private var _binding: FragmentPesoDetalleBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        _binding = FragmentPesoDetalleBinding.inflate(inflater, container, false)
        val view = binding.root

        val Prefencias = context?.getSharedPreferences("Datos_Usuario", Context.MODE_PRIVATE)
        val Peso = Prefencias?.getString("Peso", "")
        val Peso_Ideal = Prefencias?.getString("Peso_Ideal", "")


        binding.txtPesoActual.text=getString(R.string.Peso_Actual)+" $Peso lbs"
        binding.txtPesoIdeal.text="$Peso_Ideal lbs"

        val Peso_Necesario= ((Peso?.toDouble() ?: 0.0 ) - (Peso_Ideal?.toInt() ?: 0 )).roundToInt()

        binding.txtPesoNecesario.text=Peso_Necesario.absoluteValue.toString()+" lbs"

        if(Peso_Necesario > 0)
        {
            binding.txtNecesidad.text=getString(R.string.Necesita_perder)
            binding.txtConsejosTitulo.text=getString(R.string.consejos_para_bajar_de_peso)
            binding.txtConsejos.text=getString(R.string.consejos_bajar)
        }
        else
        {
            binding.txtNecesidad.text=getString(R.string.Necesita_ganar)
            binding.txtConsejosTitulo.text=getString(R.string.consejos_para_subir_de_peso)
            binding.txtConsejos.text=getString(R.string.consejos_subir)
        }

        return view
    }


}