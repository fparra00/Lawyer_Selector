package com.example.lawyerselectorv2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lawyerselectorv2.adapters.RecyclerLawyersAdapter
import com.example.lawyerselectorv2.classes.Lawyer
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_principal_menu.*

class PrincipalMenuActivity : AppCompatActivity() {
    //Aux Var
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerLawyersAdapter.ViewHolder>? = null
    private lateinit var lawyersRef: CollectionReference
    var lawArr=ArrayList<Lawyer>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_menu)
        //Init Aux Vars
        lawyersRef = Firebase.firestore.collection("Lawyer")
        //AuxFunctions
        getAllLawyers()
    }


    private fun getAllLawyers(){
        lawyersRef.get().addOnSuccessListener { result ->
            for (lawyer in result) {
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
                    lawyer.data.getValue("labor").toString()
                )
                lawArr.add(l)
                //Set the Lawyers in the Adapter
                layoutManager = LinearLayoutManager(this)
                lawyerRecycler.layoutManager = layoutManager
                adapter = RecyclerLawyersAdapter(this,lawArr)
                lawyerRecycler.adapter = adapter
            }
        }
    }
}




