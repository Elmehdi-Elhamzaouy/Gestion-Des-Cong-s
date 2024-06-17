package com.example.gestion_de_conges

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.gestion_de_conges.Adapters.CongesListAdapter
import com.example.gestion_de_conges.DataClass.Conge
import com.example.gestion_de_conges.Database.DBGestion
import com.example.gestion_de_conges.Fragments.Demande

class MainActivity : AppCompatActivity() {

    // Variable to keep track of back button press count
    private var backPressedCount = 0
    private var lastBackPressedTime = 0L

    private lateinit var demande_btn: Button
    private lateinit var nom: TextView
    private lateinit var resp: TextView
    private lateinit var listView: ListView
    private lateinit var etatSpinner: Spinner
    private lateinit var selectUser: CardView
    private lateinit var admin_btn: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        demande_btn = findViewById(R.id.demande)
        nom = findViewById(R.id.nomComp)
        resp = findViewById(R.id.responsable)
        etatSpinner = findViewById(R.id.etat)
        selectUser = findViewById(R.id.selectUser)
        admin_btn = findViewById(R.id.admin)

        nom.setText(intent.getStringExtra("user"))
        val dbgest = DBGestion(this)
        val nomComp = nom.text.toString()

        listView = findViewById(R.id.listView)
        if (nomComp == "") {
            Toast.makeText(this, "Tu peux sélectionner un employé", Toast.LENGTH_SHORT).show()
        } else {
            val congesList: List<Conge> = dbgest.getAllCongesByNom(nomComp)
            val adapter = CongesListAdapter(this, R.layout.list_item_conges, congesList)
            listView.adapter = adapter

            listView.setOnItemClickListener { parent, view, position, id ->
                val selectedConge = congesList[position]

                val fragment = Demande()
                val bundle = Bundle()
                bundle.putLong("id", selectedConge.id)
                bundle.putString("nom", selectedConge.nom)
                bundle.putString("responsable", selectedConge.responsable)
                bundle.putString("type", selectedConge.type)
                bundle.putString("dateDebut", selectedConge.dateDebut)
                bundle.putString("dateFin", selectedConge.dateFin)
                bundle.putInt("nbJours", selectedConge.nbJours)
                bundle.putString("commentaire", selectedConge.commentaire)
                bundle.putString("etat", selectedConge.etat)
                fragment.arguments = bundle

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }

            listView.setOnItemLongClickListener { parent, view, position, id ->
                val selectedConge = congesList[position]
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Supprimer Congé")
                builder.setMessage("Voulez-vous vraiment supprimer ce congé ?")
                builder.setPositiveButton("Supprimer") { dialog, which ->
                    val dbgest = DBGestion(this)
                    dbgest.deleteConge(selectedConge.id) // Assuming id is the primary key of the table
                    // Update the ListView
                    val updatedCongesList = dbgest.getAllCongesByNom(nomComp)
                    val adapter = CongesListAdapter(this, R.layout.list_item_conges, updatedCongesList)
                    listView.adapter = adapter
                }
                builder.setNegativeButton("Annuler") { dialog, which ->
                    // Do nothing, simply dismiss the dialog
                }
                val dialog = builder.create()
                dialog.show()
                true // Return true to indicate that the long click event has been consumed
            }


            etatSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedEtat = parent?.getItemAtPosition(position).toString()
                    val nomComp = nom.text.toString() // Assuming nom is the TextView for nomComp

                    val congesList = if (selectedEtat == "Etat") {
                        if (nomComp.isNotEmpty()) {
                            dbgest.getAllCongesByNom(nomComp)
                        } else {
                            dbgest.getAllConges()
                        }
                    } else {
                        if (nomComp.isNotEmpty()) {
                            dbgest.getAllCongesByEtatAndNom(selectedEtat, nomComp)
                        } else {
                            dbgest.getAllCongesByEtat(selectedEtat)
                        }
                    }
                    val adapter = CongesListAdapter(this@MainActivity, R.layout.list_item_conges, congesList)
                    listView.adapter = adapter
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle nothing selected
                }
            }

        }

        selectUser.setOnClickListener {
            val intent = Intent(this, MainActivity4::class.java)
            startActivity(intent)
        }
        admin_btn.setOnClickListener {
            val intent = Intent(this, Admin_login::class.java)
            startActivity(intent)
        }

        demande_btn.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra("NOM", nomComp)
            startActivity(intent)
        }


    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        val timeThreshold = 2000 // 2 seconds

        if (backPressedCount == 0 || currentTime - lastBackPressedTime > timeThreshold) {
            // First back press or time between presses exceeded threshold
            backPressedCount = 1
            lastBackPressedTime = currentTime
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
        } else {
            // Second back press within threshold
            backPressedCount = 0
            // Display dialog to confirm exit
            showExitDialog()
        }
    }

    private fun showExitDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Exit App")
        builder.setMessage("Are you sure you want to exit the app?")
        builder.setPositiveButton("Yes") { _, _ ->
            // Exit the app
            finishAffinity()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

}

