package com.example.mediguard3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import android.annotation.SuppressLint
import android.content.Intent
import com.android.volley.RequestQueue
import com.android.volley.VolleyLog
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.Volley

lateinit var edtUsuario: EditText
lateinit var edtPassword: EditText
lateinit var btnLogin: Button
lateinit var btnRegister:Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtUsuario = findViewById(R.id.edtUsuario)
        edtPassword = findViewById(R.id.edtPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)

        btnLogin.setOnClickListener {
            iniciarSesion()
        }

        btnRegister.setOnClickListener{
            register()
        }
    }

    private fun register(){
        val i = Intent(this,Registro::class.java)
        startActivity(i)
    }

     fun iniciarSesion() {
         var usuario = edtUsuario.text.toString()
         var password = edtPassword.text.toString()

         var URL_ROOT = "http://192.168.1.144/mediguard_db/validar_usuario.php?usuario=$usuario&password=$password"
         val queue = Volley.newRequestQueue(this)
         val StringRequest = StringRequest(
             Request.Method.GET, URL_ROOT,
             Response.Listener<String> { response ->
                     if (response == "{\"id_jefe\":1,\"usuario\":\"mbeneyto\",\"password\":\"1234\"}") {
                         val intento1 = Intent(this, PrincipalActivity::class.java)
                         startActivity(intento1)
                         Toast.makeText(applicationContext, "Bienvenida", Toast.LENGTH_LONG)
                             .show()
                     } else {
                         Toast.makeText(
                             applicationContext,
                             "Credenciales incorrectas",
                             Toast.LENGTH_SHORT
                         ).show()
                     }

             },
             object : Response.ErrorListener {
                 override fun onErrorResponse(volleyError: VolleyError) {
                     Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_SHORT)
                         .show()
                 }
             })
         queue.add(StringRequest)
     }
    }

