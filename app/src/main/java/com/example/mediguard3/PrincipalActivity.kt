package com.example.mediguard3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mediguard3.databinding.ActivityPrincipalBinding

class PrincipalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrincipalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navView.setOnItemSelectedListener {
            when(it.itemId){

                R.id.navigation_encuadre -> replaceFragment(encuadre())
                R.id.navigation_personal -> replaceFragment(PersonalCentroFragment())
                R.id.navigation_calendar -> replaceFragment(Calendario())
                R.id.navigation_historico -> replaceFragment(historico())
                R.id.navigation_perfil -> replaceFragment(Perfil())

                else->{

                }
            }
    true
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()

    }
}