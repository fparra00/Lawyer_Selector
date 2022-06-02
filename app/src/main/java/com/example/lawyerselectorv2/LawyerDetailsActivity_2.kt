package com.example.lawyerselectorv2

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.LayoutAnimationController
import android.view.animation.TranslateAnimation
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_lawyer_details.autocomGender
import kotlinx.android.synthetic.main.activity_lawyer_details2.*


class LawyerDetailsActivity_2 : AppCompatActivity() {
    //Aux Vars
    lateinit var emailPref: String
    lateinit var getPrefs: SharedPreferences
    lateinit var lawyerDb: DocumentReference
    var iter: Int = 0

    //Vars from Form
    lateinit var lvlEnglish: String
    lateinit var lvlSpanish: String
    lateinit var lvlFrench: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lawyer_details2)
        //Init Shared Preferences
        getPrefs = getSharedPreferences("prefsFile", Context.MODE_PRIVATE)
        emailPref = getPrefs.getString("email", "null").toString()
        //Init Bd Var
        lawyerDb =
            Firebase.firestore.collection("Lawyer").document(emailPref).collection("CarreerDetails")
                .document("About")
        //Aux Functions
        auxParameters()
        //..onClicks..
        btnAddStudies.setOnClickListener {
            showLyStudies()
        }
        btnCreateLawyer2.setOnClickListener {
            addLawyersDetails()
        }
    }

    /**
     * Add in Bdd a Document and a Collection with the carreer details
     */
    private fun addLawyersDetails() {
        takeVarsFromForm()
        lawyerDb.set(
            mapOf(
                "level_english" to lvlEnglish,
                "level_french" to lvlFrench,
                "level_spanish" to lvlSpanish,
                "gender" to autocomGender.text.toString(),
                "studie1" to autocomDegree.text.toString(),
                "studie1_description" to txtCarreer1.text.toString(),
                "studie2" to autocomDegree2.text.toString(),
                "studie2_description" to txtCarreer2.text.toString(),
                "studie3" to autocomDegree3.text.toString(),
                "studie3_description" to txtCarreer3.text.toString(),
                "description" to txtDescription_2.text.toString()
            )
        ).addOnSuccessListener {
            //QUE HACER EN CASO DE QUE SE INTROUZCAN BIEN EN BDD
        }.addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Error adding document", e)
        }
    }

    /**
     * Check and Asign into a String the level of the Lenguages Selecteds
     */
    private fun takeVarsFromForm() {
        if (rge1.isChecked) lvlEnglish = resources.getString(R.string.l1)
        if (rge2.isChecked) lvlEnglish = resources.getString(R.string.l2)
        if (rge3.isChecked) lvlEnglish = resources.getString(R.string.l3)
        if (rgs1.isChecked) lvlSpanish = resources.getString(R.string.l1)
        if (rgs2.isChecked) lvlSpanish = resources.getString(R.string.l2)
        if (rgs3.isChecked) lvlSpanish = resources.getString(R.string.l3)
        if (rgf1.isChecked) lvlFrench = resources.getString(R.string.l1)
        if (rgf2.isChecked) lvlFrench = resources.getString(R.string.l2)
        if (rgf3.isChecked) lvlFrench = resources.getString(R.string.l3)
    }


    /**
     * Init the Adapters of Genders and Carreers
     */
    private fun auxParameters() {
        //PASAR A STRINGS
        val items = listOf(
            "Male",
            "Female",
            "I prefer not to say"
        )

        val items2 = listOf(
            "College Carreer",
            "Master/Postgraduate",
            "Doctorate"
        )
        val adapter = ArrayAdapter<String>(this, R.layout.list_item, )
        val adapter2 = ArrayAdapter<String>(this, R.layout.list_item, items2)
        autocomDegree.setAdapter(adapter2)
        autocomDegree2.setAdapter(adapter2)
        autocomDegree3.setAdapter(adapter2)
        autocomGender.setAdapter(adapter)
    }

    /**
     * Make visible the Linear Layout of Studies
     */
    private fun showLyStudies() {
        iter += 1
        if (iter == 1) {
            lyStudie1.visibility = View.VISIBLE
            showLy(lyStudie1)
        }
        if (iter == 2) {
            lyStudie2.visibility = View.VISIBLE
            showLy(lyStudie2)
        }
        if (iter == 3) {
            lyStudie3.visibility = View.VISIBLE
            showLy(lyStudie3)
        }
    }

    /**
     * Enable (LinearLayout -0.0f in Y respect her position) the Animations of the Studies
     */
    private fun showLy(ly: LinearLayout) {
        var set: AnimationSet = AnimationSet(true)
        var animation: Animation

        animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF,
            0.0f,
            Animation.RELATIVE_TO_SELF,
            0.0f,
            Animation.RELATIVE_TO_SELF,
            -0.2f,
            Animation.RELATIVE_TO_SELF,
            0.0f
        )
        animation.duration = 300
        set.addAnimation(animation)
        var controller: LayoutAnimationController = LayoutAnimationController(set, 0.25f)
        ly.layoutAnimation = controller
        ly.startAnimation(animation)
    }
}