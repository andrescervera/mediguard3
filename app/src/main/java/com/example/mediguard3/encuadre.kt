package com.example.mediguard3

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
lateinit var btnEncuadre : Button

/**
 * A simple [Fragment] subclass.
 * Use the [encuadre.newInstance] factory method to
 * create an instance of this fragment.
 */
class encuadre : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_encuadre, container, false)
        btnEncuadre = view.findViewById<Button>(R.id.btnEncuadre)
        btnEncuadre.setOnClickListener {
            val fragmento = Calendario()

            // Obtener el administrador de fragmentos de la actividad
            val fragmentManager = activity?.supportFragmentManager

            // Iniciar una transacción de fragmentos
            val fragmentTransaction = fragmentManager?.beginTransaction()

            // Reemplazar el fragmento actual por el nuevo fragmento
            fragmentTransaction?.replace(R.id.frame_layout, fragmento)

            // Agregar la transacción al back stack
            fragmentTransaction?.addToBackStack(null)

            // Confirmar la transacción
            fragmentTransaction?.commit()
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment encuadre.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            encuadre().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
