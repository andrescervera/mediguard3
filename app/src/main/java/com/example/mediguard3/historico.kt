package com.example.mediguard3

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.CalendarView
import android.widget.Spinner
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import java.util.ArrayList
import java.util.Calendar
import java.util.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [historico.newInstance] factory method to
 * create an instance of this fragment.
 */
class historico : Fragment() {
    private lateinit var yearSpinner: Spinner
    private lateinit var calendarView: CalendarView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_historico, container, false)

        yearSpinner = view.findViewById(R.id.yearSpinner)
        calendarView = view.findViewById(R.id.calendario)

        val years = arrayOf("2023", "2022", "2021")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, years)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        yearSpinner.adapter = adapter

        yearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedYear = parent?.getItemAtPosition(position).toString()
                // Cambiar el año en el CalendarView
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = calendarView.date
                calendar.set(Calendar.YEAR, selectedYear.toInt())
                val newDate = calendar.timeInMillis
                calendarView.setDate(newDate, true, true)

                // Realizar otras acciones según el año seleccionado
                Toast.makeText(requireContext(), "Año seleccionado: $selectedYear", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Manejar el evento de "nada seleccionado" si es necesario
            }
        }

        return view
    }
}