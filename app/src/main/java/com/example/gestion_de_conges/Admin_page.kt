package com.example.gestion_de_conges

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Spinner
import com.example.gestion_de_conges.Adapters.CongesListAdapter
import com.example.gestion_de_conges.DataClass.Conge
import com.example.gestion_de_conges.Database.DBGestion
import com.example.gestion_de_conges.Fragments.Admin_fragment

class Admin_page : AppCompatActivity(), Admin_fragment.OnEtatUpdateListener {

    private lateinit var listView: ListView
    private lateinit var etatSpinner: Spinner
    private lateinit var adapter: CongesListAdapter
    private lateinit var dbgest: DBGestion
    private lateinit var exit: View

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_page)

        listView = findViewById(R.id.listView)
        etatSpinner = findViewById(R.id.etat)
        exit = findViewById(R.id.exit)

        exit.setOnClickListener {
            finish()
        }


        dbgest = DBGestion(this)

        val congesList: List<Conge> = dbgest.getAllConges()
        adapter = CongesListAdapter(this, R.layout.list_item_conges, congesList)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedConge = congesList[position]

            val fragment = Admin_fragment()
            val bundle = Bundle()
            bundle.putLong("id", selectedConge.id) // Add ID to the bundle
            bundle.putString("nom", selectedConge.nom)
            bundle.putString("responsable", selectedConge.responsable)
            bundle.putString("type", selectedConge.type)
            bundle.putString("dateDebut", selectedConge.dateDebut)
            bundle.putString("dateFin", selectedConge.dateFin)
            bundle.putInt("nbJours", selectedConge.nbJours)
            bundle.putString("commentaire", selectedConge.commentaire)
            bundle.putString("etat", selectedConge.etat)
            fragment.arguments = bundle
            fragment.etatUpdateListener = this

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        etatSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedEtat = parent?.getItemAtPosition(position).toString()
                val congesList = if (selectedEtat == "Etat") {
                    dbgest.getAllConges()
                } else {
                    dbgest.getAllCongesByEtat(selectedEtat)
                }
                adapter.clear()
                adapter.addAll(congesList)
                adapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle nothing selected
            }
        }

    }

    override fun onEtatUpdated() {
        // Refresh the ListView
        val congesList: List<Conge> = dbgest.getAllConges()
        adapter.clear()
        adapter.addAll(congesList)
        adapter.notifyDataSetChanged()
    }

}
