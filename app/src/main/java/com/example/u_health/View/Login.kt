package com.example.u_health.View


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.u_health.R
import com.example.u_health.databinding.ActivityLoginBinding
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val GOOGLE_SIGN_IN = 100
    private val callbackManager = CallbackManager.Factory.create()
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val currentUser = firebaseAuth.currentUser

        if (currentUser != null)
        {
            startActivity(Intent(this, Menu::class.java))
            Toast.makeText(this, "Bienvenido :)",Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnLogin.setOnClickListener {
            if (validarUsuario())
            {
                firebaseAuth.signInWithEmailAndPassword(binding.txtCorreo.text.toString(), binding.txtContraseA.text.toString())
                    .addOnCompleteListener(this)
                    { task ->
                        if (task.isSuccessful)
                        {
                            val id=firebaseAuth.currentUser?.uid

                            if (id != null)
                            {
                                validar_perfil(id)
                                binding.txtCorreo.setText("")
                                binding.txtContraseA.setText("")
                            }
                        }
                        else
                        { Toast.makeText(this, "El Usuario y Clave no existen.", Toast.LENGTH_SHORT).show() }
                    }
            }
        }

        binding.btnSignIn.setOnClickListener { v ->
            val intent = Intent(v.context, CrearCuenta::class.java)
            startActivity(intent)
        }

        binding.btnGoogleSignIn.setOnClickListener {
            val Googleconf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleClient = GoogleSignIn.getClient(this, Googleconf)
            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
        }
    }

    private fun validarUsuario(): Boolean
    {
        try {
            var validaok = false

            if (binding.txtCorreo.text?.length?.equals(0)!!)
            {
                binding.txtCorreo.requestFocus()
                binding.txtCorreo.error = "Debe ingresar su correo electronico"
                return validaok
            }
            if (binding.txtContraseA.text?.length?.equals(0)!!) {
                binding.txtContraseA.requestFocus()
                binding.txtContraseA.error = "Debe ingresar una contraseña."
                return validaok
            }
            validaok = true
            return validaok
        } catch (e: Exception) {
            e.message?.let { Log.e("Error en validacion", it) }
            return false
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        callbackManager.onActivityResult(requestCode, resultCode, data)

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN)
        {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try
            {
                val account = task.getResult(ApiException::class.java)

                if (account != null)
                {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)

                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(this)
                        { task ->
                            if (task.isSuccessful)
                            {
                                val id=firebaseAuth.currentUser?.uid
                                if (id != null)
                                {
                                    validar_perfil(id)
                                    binding.txtCorreo.setText("")
                                    binding.txtContraseA.setText("")
                                }

                            }
                        }
                }
            } catch (e: ApiException) {
                val Mensaje = AlertDialog.Builder(this)
                Mensaje.setTitle("Algo ha Fallado")
                Mensaje.setMessage("No se ha podido agregar la cuenta correctamente, intente nuevamente")
                Mensaje.setPositiveButton("Aceptar", null)
                val dialog: AlertDialog = Mensaje.create()
                dialog.show()
            }
        }
    }

    fun validar_perfil(Id:String)
    {
        val fireDB: FirebaseFirestore = FirebaseFirestore.getInstance()

        val auth : FirebaseAuth= FirebaseAuth.getInstance()


        fireDB.collection("Usuarios").whereEqualTo(FieldPath.documentId(), Id).get()
            .addOnSuccessListener{
                if(!it.isEmpty)
                {
                    startActivity(Intent(this, Menu::class.java))
                    Toast.makeText(this, "Bienvenido Nuevamente:D", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else
                {
                    startActivity(Intent(this, CrearPerfil::class.java))
                    Toast.makeText(this, "Cree un perfil :)", Toast.LENGTH_SHORT).show()
                }
            }
    }

}