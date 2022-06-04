package com.example.lawyerselectorv2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.LayoutAnimationController
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lawyerselectorv2.adapters.RecyclerLawyersAdapter
import com.example.lawyerselectorv2.classes.Lawyer
import com.example.lawyerselectorv2.classes.LawyerCarreer
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_principal_menu.*

class PrincipalMenuActivity : AppCompatActivity() {
    //Aux Var
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerLawyersAdapter.ViewHolder>? = null
    private var lawArr = ArrayList<Lawyer>()
    private lateinit var lawyersRef: CollectionReference
    private lateinit var lawyersRefCar: CollectionReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_menu)
        //Init Aux Vars
        lawyersRef = Firebase.firestore.collection("Lawyer")
        //Functions that need to Recalculate
        getAllLawyers()
        //Aux Functions
        checkFilters()
        checkTopMenu()
        //..onClicks
        btnCreateCase.setOnClickListener {
            goToLawyerProfile()
        }
    }

    private fun checkTopMenu() {
        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.iconFilterBot -> {
                    showLy(lyFilterContainer)
                    true
                }
                else -> false
            }
        }
    }

    private fun checkFilters() {
        chAll.setOnClickListener {
            getAllLawyers()
        }
        chCriminal.setOnClickListener {
            getFilterLawyers("criminal", "true")
        }
        chLabor.setOnClickListener {
            getFilterLawyers("labor", "true")
        }
        chFamily.setOnClickListener {
            getFilterLawyers("family", "true")
        }
        chCommercial.setOnClickListener {
            getFilterLawyers("commercial", "true")
        }
        chRealState.setOnClickListener {
            getFilterLawyers("real_state", "true")
        }
        chAdministrative.setOnClickListener {
            getFilterLawyers("administrative", "true")
        }
    }


    private fun getAllLawyers() {
        lawArr.clear()
        lawyersRef.get().addOnSuccessListener { result ->
            for (lawyer in result) {
                //Recover and Add in ArrayList the Details of her Carreers
                lawyersRefCar = Firebase.firestore.collection("Lawyer").document(lawyer.id)
                    .collection("CarreerDetails")
                lawyersRefCar.get().addOnSuccessListener { result ->
                    for (carr in result) {
                        //Recover and Save in a Object the Details of The Carreer
                        var lc: LawyerCarreer = LawyerCarreer(
                            carr.data.getValue("description") as String,
                            carr.data.getValue("gender") as String,
                            carr.data.getValue("level_english") as String,
                            carr.data.getValue("level_french") as String,
                            carr.data.getValue("level_spanish") as String,
                            carr.data.getValue("studie1") as String,
                            carr.data.getValue("studie1_description") as String,
                            carr.data.getValue("studie2") as String,
                            carr.data.getValue("studie2_description") as String,
                            carr.data.getValue("studie3") as String,
                            carr.data.getValue("studie3_description") as String
                        )
                        //Recover and Instantiate Lawyers
                        var l: Lawyer = Lawyer(
                            (lawyer.data.getValue("id") as Number).toInt(),
                            lawyer.data.getValue("name").toString(),
                            lawyer.data.getValue("telephone").toString(),
                            lawyer.data.getValue("country").toString(),
                            lawyer.data.getValue("city").toString(),
                            lawyer.data.getValue("postal code").toString(),
                            lawyer.data.getValue("adress").toString(),
                            lawyer.data.getValue("administrative").toString(),
                            lawyer.data.getValue("commercial").toString(),
                            lawyer.data.getValue("criminal").toString(),
                            lawyer.data.getValue("family").toString(),
                            lawyer.data.getValue("labor").toString(),
                            lc
                        )
                        lawArr.add(l)
                        //Set the Lawyers in the Adapter
                        layoutManager = LinearLayoutManager(this)
                        lawyerRecycler.layoutManager = layoutManager
                        adapter = RecyclerLawyersAdapter(this, lawArr)
                        lawyerRecycler.adapter = adapter
                    }

                }
            }
        }
    }

    private fun getFilterLawyers(field: String, value: String) {
        lawArr.clear()
        lawyersRef.whereEqualTo(field, value).get().addOnSuccessListener { result ->
            for (lawyer in result) {
                //Recover and Add in ArrayList the Details of her Carreers
                lawyersRefCar = Firebase.firestore.collection("Lawyer").document(lawyer.id)
                    .collection("CarreerDetails")
                lawyersRefCar.get().addOnSuccessListener { result ->
                    for (carr in result) {
                        //Recover and Save in a Object the Details of The Carreer
                        var lc: LawyerCarreer = LawyerCarreer(
                            carr.data.getValue("description") as String,
                            carr.data.getValue("gender") as String,
                            carr.data.getValue("level_english") as String,
                            carr.data.getValue("level_french") as String,
                            carr.data.getValue("level_spanish") as String,
                            carr.data.getValue("studie1") as String,
                            carr.data.getValue("studie1_description") as String,
                            carr.data.getValue("studie2") as String,
                            carr.data.getValue("studie2_description") as String,
                            carr.data.getValue("studie3") as String,
                            carr.data.getValue("studie3_description") as String
                        )
                        //Recover and Instantiate Lawyers
                        var l: Lawyer = Lawyer(
                            (lawyer.data.getValue("id") as Number).toInt(),
                            lawyer.data.getValue("name").toString(),
                            lawyer.data.getValue("telephone").toString(),
                            lawyer.data.getValue("country").toString(),
                            lawyer.data.getValue("city").toString(),
                            lawyer.data.getValue("postal code").toString(),
                            lawyer.data.getValue("adress").toString(),
                            lawyer.data.getValue("administrative").toString(),
                            lawyer.data.getValue("commercial").toString(),
                            lawyer.data.getValue("criminal").toString(),
                            lawyer.data.getValue("family").toString(),
                            lawyer.data.getValue("labor").toString(),
                            lc
                        )
                        lawArr.add(l)
                        //Set the Lawyers in the Adapter
                        layoutManager = LinearLayoutManager(this)
                        lawyerRecycler.layoutManager = layoutManager
                        adapter = RecyclerLawyersAdapter(this, lawArr)
                        lawyerRecycler.adapter = adapter
                    }
                }
            }
        }
    }

    private fun showLy(ly: LinearLayout) {

        var set: AnimationSet = AnimationSet(true)
        var animation: Animation? = null

        if (ly.visibility == View.GONE) {
            ly.visibility = View.VISIBLE
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
        }
        animation!!.duration = 300
        set.addAnimation(animation)
        var controller: LayoutAnimationController = LayoutAnimationController(set, 0.25f)
        ly.layoutAnimation = controller
        ly.startAnimation(animation)
    }

    private fun goToLawyerProfile() {
        val intent: Intent = Intent(this, CreateLegalCaseActivity::class.java)
        this.startActivity(intent)
        this.overridePendingTransition(R.anim.right_toleft_into_windows, R.anim.left_to_right_into_windows);
    }


}






