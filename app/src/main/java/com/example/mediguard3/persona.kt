package com.example.mediguard3

class persona(val nombre: String, val apellidos: String, val dni: String, val especialidad: String, val correo: String) {

    override fun toString(): String {
        return "$nombre $apellidos $dni $especialidad $correo"
    }
}