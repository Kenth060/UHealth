package com.example.u_health.View.fragmentos

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.u_health.R
import com.example.u_health.databinding.FragmentInformacionBinding

private var fbinding: FragmentInformacionBinding? = null
private val binding get() = fbinding!!

private val opc="opc"

class Informacion : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fbinding = FragmentInformacionBinding.inflate(inflater, container, false)
        val view: View = binding.root


        binding.btnAnalgesico.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.informacion_Medicamentos)
            val Preferencias: SharedPreferences? = context?.getSharedPreferences("Datos_Informacion", Context.MODE_PRIVATE)
            val editor = Preferencias?.edit()
            editor?.putString("Opc","1")
            editor?.apply()
        }

        binding.btnAntiflamatorio.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.informacion_Medicamentos)
            val Preferencias: SharedPreferences? = context?.getSharedPreferences("Datos_Informacion", Context.MODE_PRIVATE)
            val editor = Preferencias?.edit()
            editor?.putString("Opc","2")
            editor?.apply()}

        binding.btnAntibioticos.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.informacion_Medicamentos)
            val Preferencias: SharedPreferences? = context?.getSharedPreferences("Datos_Informacion", Context.MODE_PRIVATE)
            val editor = Preferencias?.edit()
            editor?.putString("Opc","3")
            editor?.apply()}

        binding.btnAntidepresivos.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.informacion_Medicamentos)
            val Preferencias: SharedPreferences? = context?.getSharedPreferences("Datos_Informacion", Context.MODE_PRIVATE)
            val editor = Preferencias?.edit()
            editor?.putString("Opc","4")
            editor?.apply() }

        binding.btnDiabetes.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.informacion_Medicamentos)
            val Preferencias: SharedPreferences? = context?.getSharedPreferences("Datos_Informacion", Context.MODE_PRIVATE)
            val editor = Preferencias?.edit()
            editor?.putString("Opc","5")
            editor?.apply()}

        binding.btnMedicinaTos.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.informacion_Medicamentos)
            val Preferencias: SharedPreferences? = context?.getSharedPreferences("Datos_Informacion", Context.MODE_PRIVATE)
            val editor = Preferencias?.edit()
            editor?.putString("Opc","6")
            editor?.apply() }


        return view
    }




}