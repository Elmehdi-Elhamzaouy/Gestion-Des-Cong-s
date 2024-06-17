package com.example.gestion_de_conges.DataClass

data class Conge(
    var id: Long,
    val nom: String,
    val responsable: String,
    val type: String,
    val dateDebut: String,
    val dateFin: String,
    val nbJours: Int,
    val commentaire: String,
    val etat: String
)
