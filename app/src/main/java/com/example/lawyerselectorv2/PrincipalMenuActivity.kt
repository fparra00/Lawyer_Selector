package com.example.lawyerselectorv2

import android.os.Bundle
import android.util.Log
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
    private lateinit var lawyersRef: CollectionReference
    private lateinit var lawyersRefCar: CollectionReference

    private var lawArr = ArrayList<Lawyer>()
    private var lawDetails = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_menu)
        //Init Aux Vars
        lawyersRef = Firebase.firestore.collection("Lawyer")
        //AuxFunctions
        getAllLawyers()
    }


    private fun getAllLawyers() {
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
                        Log.d("prueba", l.name + " : " + lc.description)

                        lawArr.add(l)
                        //Set the Lawyers in the Adapter
                        layoutManager = LinearLayoutManager(this)
                        lawyerRecycler.layoutManager = layoutManager
                        adapter = RecyclerLawyersAdapter(this, lawArr)
                        lawyerRecycler.adapter = adapter
                    }

                }
            }
        }.addOnCompleteListener {
        }
    }
}




