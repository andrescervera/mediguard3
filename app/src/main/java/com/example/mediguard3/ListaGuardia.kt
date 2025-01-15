package com.example.mediguard3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class ListaGuardia : AppCompatActivity() {
    private lateinit var listView: ListView
    //private lateinit var adapter: ArrayAdapter<String>
    private lateinit var btnRetroceder: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_guardia)

        listView = findViewById(R.id.listView)
        btnRetroceder = findViewById(R.id.btnRetroceder)

        val guardias = intent.getStringArrayListExtra("guardias")
        val guardiasList: ArrayList<String> = ArrayList(guardias)
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, guardiasList)

        btnRetroceder.setOnClickListener{
            atras()
        }
    }
    private fun atras(){
        val i = Intent(this,PrincipalActivity::class.java)
        startActivity(i)
    }
}