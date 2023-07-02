package com.example.u_health.View

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.u_health.View.fragmentos.DatePickerFragment
import com.example.u_health.R
import com.example.u_health.databinding.ActivityCrearPerfilBinding
import com.example.u_health.databinding.VistaAlturaBinding
import com.example.u_health.databinding.VistaEnfermedadBinding
import com.example.u_health.databinding.VistaGeneroBinding
import com.example.u_health.databinding.VistaPesoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class CrearPerfil : AppCompatActivity()
{
    private lateinit var popupWindow: PopupWindow
    private lateinit var binding : ActivityCrearPerfilBinding
    private lateinit var bindingGenero : VistaGeneroBinding
    private lateinit var bindingPeso : VistaPesoBinding
    private lateinit var bindingAltura : VistaAlturaBinding
    private lateinit var bindingEnfermedad : VistaEnfermedadBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var editor : SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearPerfilBinding.inflate(layoutInflater)
        bindingGenero = VistaGeneroBinding.inflate(layoutInflater)
        bindingPeso = VistaPesoBinding.inflate(layoutInflater)
        bindingAltura = VistaAlturaBinding.inflate(layoutInflater)
        bindingEnfermedad = VistaEnfermedadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializando el sharedPreferences de forma global
        sharedPref = getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE)
        editor = sharedPref.edit()
        binding.btnCrearPerfil.setOnClickListener {
            Agregar_Usuario()
        }


        initNumberPicker()
        activityCalendarFN()
        //metodo para ventanas flotantes que contiene un solo numberPicker
        activityNumberPickerOne(
            {showWindowFloat(0.8,bindingGenero.root)},
            bindingGenero.numberPicker,
            binding.TextViewActividadGenero,
            bindingGenero.generoVista,
            bindingGenero.textViewSelect
        )
        activityNumberPickerOne(
            {showWindowFloat(0.8,bindingEnfermedad.root)},
            bindingEnfermedad.numberPickerEnfermedad,
            binding.TextViewActividadEnfermedad,
            bindingEnfermedad.enfermedadVista,
            bindingEnfermedad.textViewSelectEnfermedad
        )

        //metodos para ventenas flotantes que contienen dos numberpicker
        actividadTwoNumberPicker(
            { showWindowFloat(0.89,bindingPeso.root) },
            bindingPeso.numberPicker1,
            bindingPeso.numberPicker2,
            bindingPeso.textview2,
            bindingPeso.textview3,
            binding.TextViewActividadPeso,
            bindingPeso.pesoVista
        )
        actividadTwoNumberPicker(
            { showWindowFloat(0.89,bindingAltura.root) },
            bindingAltura.numberPickerMetros,
            bindingAltura.numberPickerCentimetros,
            bindingAltura.textviewPies,
            bindingAltura.textviewPulgadas,
            binding.TextViewActividadAltura,
            bindingAltura.alturaVista
        )
        actividadCancelar()
        //updateTextView()
    }
    private fun initNumberPicker()
    {
        //Genero
        bindingGenero.numberPicker.minValue = 0
        bindingGenero.numberPicker.maxValue = 3
        bindingGenero.numberPicker.displayedValues = arrayOf(
            "Masculino","Femenino","Prefiero no decir","Otro"
        )
        //peso
        bindingPeso.numberPicker1.minValue = 0
        bindingPeso.numberPicker1.maxValue = 300
        bindingPeso.numberPicker2.minValue = 0
        bindingPeso.numberPicker2.maxValue = 9

        //altura
        bindingAltura.numberPickerMetros.minValue = 0
        bindingAltura.numberPickerMetros.maxValue = 3
        bindingAltura.numberPickerCentimetros.minValue = 30
        bindingAltura.numberPickerCentimetros.maxValue = 99

        bindingAltura.numberPickerMetros.displayedValues = Array(4) { "$it m" }
        bindingAltura.numberPickerCentimetros.displayedValues = Array(70) { "${it + 30} cm" }

        //enfermedad
        bindingEnfermedad.numberPickerEnfermedad.minValue = 0
        bindingEnfermedad.numberPickerEnfermedad.maxValue = 1
        bindingEnfermedad.numberPickerEnfermedad.displayedValues = arrayOf(
            "Diabetes","Presion"
        )
    }
    @SuppressLint("ClickableViewAccessibility")
    private fun showWindowFloat(width: Double, popupView: View) {
        val widthPixels = (resources.displayMetrics.widthPixels * width).toInt()
        val heightPixels = (resources.displayMetrics.heightPixels * 0.4).toInt()
        //condicion para verificar si el popupView tiene una vsta asignada
        if (popupView.parent != null) {
            (popupView.parent as ViewGroup).removeView(popupView)
        }
        popupWindow = PopupWindow(popupView, widthPixels, heightPixels, false) //Evita cerrarse cuando clickeas fuera del rango de la ventana
        popupWindow.showAtLocation(popupView.rootView, Gravity.CENTER, 0, 0)
    }


    private fun actividadCancelar(){
        //diseño del genero
        bindingGenero.iconAtras.setOnClickListener {
            popupWindow.dismiss()
        }

        bindingGenero.btnCancelar.setOnClickListener {
            popupWindow.dismiss()
        }

        //diseño del peso
        bindingPeso.iconAtrasPeso.setOnClickListener {
            popupWindow.dismiss()
        }
        bindingPeso.btnCancelarpeso.setOnClickListener {
            popupWindow.dismiss()
        }

        //diseño de altura
        bindingAltura.iconAtrasAltura.setOnClickListener {
            popupWindow.dismiss()
        }
        bindingAltura.btnCancelarAltura.setOnClickListener {
            popupWindow.dismiss()
        }

        //diseño de enfermedad
        bindingEnfermedad.iconAtrasEnfermedad.setOnClickListener {
            popupWindow.dismiss()
        }
        bindingEnfermedad.btnCancelarEnfermedad.setOnClickListener {
            popupWindow.dismiss()
        }
    }
    private fun codeClick(vista: ConstraintLayout, event: MotionEvent,
                          mtd: () -> Unit, TextViewActividad: TextView
    ): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val touchX = event.x.toInt() // Posición X del evento táctil
            val imageWidth = TextViewActividad.compoundDrawablesRelative[2]?.bounds?.width() ?: 0 // Ancho de la imagen

            // Ajustar las coordenadas para la nueva posición del TextView
            val textViewEndPosition = TextViewActividad.width
            val touchThreshold = textViewEndPosition - imageWidth

            // Verificar si el evento táctil ocurrió dentro de la región de la imagen
            if (touchX >= TextViewActividad.width - touchThreshold) {
                vista.visibility = View.VISIBLE
                mtd()
            }
        }
        return true
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun activityNumberPickerOne(mtdShow : () -> Unit, numberPicker : NumberPicker,
                                        TextViewActividad: TextView, vista : ConstraintLayout,
                                        selectTextView: TextView
    ) {
        numberPicker.setOnValueChangedListener { _, _, _ ->
            updateTextView(numberPicker,selectTextView)
        }
        TextViewActividad.setOnTouchListener { _, event ->
            codeClick(
                vista,
                event,
                mtdShow,
                TextViewActividad
            )
        }
        actividadCancelar()
        activityAccept()
    }
    @SuppressLint("ClickableViewAccessibility")
    private fun actividadTwoNumberPicker(mtdPeso: () -> Unit, numberPicker1: NumberPicker, numberPicker2: NumberPicker,
                                         textview1: TextView, textview2: TextView, TextViewActividadPeso: TextView
                                         , pesoVista: ConstraintLayout
    ) {

        // Actualiza el TextView mientras deslizas el NumberPicker1
        numberPicker1.setOnValueChangedListener { _, _, newVal ->
            textview1.text = newVal.toString()
        }

        // Actualiza el TextView mientras deslizas el NumberPicker2
        numberPicker2.setOnValueChangedListener { _, _, newVal ->
            textview2.text = newVal.toString()
        }

        TextViewActividadPeso.setOnTouchListener { _, event ->
            codeClick(pesoVista,event,mtdPeso,TextViewActividadPeso)
        }

    }
    @SuppressLint("ClickableViewAccessibility")
    private fun activityCalendarFN(){
        binding.TextViewActividadFechaNac.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val touchX = event.x.toInt() // Posición X del evento táctil
                val imageWidth =
                    binding.TextViewActividadFechaNac.compoundDrawablesRelative[2]?.bounds?.width()
                        ?: 0 // Ancho de la imagen

                // Ajustar las coordenadas para la nueva posición del TextView
                val textViewEndPosition = binding.TextViewActividadFechaNac.width
                val touchThreshold = textViewEndPosition - imageWidth

                // Verificar si el evento táctil ocurrió dentro de la región de la imagen
                if (touchX >= binding.TextViewActividadFechaNac.width - touchThreshold) {
                    showDatePickerDialog()
                }
            }
            false
        }
    }

    private fun activityAccept() {
        //diseño de genero
        bindingGenero.btnAceptar.setOnClickListener {
            confirmationDialogAcceptGenero(bindingGenero.textViewSelect,bindingGenero.numberPicker,
               binding.TextViewActividadGenero)
        }
        //diseño de peso
        bindingPeso.btnAceptarpeso.setOnClickListener {
            confirmationDialogAcceptWeight()
        }
        //diseño de altura
        bindingAltura.btnAceptarAltura.setOnClickListener {
            confirmacionDialogAceptarAltura()
        }
        bindingEnfermedad.btnAceptarEnfermedad.setOnClickListener {
            confirmationDialogAcceptEnfermedad(bindingEnfermedad.textViewSelectEnfermedad,bindingEnfermedad.numberPickerEnfermedad,
                  binding.TextViewActividadEnfermedad)
        }

    }

    private fun confirmationDialogAcceptEnfermedad(textViewSelectEnfermedad: TextView,
                                                   numberPickerEnfermedad: NumberPicker,
                                                   textViewActividadEnfermedad: TextView) {

        val dialogBuilder = AlertDialog.Builder(this, R.style.ConfirmationDialog)
        dialogBuilder.setTitle("Confirmar")
            .setMessage("¿Desea confirmar su tipo de enfermedad, ${textViewSelectEnfermedad.text}?")
            .setPositiveButton("Sí") { dialog, _ ->
                dialog.dismiss()
                val selectedValue = numberPickerEnfermedad.value
                val selectedText = numberPickerEnfermedad.displayedValues[selectedValue]
                textViewActividadEnfermedad.hint = selectedText
                editor.putString("enfermedad", selectedText)
                editor.apply()
                popupWindow.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }

    private fun confirmationDialogAcceptGenero(textViewSelect : TextView, numberPicker : NumberPicker,
                                          TextViewActividad : TextView
    ) {
        val dialogBuilder = AlertDialog.Builder(this, R.style.ConfirmationDialog)
        dialogBuilder.setTitle("Confirmar")
            .setMessage("¿Desea confirmar su genero, ${textViewSelect.text}?")
            .setPositiveButton("Sí") { dialog, _ ->
                dialog.dismiss()
                val selectedValue = numberPicker.value
                val selectedText = numberPicker.displayedValues[selectedValue]
                TextViewActividad.hint = selectedText
                editor.putString("genero", selectedText)
                editor.apply()
                popupWindow.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }
    private fun confirmationDialogAcceptWeight() {
        if(bindingPeso.textview2.text.equals("0") && bindingPeso.textview3.text.equals("0")){
           // dialogPeso(bindingPeso.numberPicker1,bindingPeso.numberPicker2,binding.TextViewActividadPeso,
               // bindingPeso.pesoVista)
            Toast.makeText(this, "ingrese una medida", Toast.LENGTH_SHORT).show()
        }else{
            dialogPeso(bindingPeso.numberPicker1,bindingPeso.numberPicker2,binding.TextViewActividadPeso)
        }

    }
    private fun dialogPeso(numberPicker1: NumberPicker, numberPicker2: NumberPicker,
                       TextViewActividad: TextView
    ) {
        val selectedValue1 = numberPicker1.value
        val selectedValue2 = numberPicker2.value
        val dialogBuilder = AlertDialog.Builder(this, R.style.ConfirmationDialog)
        dialogBuilder.setTitle("Confirmar")
            .setMessage("¿Desea confirmar su peso, ${selectedValue1}.${selectedValue2} lbs?")
            .setPositiveButton("Sí") { dialog, _ ->
                dialog.dismiss()
                TextViewActividad.hint = "$selectedValue1.$selectedValue2"
                editor.putString("peso", TextViewActividad.hint.toString())
                editor.apply()
                popupWindow.dismiss()
            }.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }.create().show()
    }
    private fun confirmacionDialogAceptarAltura() {
        dialogAltura(bindingAltura.numberPickerMetros,bindingAltura.numberPickerCentimetros,
            binding.TextViewActividadAltura,bindingAltura.alturaVista)
    }

    private fun dialogAltura(numberPickerPies: NumberPicker, numberPickerPulgadas: NumberPicker,
                             textViewActividadAltura: TextView, alturaVista: ConstraintLayout) {
        val selectedValue1 = numberPickerPies.value
        val selectedValue2 = numberPickerPulgadas.value
        val dialogBuilder = AlertDialog.Builder(this, R.style.ConfirmationDialog)
        dialogBuilder.setTitle("Confirmar")
            .setMessage("¿Desea confirmar su Altura ${selectedValue1}.${selectedValue2} metros?")
            .setPositiveButton("Sí") { dialog, _ ->
                dialog.dismiss()
                textViewActividadAltura.hint = "$selectedValue1.$selectedValue2"
                editor.putString("altura", textViewActividadAltura.hint.toString())
                editor.apply()
                popupWindow.dismiss()
            }.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }.create().show()
    }

    private fun updateTextView(numberPicker : NumberPicker, textViewSelect : TextView) {
        val selectedValue = numberPicker.value
        textViewSelect.text = numberPicker.displayedValues[selectedValue]
    }
    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { dia, mes, year -> currentDate(dia, mes, year) }
        datePicker.show(supportFragmentManager, "datepicker")
    }
    private fun currentDate(dia: Int, mes: Int, yearNac: Int) {
        val yearActual = Calendar.getInstance().get(Calendar.YEAR)
        val monthActual = Calendar.getInstance().get(Calendar.MONTH)
        val dayActual = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        var edad = yearActual - yearNac

        if (monthActual < mes || (monthActual == mes && dayActual < dia)) edad--
        binding.TextViewActividadFechaNac.hint = "$edad"
        val editor = sharedPref.edit()
        editor.putString("edad", binding.TextViewActividadFechaNac.hint.toString())
        editor.apply()

    }
    private fun Agregar_Usuario()
    {
        val genero = sharedPref.getString("genero", "")
        val enfermedad = sharedPref.getString("enfermedad", "")
        val peso = sharedPref.getString("peso", "")
        val altura = sharedPref.getString("altura", "")
        val edad = sharedPref.getString("edad", "")
        val Nombre = binding.txtNombrePerfil.text.toString()
        val Apellidos = binding.txtApellidoPerfil.text.toString()

        val fireDB: FirebaseFirestore = FirebaseFirestore.getInstance()

        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

        val UsuarioActual = firebaseAuth.currentUser

        val Id= UsuarioActual?.uid

        if (Id != null)
        {
            val usuario = hashMapOf("ID" to Id,"Nombres" to Nombre , "Apellidos" to Apellidos ,
                                    "Edad" to edad, "Genero" to genero, "Altura" to altura,
                                    "Peso" to peso, "Enfermedad" to enfermedad)

            fireDB.collection("Usuarios").document(Id)
                .set(usuario)
                .addOnSuccessListener { Toast.makeText(this, "Perfil Creado Correctamente", Toast.LENGTH_SHORT).show()}
                .addOnFailureListener { Toast.makeText(this, "No se pudo crear el perfil", Toast.LENGTH_SHORT).show() }

        }
        else
        {Toast.makeText(this, "No se pudo crear el perfil", Toast.LENGTH_SHORT).show() }


    //limpiando los datos que tenga almacenado sharedPreference
        editor.clear()
        editor.apply()

        //siguiente pantalla
        startActivity(Intent(this, Menu::class.java))
    }
}