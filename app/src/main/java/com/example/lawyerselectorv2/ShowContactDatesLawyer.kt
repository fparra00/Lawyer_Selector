package com.example.lawyerselectorv2

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.lawyerselectorv2.adapters.LegalCasesAdapter
import com.example.lawyerselectorv2.classes.User
import com.example.lawyerselectorv2.navmenu_left_activities.MyLawyersActivity
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_my_legal_cases.*

class ShowContactDatesLawyer : AppCompatActivity() {
    //Aux Vars
    private lateinit var lawyerName: String
    private lateinit var legalCaseName: String
    private lateinit var lawyersRefCar: DocumentReference
    private lateinit var legalCaseRef: DocumentReference
    private lateinit var legalCaseRefUs: DocumentReference
    private lateinit var myLawyer: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_contact_dates_lawyer)
        topAppBar.menu.removeItem(R.id.iconFilterBot)

        //Aux Functions
        if (LegalCasesAdapter.lc != null) recoverLawyerDates()
    }

    private fun NotificationLawyer() {
        val layoutInflater = LayoutInflater.from(this)
        val promptView: View = layoutInflater.inflate(R.layout.cd_notification_lawyer_added, null)
        val alertDialog: AlertDialog = AlertDialog.Builder(this).create()
        alertDialog.setView(promptView);
        val btnNegative: Button = promptView.findViewById<View>(R.id.btnGoToLawyers) as Button
        btnNegative.setOnClickListener {
            goToMyLawyers()
        }
        alertDialog.show()
    }

    private fun recoverLawyerDates() {
        lawyerName = LegalCasesAdapter.lc!!.lawyer
        lawyersRefCar = Firebase.firestore.collection("Lawyer").document(lawyerName)
        lawyersRefCar.get().addOnSuccessListener { document ->
            myLawyer = User(
                //We set to empty since we don't need it
                0,
                document.data?.getValue("name") as String,
                document.data?.getValue("telephone") as String,
                LegalCasesAdapter.lc!!.lawyer,
                "",
                "",
                ""
            )
        }.addOnCompleteListener {
            insertLegalCaseInUserBdd(myLawyer)
        }
    }

    private fun insertLegalCaseInUserBdd(u: User) {

        legalCaseRefUs = Firebase.firestore.collection("User").document(LegalCasesAdapter.lc!!.user)
            .collection("MyLawyers")
            .document(u.country)
        legalCaseRefUs.set(
            mapOf(
                "legalCase" to LegalCasesAdapter.lc!!.tittle,
                "lawyerName" to u.name,
                "lawyerEmail" to u.country,
                "lawyerTelephone" to u.telephone
            )
        ).addOnSuccessListener {
            closeLegalCase()
        }.addOnFailureListener { e ->

            Log.w(ContentValues.TAG, "Error adding document", e)
        }
    }

    private fun closeLegalCase() {
        legalCaseName = LegalCasesAdapter.lc!!.tittle
        legalCaseRef = Firebase.firestore.collection("LegalCases").document(legalCaseName)
        legalCaseRef.get().addOnSuccessListener { document ->
            legalCaseRef.update("status", "2")
        }.addOnCompleteListener {
            NotificationLawyer()
        }
    }

    private fun goToMyLawyers() {
        val intent: Intent = Intent(this, MyLawyersActivity::class.java)
        this.startActivity(intent)
        this.overridePendingTransition(
            R.anim.right_toleft_into_windows,
            R.anim.left_to_right_into_windows
        );
    }

}
