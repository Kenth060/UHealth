package com.example.u_health.View.fragmentos

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.u_health.Adapters.MedicamentosProvider
import com.example.u_health.databinding.FragmentUsuarioBinding
import com.example.u_health.model.Medicamentos
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class Usuario : Fragment()
{
    private var _binding: FragmentUsuarioBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        _binding = FragmentUsuarioBinding.inflate(inflater, container, false)
        val view = binding.root
        var Calorias:Int = 0
        val fireDB: FirebaseFirestore = FirebaseFirestore.getInstance()
        val auth : FirebaseAuth = FirebaseAuth.getInstance()
        val Id= auth.currentUser?.uid

        if (Id != null)
        {
            val usuario =fireDB.collection("Usuarios").document(Id)
            usuario.get().addOnSuccessListener {
                val altura=(it.get("Altura").toString().toDouble())
                val edad=it.get("Edad").toString().toInt()
                val peso=it.get("Peso").toString().toDouble()
                val Peso_Ideal=(((altura*100)-100+((edad/10)*0.9))*2.2).roundToInt()

                binding.txtUsuario.text=it.get("Nombres").toString()
                binding.txtAltura.text="${(altura*100).roundToInt()} cm"
                binding.txtPeso.text="$peso lbs"
                binding.txtEdad.text="$edad años"
                binding.txtPesoIdeal.text= "$Peso_Ideal lbs"
                val IMC=((peso/2.2)/altura.pow(2)).roundToInt()
                //binding.txtIMC.text=IMC.toString()

                if( (it.get("Genero").toString())=="Masculino" )
                {
                    Calorias= ((( 66 + (13.7 * (peso/2.2)) ) + ( (5*(altura*100)) - (6.8*edad) ))*1.375).roundToInt()
                }

                if( (it.get("Genero").toString())=="Femenino" )
                {
                    Calorias= ((( 655 + (9.6 * (peso/2.2)) ) + ( (1.8*(altura*100)) - (4.7*edad) ))*1.375).roundToInt()
                }

                binding.txtCalorias.text="$Calorias necesarias al dia"

            }
        }


        return view
    }

}