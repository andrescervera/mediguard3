package com.example.mediguard3

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import org.json.JSONException
import org.json.JSONObject
import com.android.volley.VolleyError
import org.json.JSONArray
import android.annotation.SuppressLint
import com.android.volley.RequestQueue
import com.android.volley.VolleyLog
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.Volley

lateinit var btnAtras: Button

class Registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        btnAtras = findViewById(R.id.btnAtras)
        edtUsuario = findViewById(R.id.edtUsuario)
        edtPassword = findViewById(R.id.edtPassword)
        btnRegister = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener() {
            register()
        }

        btnAtras.setOnClickListener{
            atras()
        }
    }

    private fun atras(){
        val i = Intent(this,MainActivity::class.java)
        startActivity(i)
    }

    private fun register(){
        //getting the record values
        var usuario = edtUsuario.text.toString()
        var password = edtPassword.text.toString()

        val url = "http://192.168.1.144/mediguard_db/registro.php"

        //creating volley string request
        val request = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { response ->
                try {
                    val intento1 = Intent(this, PrincipalActivity::class.java)
                    startActivity(intento1)
                    Toast.makeText(applicationContext, "Usuario creado correctamente", Toast.LENGTH_LONG)
                        .show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["usuario"] = usuario
                params["password"] = password
                return params
            }
        }

        //adding request to queue
        Volley.newRequestQueue(this).add(request)

    }
}
