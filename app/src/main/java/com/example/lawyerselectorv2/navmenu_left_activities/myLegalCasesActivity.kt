package com.example.lawyerselectorv2.navmenu_left_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lawyerselectorv2.R
import com.example.lawyerselectorv2.adapters.LegalCasesAdapter
import com.example.lawyerselectorv2.classes.LegalCase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_my_legal_cases.*

class myLegalCasesActivity : AppCompatActivity() {
    //Aux Var
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<LegalCasesAdapter.ViewHolder>? = null
    private var caseArr = ArrayList<LegalCase>()
    private lateinit var userRef: CollectionReference
    private lateinit var lc: LegalCase
    private lateinit var userRefCase: CollectionReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_legal_cases)
        //Init Aux Vars
        userRef = Firebase.firestore.collection("User")
        //Aux Functions
        getAllLegalCases()
    }


    private fun getAllLegalCases(){
        userRefCase = Firebase.firestore.collection("User").document("fparraoboe4@gmail.com")
            .collection("Legal Cases")
        userRefCase.get().addOnSuccessListener { result ->
            for (case in result) {
             lc = LegalCase(
                  case.data.getValue("tittle") as String,
                  case.data.getValue("description") as String,
                  case.data.getValue("date") as String,
                  case.data.getValue("hour") as String,
                  case.data.getValue("external_people") as String,
                  case.data.getValue("description_of_external_people") as String,
                  )
                caseArr.add(lc)
                //Set the Lawyers in the Adapter
                layoutManager = LinearLayoutManager(this)
                lawyerRecycler.layoutManager = layoutManager
                adapter = LegalCasesAdapter(this, caseArr)
                lawyerRecycler.adapter = adapter
            }
        }
    }
}