package com.example.gestion_de_conges

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.gestion_de_conges.Database.DBGestion
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity4 : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var add_btn: FloatingActionButton
    private lateinit var dbGestion: DBGestion

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        dbGestion = DBGestion(this)
        listView = findViewById(R.id.listView)

        val users = dbGestion.getAllUser().map { it.user }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, users)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedUser = parent.getItemAtPosition(position).toString()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("user", selectedUser)
            finish()
            startActivity(intent)
        }

        add_btn = findViewById(R.id.fab)
        add_btn.setOnClickListener {
            val intent = Intent(this, User::class.java)
            startActivity(intent)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        dbGestion.close()
    }
}
