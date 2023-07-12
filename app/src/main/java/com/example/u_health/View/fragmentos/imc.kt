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

private var fbinding: FragmentImcBinding? = null
private val binding get() = fbinding!!


class imc : Fragment()
{



    @SuppressLint("ResourceAsColor")
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
        val IMC = Prefencias?.getString("IMC", "")

       // binding.textView5.text=IMC

        return view
    }

}