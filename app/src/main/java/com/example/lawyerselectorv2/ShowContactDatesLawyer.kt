package com.example.lawyerselectorv2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lawyerselectorv2.adapters.LegalCasesAdapter
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_my_legal_cases.*

class ShowContactDatesLawyer : AppCompatActivity() {
    //Aux Vars
    private lateinit var lawyerName: String
    private lateinit var lawyersRefCar: DocumentReference
    private var txtName: String? = null
    private var txtTelephone: String? = null
    private var txtEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_contact_dates_lawyer)
        topAppBar.menu.removeItem(R.id.iconFilterBot)
        //Aux Functions
        if (LegalCasesAdapter.lc != null) recoverLawyerDates()
    }

    private fun recoverLawyerDates() {
        lawyerName = LegalCasesAdapter.lc!!.lawyer
        lawyersRefCar = Firebase.firestore.collection("Lawyer").document(lawyerName)
        lawyersRefCar.get().addOnSuccessListener { document ->
            txtName = document.data?.getValue("name") as String
            txtTelephone = document.data?.getValue("telephone") as String
            txtEmail = document.data?.getValue("email") as String
        }
    }
}