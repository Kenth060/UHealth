package com.example.u_health.View.fragmentos

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import com.example.u_health.R
import com.example.u_health.databinding.FragmentImcBinding
import com.example.u_health.databinding.FragmentInformacionBinding
import java.math.RoundingMode
import java.text.DecimalFormat

private var fbinding: FragmentImcBinding? = null
private val binding get() = fbinding!!


class imc : Fragment()
{

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        fbinding = FragmentImcBinding.inflate(inflater, container, false)
        val view: View = binding.root

        val toolbar: Toolbar = binding.tbImc
        toolbar.title = getString(R.string.Imc)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.navigationIcon?.setTint(R.color.textologin)
        toolbar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigation_usuario) }

        val Prefencias = context?.getSharedPreferences("Datos_Usuario", Context.MODE_PRIVATE)
        var IMC = Prefencias?.getString("IMC", "")?.toDouble()
        val Genero = Prefencias?.getString("Genero", "")
        val Edad = (Prefencias?.getString("Edad", ""))?.toInt()
        var Fat = 0.0
        var Estado=""
        var Necesidad=""

        if(Genero == "Masculino" && IMC != null && Edad != null)
        { Fat = ( (1.2  * IMC) + (0.23 * Edad) - (10.8 * 1) - 5.4 ) }
        else if(Genero == "Femenino" && IMC != null && Edad != null)
        { Fat = ( (1.2  * IMC) + (0.23 * Edad) - (10.8 * 0) - 5.4 ) }

        if(IMC != null)
        {
            if (IMC < 16)
            {
                Estado = getString(R.string.imc_pesodemasiadobajo)
                Necesidad="Ganar"
            }
            else if (IMC >= 16 && IMC < 17)
            {
                Estado = getString(R.string.imc_pesomuybajo)
                Necesidad="Ganar"
            }
            else if (IMC >= 17 && IMC < 18.5)
            {
                Estado = getString(R.string.imc_pesobajo)
                Necesidad="Ganar"
            }
            else if (IMC >= 18.5 && IMC < 25)
            {
                Estado = getString(R.string.imc_pesonormal)
                binding.txtNecesidadImc.text="Se encuentra en excelentes condiciones"
            }
            else if( IMC >=25 && IMC < 30)
            {
                Estado = getString(R.string.imc_sobrepeso)
                Necesidad="Perder"
            }
            else if( IMC >=30 && IMC < 35)
            {
                Estado = getString(R.string.imc_obesidad1)
                Necesidad="Perder"
            }
            else if( IMC >=35 && IMC < 40)
            {
                Estado = getString(R.string.imc_obesidad2)
                Necesidad="Perder"
            }
            else if( IMC >=40)
            {
                Estado = getString(R.string.imc_obesidad3)
                Necesidad="Perder"
            }
        }

        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN
        val imc = df.format (IMC)
        val fat = df.format (Fat)

        binding.txtIMC.text=imc
        binding.txtFat.text= "$fat %"
        binding.txtImcEstado.text=Estado
        binding.txtEstadoImc.text= (getString(R.string.estado)) + " $Estado"

        if(Necesidad == "Ganar")
        { binding.txtNecesidadImc.text = (getString(R.string.Necesita_ganar)) + " Peso" }
        else if(Necesidad == "Perder")
        { binding.txtNecesidadImc.text = (getString(R.string.Necesita_perder)) + " Peso" }

        return view
    }

}