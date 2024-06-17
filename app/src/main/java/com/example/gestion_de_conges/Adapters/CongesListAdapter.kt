package com.example.gestion_de_conges.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.gestion_de_conges.DataClass.Conge
import com.example.gestion_de_conges.R

class CongesListAdapter(context: Context, private val resource: Int, private val congesList: List<Conge>) :
    ArrayAdapter<Conge>(context, resource, congesList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(resource, null)

        val nomTextView: TextView = view.findViewById(R.id.nom)
        val typeConges: TextView = view.findViewById(R.id.typeConges)
        val nbJours: TextView = view.findViewById(R.id.nbJours)
        val etatTextView: TextView = view.findViewById(R.id.etat)

        val conge: Conge = congesList[position]
        nomTextView.text = conge.nom
        typeConges.text = conge.type
        nbJours.text = conge.nbJours.toString()
        etatTextView.text = conge.etat

        // Set text color based on etat
        when (conge.etat) {
            "En cour" -> etatTextView.setTextColor(ContextCompat.getColor(context, R.color.orange))
            "Accepté" -> etatTextView.setTextColor(ContextCompat.getColor(context, R.color.green))
            "Refusé" -> etatTextView.setTextColor(ContextCompat.getColor(context, R.color.red))
        }

        return view
    }

}
