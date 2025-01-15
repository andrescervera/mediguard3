package com.example.mediguard3

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Calendario.newInstance] factory method to
 * create an instance of this fragment.
 */
class Calendario : Fragment() {
    private lateinit var calendarView: CalendarView
    private lateinit var cargarDatosButton: Button
    private lateinit var personalList: ArrayList<String>
    private lateinit var requestQueue: RequestQueue
    private lateinit var guardias: ArrayList<Pair<String, String>>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendario, container, false)

        calendarView = view.findViewById(R.id.calendario)
        cargarDatosButton = view.findViewById(R.id.cargarDatosButton)

        requestQueue = Volley.newRequestQueue(requireContext())

        cargarDatosButton.setOnClickListener {
            val selectedDate = Calendar.getInstance().apply {
                timeInMillis = calendarView.date
            }
            val year = selectedDate.get(Calendar.YEAR)

            loadPersonalData()
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val date = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }

            val guardia = getGuardiaForDate(date)
            if (guardia != null) {
                val (person1, person2) = guardia
                val intent = Intent(requireContext(), ListaGuardia::class.java)
                intent.putStringArrayListExtra("guardias", arrayListOf(person1, person2))
                startActivity(intent)
            } else {
                Toast.makeText(
                    requireContext(),
                    "No hay guardia para el día $dayOfMonth/$month/$year",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return view
    }

    private fun loadPersonalData() {
        val year = 2023
        val url = "http://192.168.1.144/mediguard_db/obtenerPersonalCalendario.php?year=$year"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener { response ->
                // Manejar la respuesta exitosa
                if (!response.isNullOrEmpty()) {
                    val json = JSONArray(response)

                    // Generar las guardias aleatorias
                    personalList = ArrayList()
                    for (i in 0 until json.length()) {
                        personalList.add(json.getString(i))
                    }
                    generarGuardiasAleatorias()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Error al cargar los datos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            Response.ErrorListener { error ->
                // Manejar el error de la solicitud
                Toast.makeText(
                    requireContext(),
                    "Error al cargar los datos: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            })

        requestQueue.add(stringRequest)
    }

    private fun generarGuardiasAleatorias() {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = calendarView.date

        val year = calendar.get(Calendar.YEAR)
        val totalDays = if (year % 4 == 0) 366 else 365
        val numberOfShifts = personalList.size / 2
        val random = Random()
        guardias = ArrayList()

        for (i in 1..totalDays) {
            val person1Index = random.nextInt(personalList.size)
            var person2Index = random.nextInt(personalList.size)
            while (person2Index == person1Index) {
                person2Index = random.nextInt(personalList.size)
            }

            val person1 = personalList[person1Index]
            val person2 = personalList[person2Index]

            guardias.add(Pair(person1, person2))
        }
    }

    private fun getGuardiaForDate(date: Calendar): Pair<String, String>? {
        val dayOfYear = date.get(Calendar.DAY_OF_YEAR)

        return guardias.getOrNull(dayOfYear - 1)
    }
}