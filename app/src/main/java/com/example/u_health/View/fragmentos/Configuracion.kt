package com.example.u_health.View.fragmentos

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import com.example.u_health.R
import com.example.u_health.View.CrearPerfil
import com.example.u_health.View.Login
import com.example.u_health.databinding.FragmentCitasBinding
import com.example.u_health.databinding.FragmentConfiguracionBinding
import com.google.firebase.auth.FirebaseAuth


class Configuracion : Fragment()
{

    private var _binding: FragmentConfiguracionBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConfiguracionBinding.inflate(inflater, container, false)
        val view = binding.root

        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

        val toolbar: Toolbar = binding.tbConfiguracion
        toolbar.title = getString(R.string.Configuracion)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.navigationIcon?.setTint(R.color.textologin)
        toolbar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigate(R.id.navigation_usuario)
        }

        binding.btnActualizarPerfil.setOnClickListener {
            Toast.makeText(requireContext(),"Actualizar Perfil",Toast.LENGTH_SHORT)
            startActivity(Intent(requireContext(), CrearPerfil::class.java))
        }

        binding.btnCerrarSesion.setOnClickListener {
            Toast.makeText(requireContext(),"Cerrar Sesion",Toast.LENGTH_SHORT)
            firebaseAuth.signOut()
            startActivity(Intent(requireContext(), Login::class.java))
        }


        return view
    }


}