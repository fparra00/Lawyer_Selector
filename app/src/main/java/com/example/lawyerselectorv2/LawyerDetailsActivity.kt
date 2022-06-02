package com.example.lawyerselectorv2

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.lawyerselectorv2.classes.User
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_lawyer_details.*
import kotlinx.android.synthetic.main.activity_lawyer_details.autocom
import kotlinx.android.synthetic.main.activity_lawyer_details.btnSignIn2
import kotlinx.android.synthetic.main.activity_signup2.*
import java.text.SimpleDateFormat
import kotlin.random.Random


class LawyerDetailsActivity : AppCompatActivity() {

    //Aux Var
    lateinit var emailPref: String
    lateinit var getPrefs: SharedPreferences
    lateinit var personalDatesLawyer: User
    lateinit var birthDate:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lawyer_details)

        auxParameters()

        //Init Shared Preferences
        getPrefs = getSharedPreferences("prefsFile", Context.MODE_PRIVATE)
        emailPref = getPrefs.getString("email", "null").toString()

        //Save the personal dates of the lawyer with the intent
        if (getIntent().getSerializableExtra("personalDatesLawyer") != null) personalDatesLawyer =
            getIntent().getSerializableExtra("personalDatesLawyer") as User

        //.... onClicks ....
        btnSignIn2.setOnClickListener {
            createLawyer()
        }
        txtCalendar_2.setOnClickListener {
            showCalendar()
        }
        txtBirthday_2.setOnClickListener {
            showCalendarBirth()
        }

    }

    //Function to Create the Lawyer in Firestore
    private fun createLawyer() {
        val db = Firebase.firestore
        val users = db.collection("Lawyer")
        users.document(emailPref).set(
            mapOf(
                "id" to personalDatesLawyer.id,
                "name" to personalDatesLawyer.name,
                "telephone" to personalDatesLawyer.telephone,
                "country" to personalDatesLawyer.country,
                "city" to personalDatesLawyer.city,
                "postal code" to personalDatesLawyer.postalCode,
                "adress" to personalDatesLawyer.addres,
                "email" to emailPref,
                "criminal" to chip.isChecked.toString(),
                "labor" to chip2.isChecked.toString(),
                "family" to chip3.isChecked.toString(),
                "commercial" to chip4.isChecked.toString(),
                "real_state" to chip5.isChecked.toString(),
                "administrative" to chip6.isChecked.toString(),
                "birthDate" to birthDate
            )
        ).addOnSuccessListener {
            //Que hacer cuando se agregue a la bd
        }.addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Error adding document", e)
        }
    }

    //Function to generate a random ID
    private fun generateId(): Int {
        return Random.nextInt(from = 0, until = 256)
    }

    private fun showCalendar() {
        var dateFin: String = ""
        val simpleFormat = SimpleDateFormat("dd/mm/yy")
        val datePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select date")
                .build()

        datePicker.show(supportFragmentManager, "tag")
        datePicker.addOnPositiveButtonClickListener {
            dateFin =
                simpleFormat.format(it.first).toString() + " - " + simpleFormat.format(it.second)
                    .toString()
            txtCalendar_2.setText(dateFin)
        }
    }

    private fun showCalendarBirth() {
        val simpleFormat = SimpleDateFormat("dd/mm/yy")
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .build()

        datePicker.show(supportFragmentManager, "tag")
        datePicker.addOnPositiveButtonClickListener {
            birthDate =
                simpleFormat.format(it).toString()
                    .toString()
            txtBirthday_2.setText(birthDate)
        }
    }

    private fun auxParameters(){
        txtCalendar_2.setEnabled(true);
        txtCalendar_2.setTextIsSelectable(true);
        txtCalendar_2.setFocusable(false);
        txtCalendar_2.setFocusableInTouchMode(false);

        txtBirthday_2.setEnabled(true);
        txtBirthday_2.setTextIsSelectable(true);
        txtBirthday_2.setFocusable(false);
        txtBirthday_2.setFocusableInTouchMode(false);

        //Dropdown Items Prefix Tlf
        val items = listOf(
            "Jaen Law School",
            "Albacete Law School",
            "Barcelona Law School",
            "Madrid Law School"
        )
        val adapter = ArrayAdapter<String>(this, R.layout.list_item, items)
        autocom.setAdapter(adapter)
    }
}