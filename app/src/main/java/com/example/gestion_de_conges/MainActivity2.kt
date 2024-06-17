package com.example.gestion_de_conges

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.example.gestion_de_conges.Database.DBGestion
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity2 : AppCompatActivity() {
    private lateinit var debutEditText: EditText
    private lateinit var finEditText: EditText
    private lateinit var nbJoursEditText: EditText
    private lateinit var saveTextView: TextView
    private lateinit var demandeur: TextView
    private lateinit var spinner: Spinner
    private lateinit var coment: EditText
    private lateinit var valideCheckBox: CheckBox
    private lateinit var exit: View
    private lateinit var date_debut: View
    private lateinit var date_fin: View

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        debutEditText = findViewById(R.id.debut)
        finEditText = findViewById(R.id.fin)
        nbJoursEditText = findViewById(R.id.nb_jours)
        saveTextView = findViewById(R.id.save)
        demandeur = findViewById(R.id.demandeur)
        coment = findViewById(R.id.textArea)
        spinner = findViewById(R.id.type_conges)
        valideCheckBox = findViewById(R.id.valide)
        exit = findViewById(R.id.exit)

        date_debut = findViewById(R.id.date_debut)
        date_fin = findViewById(R.id.date_fin)


        demandeur.setText(intent.getStringExtra("NOM"))


        val editTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkFieldsFilled()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        debutEditText.addTextChangedListener(editTextWatcher)
        finEditText.addTextChangedListener(editTextWatcher)
        nbJoursEditText.addTextChangedListener(editTextWatcher)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                checkFieldsFilled()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        valideCheckBox.setOnCheckedChangeListener { _, _ ->
            checkFieldsFilled()
        }

        checkFieldsFilled()

        saveTextView.setOnClickListener {
            val dbgestion = DBGestion(this)

            val demandeur = demandeur.text.toString()
            val debut = debutEditText.text.toString()
            val fin = finEditText.text.toString()
            val nbJours = nbJoursEditText.text.toString().toInt()
            val typeConges = spinner.selectedItem.toString()
            val comentaire = coment.text.toString()

            dbgestion.addConge(demandeur, "default responsable", typeConges, debut, fin, nbJours, comentaire, "En cour")

            Toast.makeText(this, "Demande ajoute avec succes", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        exit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        date_debut.setOnClickListener {
            showDatePicker(debutEditText)
        }
        date_fin.setOnClickListener {
            showDatePicker(finEditText)
        }
    }

    private fun checkFieldsFilled() {
        val debut = debutEditText.text.toString()
        val fin = finEditText.text.toString()
        val nbJours = nbJoursEditText.text.toString()
        val typeConges = spinner.selectedItem.toString()
        val isChecked = valideCheckBox.isChecked

        saveTextView.isEnabled = typeConges.isNotEmpty() && debut.isNotEmpty() && fin.isNotEmpty() && nbJours.isNotEmpty() && typeConges != "Type" && isChecked

        if (saveTextView.isEnabled) {
            saveTextView.setTextColor(0xFFE8EDF2.toInt())
        } else {
            saveTextView.setTextColor(resources.getColor(android.R.color.darker_gray))
        }
    }
    private fun showDatePicker(field: EditText) {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate.time)
                field.setText(formattedDate)

                // Calculate and set the number of days
                val debutDateString = debutEditText.text.toString()
                val finDateString = finEditText.text.toString()
                if (debutDateString.isNotEmpty() && finDateString.isNotEmpty()) {
                    val debutDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(debutDateString)
                    val finDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(finDateString)
                    val diffInMillis = finDate.time - debutDate.time
                    val diffInDays = diffInMillis / (1000 * 60 * 60 * 24)
                    val days = diffInDays + 1
                    nbJoursEditText.setText(days.toString())
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }


}
