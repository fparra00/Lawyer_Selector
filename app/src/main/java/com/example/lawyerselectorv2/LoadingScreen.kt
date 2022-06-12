package com.example.lawyerselectorv2

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.lawyerselectorv2.classes.Lawyer
import com.example.lawyerselectorv2.classes.LegalCase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.loading_screen.*

class LoadingScreen : AppCompatActivity() {

    //Aux Var
    lateinit var legalCase: LegalCase
    lateinit var recommendedLawyer: Lawyer
    lateinit var legalCaseDb: DocumentReference
    lateinit var emailPref: String
    lateinit var getPrefs: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_screen)

        //Send the legal case to the lawyer view
        if (intent.getSerializableExtra("casoLegal") != null) {
            legalCase = intent.getSerializableExtra("casoLegal") as LegalCase
        }
        //Init Vars
        getPrefs = getSharedPreferences("prefsFile", Context.MODE_PRIVATE)
        emailPref = getPrefs.getString("email", "null").toString()
        legalCaseDb =
            Firebase.firestore.collection("LegalCases").document(legalCase.tittle)
        //Aux Functions
        calculateRecommendedLawyer()
        addLegalCaseInBdd()
    }

    /**
     * Set a new legal case into the bdd like a document in a new collection
     */
    private fun addLegalCaseInBdd() {
        legalCaseDb.set(
            mapOf(
                "tittle" to legalCase.tittle,
                "description" to legalCase.description,
                "date" to legalCase.date,
                "hour" to legalCase.hour,
                "outsidePeople" to legalCase.outsidePeople,
                "dscOutsidePeople" to legalCase.dscOutsidePeople,
                "status" to "0",
                "user" to emailPref,
                "lawyer" to recommendedLawyer.email
            )
        ).addOnSuccessListener {
            enableAnimator()
        }.addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Error adding document", e)
        }
    }

    /**
     * Enable the Animation in Splash Screen
     */
    private fun enableAnimator() {
        var anim: Animation = AnimationUtils.loadAnimation(this, R.anim.left_to_right)
        lyLoadingScreen.animation = anim

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LawyerUser::class.java)
            intent.putExtra("casoLegal", legalCase)
            intent.putExtra("arrLaw", recommendedLawyer)
            startActivity(intent)
            finish()
        }, 3500)
    }

    /**
     * Calculate the Recommended Lawyer to the legal case of the user
     */
    private fun calculateRecommendedLawyer() {
        recommendedLawyer = PrincipalMenuActivity.allLawyer[4]
    }
}