package com.example.u_health.View.fragmentos

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.Navigation
import com.example.u_health.R
import com.example.u_health.databinding.FragmentCitasBinding
import com.example.u_health.databinding.FragmentDatosSearchBinding
import com.example.u_health.databinding.FragmentFrequencyDataBinding
import com.example.u_health.databinding.FragmentRecordatoriosBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_SELECTED_ITEM = "selectedItem"

class FrequencyData : Fragment() {

    private lateinit var valorCheck : String
    private var selectedItem: String? = null

    private var _binding: FragmentFrequencyDataBinding? = null
    private val binding get() = _binding!!

    private var param1: String? = null
    private var param2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedItem = it.getString(ARG_SELECTED_ITEM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding= FragmentFrequencyDataBinding.inflate(inflater,container,false)
        val view = binding.root
        binding.radioButton1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                valorCheck = binding.radioButton1.text.toString()
            }
        }

        binding.radioButton2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                valorCheck = binding.radioButton2.text.toString()
            }
        }

        binding.radioButton3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                valorCheck = binding.radioButton3.text.toString()
            }
        }


        binding.btnGuardar.setOnClickListener {
            if(binding.radioButton1.isChecked || binding.radioButton2.isChecked || binding.radioButton3.isChecked)
            {
                val sharedPreferences = context?.getSharedPreferences("mi_pref", Context.MODE_PRIVATE)
                val editor = sharedPreferences?.edit()
                editor?.putString("frecuenciaDato", valorCheck)
                editor?.apply()
                Navigation.findNavController(view).navigate(R.id.frequency)


            }else{
                Toast.makeText(requireContext(), "Rellene los datos", Toast.LENGTH_SHORT).show()
            }

        }

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FrequencyData().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}