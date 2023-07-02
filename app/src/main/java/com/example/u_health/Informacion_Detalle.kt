package com.example.u_health

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import com.example.u_health.databinding.FragmentInformacionDetalleBinding
import com.example.u_health.databinding.FragmentInformacionMedicamentosBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

private var fbinding: FragmentInformacionDetalleBinding? = null
private val binding get() = fbinding!!

class Informacion_Detalle : Fragment()
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
        fbinding = FragmentInformacionDetalleBinding.inflate(inflater, container, false)
        val view: View = binding.root

        val toolbar: Toolbar = binding.tbInformacionDetalles
        toolbar.title = getString(R.string.Informacion_Detalle)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.navigationIcon?.setTint(R.color.textologin)
        toolbar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigate(R.id.informacion_Medicamentos)
        }


        val Prefencias = context?.getSharedPreferences("Datos_Informacion", Context.MODE_PRIVATE)
        val Pastilla=  Prefencias?.getString("Pastilla","")
        val Tipo=  Prefencias?.getString("Tipo","")

        val Nombre_Pastilla=binding.txtNombrePastilla
        val Informacion=binding.txtInfo
        val usos=binding.txtUsosContent
        val Riesgos=binding.txtRiesgos

        Nombre_Pastilla.text=Pastilla
        val fireDB: FirebaseFirestore = FirebaseFirestore.getInstance()

        if(Tipo!=null && Pastilla!= null)
        {
            val pills = fireDB.collection(Tipo).document(Pastilla)

            pills.get().addOnSuccessListener{
                Informacion.text= it.get("Informacion").toString()
                Riesgos.text= it.get("Riesgos").toString()
                usos.text= it.get("Usos").toString()
            }
        }

        return view
    }
}