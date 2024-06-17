package com.example.gestion_de_conges.Database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.gestion_de_conges.DataClass.Conge
import com.example.gestion_de_conges.DataClass.UserData

class DBGestion(context: Context) : SQLiteOpenHelper(context, "BD_Gestion", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE Demande(id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT, responsable TEXT, type TEXT, date_debut TEXT, date_fin TEXT, nb_jours INTEGER, commentaire TEXT, etat TEXT)"
        )

        db.execSQL(
            "CREATE TABLE User(cin TEXT PRIMARY KEY, user TEXT, phone INTEGER, mail TEXT, departement TEXT)"
        )
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Demande")
        db.execSQL("DROP TABLE IF EXISTS User")
        onCreate(db)
    }

    fun addConge(nom: String, responsable: String, type: String, dateDebut: String, dateFin: String, nbJours: Int, commentaire: String, etat: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("nom", nom)
            put("responsable", responsable)
            put("type", type)
            put("date_debut", dateDebut)
            put("date_fin", dateFin)
            put("nb_jours", nbJours)
            put("commentaire", commentaire)
            put("etat", etat)
        }
        db.insert("Demande", null, values)
        db.close()
    }

    fun addUser(cin: String, user: String, phone: Int, mail: String, departement: String){
        val db = writableDatabase
        val values = ContentValues().apply {
            put("cin", cin)
            put("user", user)
            put("phone", phone)
            put("mail", mail)
            put("departement", departement)
        }
        db.insert("User", null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getAllUser(): List<UserData> {
        val users = mutableListOf<UserData>()
        val selectQuery = "SELECT * FROM User"
        val db = readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        cursor?.use {
            if (it.moveToFirst()) {
                do {
                    val cin = it.getString(it.getColumnIndex("cin"))
                    val user = it.getString(it.getColumnIndex("user"))
                    val phone = it.getInt(it.getColumnIndex("phone"))
                    val mail = it.getString(it.getColumnIndex("mail"))
                    val departement = it.getString(it.getColumnIndex("departement"))

                    val userTab = UserData(cin, user, phone, mail, departement)
                    users.add(userTab)
                } while (it.moveToNext())
            }
        }

        cursor?.close()
        return users
    }


    @SuppressLint("Range")
    fun getAllConges(): List<Conge> {
        val conges = mutableListOf<Conge>()
        val selectQuery = "SELECT * FROM Demande"
        val db = readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        cursor?.use {
            if (it.moveToFirst()) {
                do {
                    val id = it.getLong(it.getColumnIndex("id"))
                    val nom = it.getString(it.getColumnIndex("nom"))
                    val responsable = it.getString(it.getColumnIndex("responsable"))
                    val type = it.getString(it.getColumnIndex("type"))
                    val dateDebut = it.getString(it.getColumnIndex("date_debut"))
                    val dateFin = it.getString(it.getColumnIndex("date_fin"))
                    val nbJours = it.getInt(it.getColumnIndex("nb_jours"))
                    val commentaire = it.getString(it.getColumnIndex("commentaire"))
                    val etat = it.getString(it.getColumnIndex("etat"))

                    val conge = Conge(id, nom, responsable, type, dateDebut, dateFin, nbJours, commentaire, etat)
                    conges.add(conge)
                } while (it.moveToNext())
            }
        }

        cursor?.close()
        return conges
    }

    @SuppressLint("Range")
    fun getAllCongesByEtat(etat: String): List<Conge> {
        val conges = mutableListOf<Conge>()
        val selectQuery = "SELECT * FROM Demande WHERE etat = ?"
        val db = readableDatabase
        val cursor = db.rawQuery(selectQuery, arrayOf(etat))

        cursor?.use {
            if (it.moveToFirst()) {
                do {
                    val id = it.getLong(it.getColumnIndex("id"))
                    val nom = it.getString(it.getColumnIndex("nom"))
                    val responsable = it.getString(it.getColumnIndex("responsable"))
                    val type = it.getString(it.getColumnIndex("type"))
                    val dateDebut = it.getString(it.getColumnIndex("date_debut"))
                    val dateFin = it.getString(it.getColumnIndex("date_fin"))
                    val nbJours = it.getInt(it.getColumnIndex("nb_jours"))
                    val commentaire = it.getString(it.getColumnIndex("commentaire"))
                    val etat = it.getString(it.getColumnIndex("etat"))

                    val conge = Conge(id, nom, responsable, type, dateDebut, dateFin, nbJours, commentaire, etat)
                    conges.add(conge)
                } while (it.moveToNext())
            }
        }

        cursor?.close()
        return conges
    }

    @SuppressLint("Range")
    fun getAllCongesByNom(etat: String): List<Conge> {
        val conges = mutableListOf<Conge>()
        val selectQuery = "SELECT * FROM Demande WHERE nom = ?"
        val db = readableDatabase
        val cursor = db.rawQuery(selectQuery, arrayOf(etat))

        cursor?.use {
            if (it.moveToFirst()) {
                do {
                    val id = it.getLong(it.getColumnIndex("id"))
                    val nom = it.getString(it.getColumnIndex("nom"))
                    val responsable = it.getString(it.getColumnIndex("responsable"))
                    val type = it.getString(it.getColumnIndex("type"))
                    val dateDebut = it.getString(it.getColumnIndex("date_debut"))
                    val dateFin = it.getString(it.getColumnIndex("date_fin"))
                    val nbJours = it.getInt(it.getColumnIndex("nb_jours"))
                    val commentaire = it.getString(it.getColumnIndex("commentaire"))
                    val etat = it.getString(it.getColumnIndex("etat"))

                    val conge = Conge(id, nom, responsable, type, dateDebut, dateFin, nbJours, commentaire, etat)
                    conges.add(conge)
                } while (it.moveToNext())
            }
        }

        cursor?.close()
        return conges
    }

    @SuppressLint("Range")
    fun getAllCongesByEtatAndNom(etat: String, nom: String): List<Conge> {
        val conges = mutableListOf<Conge>()
        val selectQuery = "SELECT * FROM Demande WHERE etat = ? AND nom = ?"
        val db = readableDatabase
        val cursor = db.rawQuery(selectQuery, arrayOf(etat, nom))

        cursor?.use {
            if (it.moveToFirst()) {
                do {
                    val id = it.getLong(it.getColumnIndex("id"))
                    val congeNom = it.getString(it.getColumnIndex("nom"))
                    val responsable = it.getString(it.getColumnIndex("responsable"))
                    val type = it.getString(it.getColumnIndex("type"))
                    val dateDebut = it.getString(it.getColumnIndex("date_debut"))
                    val dateFin = it.getString(it.getColumnIndex("date_fin"))
                    val nbJours = it.getInt(it.getColumnIndex("nb_jours"))
                    val commentaire = it.getString(it.getColumnIndex("commentaire"))
                    val etat = it.getString(it.getColumnIndex("etat"))

                    val conge = Conge(id, congeNom, responsable, type, dateDebut, dateFin, nbJours, commentaire, etat)
                    conges.add(conge)
                } while (it.moveToNext())
            }
        }

        cursor?.close()
        return conges
    }

    fun updateEtat(id: Long, newEtat: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("etat", newEtat)
        }
        db.update("Demande", values, "id=?", arrayOf(id.toString()))
        db.close()
    }



    fun deleteConge(id: Long): Int {
        val db = writableDatabase
        val result = db.delete("Demande", "id=?", arrayOf(id.toString()))
        db.close()
        return result
    }



}