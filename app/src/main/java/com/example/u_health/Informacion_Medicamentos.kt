package com.example.u_health

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.u_health.Adapters.AdapterPastillas
import com.example.u_health.Adapters.MedicamentosProvider
import com.example.u_health.Adapters.PastillasListener
import com.example.u_health.databinding.FragmentInformacionMedicamentosBinding


private var fbinding: FragmentInformacionMedicamentosBinding? = null
private val binding get() = fbinding!!

class Informacion_Medicamentos : Fragment() , PastillasListener
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        fbinding = FragmentInformacionMedicamentosBinding.inflate(inflater, container, false)
        val view: View = binding.root

        val toolbar: Toolbar = binding.tbInfo
        toolbar.title = getString(R.string.Informacion_Detalle)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.navigationIcon?.setTint(R.color.textologin)
        toolbar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigate(R.id.navigation_informacion)
        }

        initRecyclerView()
        return view
    }

    fun initRecyclerView()
    {
        val Prefencias = context?.getSharedPreferences("Datos_Informacion", Context.MODE_PRIVATE)
        val opc = Prefencias?.getString("Opc", "")

        val rv= binding.rvPastillas
        rv.layoutManager= LinearLayoutManager(requireContext())


        if(opc=="1")
        {rv.adapter= AdapterPastillas(MedicamentosProvider.Analgesicos,this)
        }
        else if (opc=="2")
        { rv.adapter= AdapterPastillas(MedicamentosProvider.Antiflamatorios,this) }
        else if (opc == "3")
        { rv.adapter= AdapterPastillas(MedicamentosProvider.Antibioticos,this) }
        else if (opc == "4")
        { rv.adapter= AdapterPastillas(MedicamentosProvider.Antidepresivos,this) }
        else if (opc == "5")
        { rv.adapter= AdapterPastillas(MedicamentosProvider.MedDiabetes,this) }
        else if (opc == "6")
        { rv.adapter= AdapterPastillas(MedicamentosProvider.MediTosGripe,this) }
        else
        { rv.adapter= AdapterPastillas(MedicamentosProvider.Prueba,this) }


    }
    override fun onPastillaClicked(p: String)
    {

        val Prefencias = context?.getSharedPreferences("Datos_Informacion", Context.MODE_PRIVATE)
        val opc = Prefencias?.getString("Opc", "")

        if(opc=="1")
        {
            view?.let { Navigation.findNavController(it).navigate(R.id.informacion_Detalle) }
            val Preferencias: SharedPreferences? = context?.getSharedPreferences("Datos_Informacion", Context.MODE_PRIVATE)
            val editor = Preferencias?.edit()
            editor?.putString("Pastilla",p)
            editor?.putString("Tipo","Analgesicos")
            editor?.apply()
        }
        else if (opc=="2")
        {
            view?.let { Navigation.findNavController(it).navigate(R.id.informacion_Detalle) }
            val Preferencias: SharedPreferences? = context?.getSharedPreferences("Datos_Informacion", Context.MODE_PRIVATE)
            val editor = Preferencias?.edit()
            editor?.putString("Pastilla",p)
            editor?.putString("Tipo","Antiflamatorios")
            editor?.apply()
        }
        else if (opc == "3")
        {
            view?.let { Navigation.findNavController(it).navigate(R.id.informacion_Detalle) }
            val Preferencias: SharedPreferences? = context?.getSharedPreferences("Datos_Informacion", Context.MODE_PRIVATE)
            val editor = Preferencias?.edit()
            editor?.putString("Pastilla",p)
            editor?.putString("Tipo","Antibioticos")
            editor?.apply()
        }
        else if (opc == "4")
        {
            view?.let { Navigation.findNavController(it).navigate(R.id.informacion_Detalle) }
            val Preferencias: SharedPreferences? = context?.getSharedPreferences("Datos_Informacion", Context.MODE_PRIVATE)
            val editor = Preferencias?.edit()
            editor?.putString("Pastilla",p)
            editor?.putString("Tipo","Antidepresivos")
            editor?.apply()
        }
        else if (opc == "5")
        {
            view?.let { Navigation.findNavController(it).navigate(R.id.informacion_Detalle) }
            val Preferencias: SharedPreferences? = context?.getSharedPreferences("Datos_Informacion", Context.MODE_PRIVATE)
            val editor = Preferencias?.edit()
            editor?.putString("Pastilla",p)
            editor?.putString("Tipo","Medicamentos para la diabetes")
            editor?.apply()
        }
        else if (opc == "6")
        {
            view?.let { Navigation.findNavController(it).navigate(R.id.informacion_Detalle) }
            val Preferencias: SharedPreferences? = context?.getSharedPreferences("Datos_Informacion", Context.MODE_PRIVATE)
            val editor = Preferencias?.edit()
            editor?.putString("Pastilla",p)
            editor?.putString("Tipo","Medicamentos para la tos y gripe")
            editor?.apply()
        }

    }
}