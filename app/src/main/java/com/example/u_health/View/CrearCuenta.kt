package com.example.u_health.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.example.u_health.R
import com.example.u_health.databinding.ActivityCrearCuentaBinding
import com.google.firebase.auth.FirebaseAuth

class CrearCuenta : AppCompatActivity()
{
    private lateinit var binding:ActivityCrearCuentaBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        //

        binding = ActivityCrearCuentaBinding.inflate(layoutInflater)
        val view= binding.root
        setContentView(view)

        val toolbar: Toolbar = binding.tbCrearCuenta
        setSupportActionBar(toolbar)
        supportActionBar!!.title = getString(R.string.btnCrearCuenta)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.btnCrearCuenta.setOnClickListener{
            if (valida().equals(true))
            {
                addCuentaUsuario()

                //onBackPressed()

            }
            else
            {
                val Mensaje = AlertDialog.Builder(this)
                Mensaje.setTitle("Algo ha Fallado")
                Mensaje.setMessage("No se ha podido agregar la cuenta correctamente, intente nuevamente")
                Mensaje.setPositiveButton("Aceptar",null )
                val dialog:AlertDialog = Mensaje.create()
                dialog.show()
            }

        }

    }

    fun valida():Boolean
    {
        try{
            var validaok:Boolean=false
            //validacion de el correo
            if(binding.txtCrearCorreo.text?.length?.equals(0)!!)
            {
                binding.txtCrearCorreo.requestFocus()
                binding.txtCrearCorreo.setError("El correo es un valor requerido")
                return validaok
            }
            //validacion de la contraseña
            if(binding.txtCrearContraseA.text?.length?.equals(0)!!)
            {
                binding.txtCrearContraseA.requestFocus()
                binding.txtCrearContraseA.setError("Debe Ingresar una contraseña")
                return validaok
            }
            //validacion de la confirmacion de la contraseña
            if(binding.txtConfirmContraseA.text?.length?.equals(0)!!)
            {
                binding.txtConfirmContraseA.requestFocus()
                binding.txtConfirmContraseA.setError("Debe Ingresar la contraseña nuevamente")
                return validaok
            }

            val strpassword:String =if(binding.txtCrearContraseA.text!= null)
                binding.txtCrearContraseA.text.toString()
            else
                ""
            val strpasswordconfirmar:String=if(binding.txtConfirmContraseA.text!= null)
                binding.txtConfirmContraseA.text.toString()
            else
                ""
            if(strpassword.equals(strpasswordconfirmar)==false)
            {
                binding.txtCrearContraseA.requestFocus()
                binding.txtCrearContraseA.setError("La contraseña y la confirmacion no coinciden")
                return validaok
            }
            validaok=true
            return validaok
        }catch (e:Exception){
            e.message?.let{ Log.e("Error en valida",it)};
            return false;
        }
    }

    fun addCuentaUsuario()
    {
        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(binding.txtCrearCorreo.text.toString(),
            binding.txtCrearContraseA.text.toString())
            .addOnCompleteListener{task ->
                if (task.isSuccessful)
                {
                    val Mensaje = AlertDialog.Builder(this)
                    Mensaje.setTitle("Operación Exitosa")
                    Mensaje.setMessage("Se ha agregado la cuenta correctamente ;)")
                    Mensaje.setPositiveButton("Aceptar",null)
                    val dialog : AlertDialog = Mensaje.create()
                    dialog.show()
                    Toast.makeText(this,"El Usuario ha sido creado.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this,"El Usuario no ha sido creado.", Toast.LENGTH_SHORT).show();
                }
            }
    }



}