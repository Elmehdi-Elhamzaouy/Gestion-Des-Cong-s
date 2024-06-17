package com.example.gestion_de_conges

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Admin_login : AppCompatActivity() {

    private lateinit var exit: View
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var login: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        exit = findViewById(R.id.exit)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        login = findViewById(R.id.login)

        exit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        login.setOnClickListener {
            val user = username.text.toString().toLowerCase()
            val pass = password.text.toString().toLowerCase()

            if (user.equals("admin") && pass.equals("admin")){
                val intent = Intent(this, Admin_page::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
            }

        }

    }
}