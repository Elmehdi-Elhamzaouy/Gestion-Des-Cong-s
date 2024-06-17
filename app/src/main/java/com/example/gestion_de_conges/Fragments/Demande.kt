package com.example.gestion_de_conges.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.gestion_de_conges.R

class Demande : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_demande, container, false)
    }

    private lateinit var idTextView: TextView
    private lateinit var nomTextView: TextView
    private lateinit var responsableTextView: TextView
    private lateinit var typeTextView: TextView
    private lateinit var dateDebutTextView: TextView
    private lateinit var dateFinTextView: TextView
    private lateinit var nbJoursTextView: TextView
    private lateinit var commentaireTextView: TextView
    private lateinit var etatTextView: TextView
    private lateinit var fraExit: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        idTextView = view.findViewById(R.id.id)
        nomTextView = view.findViewById(R.id.nomTextView)
        responsableTextView = view.findViewById(R.id.responsableTextView)
        typeTextView = view.findViewById(R.id.typeTextView)
        dateDebutTextView = view.findViewById(R.id.dateDebutTextView)
        dateFinTextView = view.findViewById(R.id.dateFinTextView)
        nbJoursTextView = view.findViewById(R.id.nbJoursTextView)
        commentaireTextView = view.findViewById(R.id.commentaireTextView)
        etatTextView = view.findViewById(R.id.etatTextView)
        fraExit = view.findViewById(R.id.fraExit)

        fraExit.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager

            // Remove the current fragment
            fragmentManager.beginTransaction().remove(this@Demande).commit()
        }

        // Get the congé details from arguments
        val id = arguments?.getLong("id").toString()
        val nom = arguments?.getString("nom")
        val responsable = arguments?.getString("responsable")
        val type = arguments?.getString("type")
        val dateDebut = arguments?.getString("dateDebut")
        val dateFin = arguments?.getString("dateFin")
        val nbJours = arguments?.getInt("nbJours")
        val commentaire = arguments?.getString("commentaire")
        val etat = arguments?.getString("etat")

        // Display the details in TextViews
        idTextView.text = id
        nomTextView.text = nom
        responsableTextView.text = responsable
        typeTextView.text = type
        dateDebutTextView.text = dateDebut
        dateFinTextView.text = dateFin
        nbJoursTextView.text = nbJours.toString()
        commentaireTextView.text = commentaire
        etatTextView.text = etat
    }
}
