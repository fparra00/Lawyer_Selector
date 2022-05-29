package com.example.lawyerselectorv2

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lawyerselectorv2.classes.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signup2.*
import kotlin.random.Random

class SignupActivity_2 : AppCompatActivity() {

    //Aux Var
    lateinit var emailPref: String
    lateinit var userType: String
    lateinit var getPrefs: SharedPreferences
    lateinit var personalDatesLawyer: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup2)

        //Init Shared Preferences
        getPrefs = getSharedPreferences("prefsFile", Context.MODE_PRIVATE)
        emailPref = getPrefs.getString("email", "null").toString()
        userType = getPrefs.getString("userType", "null").toString()

        Toast.makeText(this, emailPref, Toast.LENGTH_LONG).show()

        //.... onClicks ....
        btnSignIn2.setOnClickListener {
            //Debemos de Comprobar Formulario Antes
            if (userType.equals("user")) createUser()
            if (userType.equals("lawyer")) createLawyer()
        }
        //Aux Functions
        setDropdowns()
    }


    //Function to set the text of the dropdowns
    private fun setDropdowns() {
        //Dropdown Items Prefix Tlf
        val items = listOf("+34", "+42", "+57", "+76", "+21", "+26", "+06")
        val adapter = ArrayAdapter<String>(this, R.layout.list_item, items)
        autocom.setAdapter(adapter)
        //Dropdown Items Prefix Country
        val items_count = listOf("Spain", "France", "Germany", "United States", "England")
        val adapter_count = ArrayAdapter<String>(this, R.layout.list_item, items_count)
        autocomcity.setAdapter(adapter_count)
    }

    //Function to Create the User in Firestore
    private fun createUser() {
        val db = Firebase.firestore
        val users = db.collection("User")
        users.document(emailPref).set(
            mapOf(
                "id" to generateId(),
                "name" to txtName.text.toString(),
                "telephone" to (autocom.text.toString() + " " + txtTlf.text),
                "country" to autocomcity.text.toString(),
                "city" to txtCity.text.toString(),
                "postal code" to txtPostal.text.toString(),
                "adress" to txtDirecion1.text.toString()
            )
        ).addOnSuccessListener {
            goToTakeAPhotoAct()
        }.addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
        }
    }

    private fun createLawyer() {
        //instanciamos una clase de user, y la vamos pasando por intents, y asi pillamos sus
        //personales
        personalDatesLawyer = User(
            generateId(),
            txtName.text.toString(),
            autocom.text.toString() + " " + txtTlf.text,
            autocomcity.text.toString(),
            txtCity.text.toString(),
            txtPostal.text.toString(),
            txtDirecion1.text.toString()
        )
    }

    //Function to generate a random ID
    private fun generateId(): Int {
        val id: Int = Random.nextInt(from = 0, until = 256)
        val prefs: SharedPreferences.Editor? =
            getSharedPreferences("prefsFile", Context.MODE_PRIVATE).edit()
        prefs!!.putInt("id", id)
        prefs.apply()
        return id
    }

    private fun goToTakeAPhotoAct() {
        val intent = Intent(this, TakeASelfieActivity::class.java)
        startActivity(intent)
    }


}

