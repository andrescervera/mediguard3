package com.example.mediguard3

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject


lateinit var edtNombre: EditText
lateinit var edtApellidos: EditText
lateinit var edtDNI: EditText
lateinit var edtEspe:EditText
lateinit var edtCorreo: EditText

class altaPersonal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alta_personal)
        btnAtras = findViewById(R.id.btnAtras)
        btnRegister = findViewById(R.id.btnRegister)
        edtNombre = findViewById(R.id.edtNombre)
        edtApellidos = findViewById(R.id.edtApellidos)
        edtDNI = findViewById(R.id.edtDNI)
        edtEspe = findViewById(R.id.edtEspe)
        edtCorreo = findViewById(R.id.edtCorreo)


        btnRegister.setOnClickListener() {
            register()
        }

        btnAtras.setOnClickListener{
            atras()
        }
    }

    private fun atras(){
        val i = Intent(this,PrincipalActivity::class.java)
        startActivity(i)
    }

    private fun register(){
        //getting the record values
        var nombre = edtNombre.text.toString()
        var apellidos = edtApellidos.text.toString()
        var DNI = edtDNI.text.toString()
        var especialidad = edtEspe.text.toString()
        var correo = edtCorreo.text.toString()

        val url = "http://192.168.1.144/mediguard_db/altaPersonal.php"

        //creating volley string request
        val request = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { response ->
                try {
                    val intento1 = Intent(this, PrincipalActivity::class.java)
                    startActivity(intento1)
                    Toast.makeText(applicationContext, "Personal dado de alta", Toast.LENGTH_LONG)
                        .show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["nombre"] = nombre
                params["apellidos"] = apellidos
                params["DNI"] = DNI
                params["especialidad"] = especialidad
                params["correo"] = correo
                return params
            }
        }

        //adding request to queue
        Volley.newRequestQueue(this).add(request)

    }
}