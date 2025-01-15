package com.example.mediguard3

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import androidx.recyclerview.widget.LinearLayoutManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [personalCentro.newInstance] factory method to
 * create an instance of this fragment.
 */
class PersonalCentroFragment : Fragment() {
    private lateinit var listViewPersonal: ListView
    private lateinit var btnEliminar: Button
    private lateinit var btnVisualizar: Button
    private lateinit var editTextBuscar: EditText
    private var selectedName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_personal_centro, container, false)
        listViewPersonal = view.findViewById(R.id.listViewPersonal)
        editTextBuscar = view.findViewById(R.id.editTextBuscar)
        btnEliminar = view.findViewById(R.id.btnEliminar)
        btnVisualizar = view.findViewById(R.id.btnVisualizar)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = "http://192.168.1.144/mediguard_db/obtenerPersonal.php"
        val btnAlta = view.findViewById<Button>(R.id.btnAlta)
        btnAlta.setOnClickListener {
            val i = Intent(activity, altaPersonal::class.java)
            startActivity(i)
        }
        btnVisualizar.setOnClickListener {
            if (selectedName != null) {
                val intent = Intent(activity, VisualizarPersonal::class.java)
                intent.putExtra("nombre", selectedName)
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "No se ha seleccionado ningún registro", Toast.LENGTH_SHORT).show()
            }
        }
        val request = StringRequest(Request.Method.GET, url,
            Response.Listener { response ->
                val personalList = mutableListOf<String>()

                // Procesar la respuesta obtenida directamente sin utilizar JSON
                val lines = response.split("\n")
                for (line in lines) {
                    val nombre = line.trim()
                    personalList.add(nombre)
                }

                // Crear el adaptador y establecerlo en el RecyclerView
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, personalList)
                listViewPersonal.adapter = adapter

                // Mostrar el Toast de operación exitosa
                Toast.makeText(requireContext(), "Operación exitosa", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                error.printStackTrace()

                // Mostrar el Toast de error
                Toast.makeText(requireContext(), "Error al obtener los datos", Toast.LENGTH_SHORT).show()
            })

        // Agregar la solicitud a la cola de solicitudes de Volley
        Volley.newRequestQueue(requireContext()).add(request)
        listViewPersonal.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            selectedName = listViewPersonal.getItemAtPosition(position) as String
            Toast.makeText(requireContext(), "Has seleccionado: $selectedName", Toast.LENGTH_SHORT).show()
        }

        // Manejar el evento de clic en el botón Eliminar
        btnEliminar.setOnClickListener {
            if (selectedName != null) {
                eliminarRegistro(selectedName!!)
            } else {
                Toast.makeText(requireContext(), "No se ha seleccionado ningún registro", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // ...
    private fun eliminarRegistro(nombre: String) {
        val url = "http://192.168.1.144/mediguard_db/eliminarBD.php"

        // Envía el nombre del registro a eliminar al servidor
        val request = object : StringRequest(Request.Method.POST, url,
            Response.Listener { response ->
                Toast.makeText(requireContext(), response, Toast.LENGTH_SHORT).show()

                // Filtrar la lista para eliminar el registro
                val adapter = listViewPersonal.adapter as ArrayAdapter<String>
                adapter.remove(nombre)
                adapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                Toast.makeText(
                    requireContext(),
                    "Error al eliminar el registro",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["nombre"] = nombre
                return params
            }
        }
        Volley.newRequestQueue(requireContext()).add(request)
    }



    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PersonalCentroFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
