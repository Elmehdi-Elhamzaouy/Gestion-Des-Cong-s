package com.example.gestion_de_conges

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.gestion_de_conges.Database.DBGestion

class User : AppCompatActivity() {

    private lateinit var exit: View
    private lateinit var save: TextView
    private lateinit var cin: EditText
    private lateinit var nom: EditText
    private lateinit var phone: EditText
    private lateinit var email: EditText
    private lateinit var departement: Spinner

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        exit = findViewById(R.id.exit)
        save = findViewById(R.id.save)
        cin = findViewById(R.id.cin)
        nom = findViewById(R.id.nomComplet)
        phone = findViewById(R.id.phone)
        email = findViewById(R.id.email)
        departement = findViewById(R.id.departement)

        departement.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                checkFieldsFilled()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        exit.setOnClickListener {
            finish()
        }

        checkFieldsFilled()

        save.setOnClickListener {
            val dbgestion = DBGestion(this)

            val cin = cin.text.toString()
            val user = nom.text.toString()
            val phone = phone.text.toString().toInt()
            val email = email.text.toString()
            val departement = departement.selectedItem.toString()

            dbgestion.addUser(cin, user, phone, email, departement)

            Toast.makeText(this, "User ajoute avec succes", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun checkFieldsFilled() {
        val cin = cin.text.toString()
        val user = nom.text.toString()
        val phone = phone.text.toString()
        val email = email.text.toString()
        val departement = departement.selectedItem.toString()

        save.isEnabled = cin.isNotEmpty() && user.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty() && departement.isNotEmpty() && departement != "Département"

        if (save.isEnabled) {
            save.setTextColor(0xFFE8EDF2.toInt())
        } else {
            save.setTextColor(resources.getColor(android.R.color.darker_gray))
        }
    }
}