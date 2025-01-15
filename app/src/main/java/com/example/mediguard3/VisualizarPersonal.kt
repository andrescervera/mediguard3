package com.example.mediguard3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class VisualizarPersonal : AppCompatActivity() {
    private lateinit var txtNombre: TextView
    private lateinit var txtApellidos: TextView
    private lateinit var txtDNI: TextView
    private lateinit var txtEspecialidad: TextView
    private lateinit var txtCorreo: TextView
    // Agrega más campos de texto según los datos que deseas mostrar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_personal)

        val nombre = intent.getStringExtra("nombre")

        val url = "http://192.168.1.144/mediguard_db/visualizarPersona.php?nombre=$nombre"
        val request = Request.Builder()
            .url(url)
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@VisualizarPersonal, "Error al obtener la información", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                runOnUiThread {
                    if (response.isSuccessful && responseData != null) {
                        mostrarInformacionPersonal(responseData)
                    } else {
                        Toast.makeText(this@VisualizarPersonal, "Error al obtener la información", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun mostrarInformacionPersonal(responseData: String) {
        try {
            val jsonObject = JSONObject(responseData)

            // Obtener los valores de los campos del JSON
            val nombre = jsonObject.getString("nombre")
            val apellidos = jsonObject.getString("apellidos")
            val dni = jsonObject.getString("DNI")
            val especialidad = jsonObject.getString("especialidad")
            val correo = jsonObject.getString("correo")
            // Agrega más campos según los datos que deseas mostrar

            // Asignar los valores a los campos de texto correspondientes
            txtNombre.text = nombre
            txtApellidos.text = apellidos
            txtDNI.text = dni
            txtEspecialidad.text = especialidad
            txtCorreo.text = correo
            // Asigna los valores a otros campos de texto

        } catch (e: JSONException) {
            e.printStackTrace()
            Toast.makeText(this, "Error al procesar la información", Toast.LENGTH_SHORT).show()
        }

    }
}
