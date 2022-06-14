package com.example.lawyerselectorv2.navmenu_left_activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lawyerselectorv2.PrincipalMenuActivity
import com.example.lawyerselectorv2.R
import com.example.lawyerselectorv2.SplashScreenActivity
import com.example.lawyerselectorv2.adapters.LegalCasesAdapter
import com.example.lawyerselectorv2.classes.LegalCase
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_my_legal_cases.*

class MyLegalCasesActivity : AppCompatActivity() {

    //Aux Var
    private lateinit var userRef: CollectionReference
    private lateinit var lc: LegalCase
    private lateinit var refCase: CollectionReference
    private lateinit var NavView: NavigationView
    private lateinit var emailPref: String
    private lateinit var userType: String
    private lateinit var getPrefs: SharedPreferences
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<LegalCasesAdapter.ViewHolder>? = null
    private var caseArr = ArrayList<LegalCase>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_legal_cases)
        //Init Aux Vars
        userRef = Firebase.firestore.collection("User")
        NavView = navView
        topAppBar.menu.removeItem(R.id.iconFilterBot)
        //Init Shared Preferences
        getPrefs = getSharedPreferences("prefsFile", Context.MODE_PRIVATE)
        emailPref = getPrefs.getString("email", "null").toString()
        userType = getPrefs.getString("userType", "null").toString()
        refCase = Firebase.firestore.collection("LegalCases")
        //Aux Functions
        getAllLegalCases()
        checkTopMenu()
        checkLeftMenu()
    }


    private fun getAllLegalCases() {
        refCase.get().addOnSuccessListener { result ->
            for (case in result) {
                if (case.data.getValue("user").equals(emailPref)) {
                    lc = LegalCase(
                        case.data.getValue("tittle") as String,
                        case.data.getValue("description") as String,
                        case.data.getValue("date") as String,
                        case.data.getValue("hour") as String,
                        case.data.getValue("outsidePeople") as String,
                        case.data.getValue("dscOutsidePeople") as String,
                        case.data.getValue("user") as String,
                        case.data.getValue("status") as String,
                        case.data.getValue("lawyer") as String,
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

    private fun checkTopMenu() {
        //Click on the Navigation Icon
        topAppBar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        //Click on the other Icons of the Top Bar
        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.iconHome -> {
                    goToHome()
                    true
                }
                else -> false
            }
        }
    }

    private fun checkLeftMenu() {
        NavView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuMyLawyers -> {
                    true
                }
                R.id.menuProfile -> {
                    goToMyProfile()
                    true
                }
                R.id.menuCases -> {
                    goToMyLegalCases()
                    true
                }
                R.id.menuLogOut -> {
                    logOut()
                    true
                }
                else -> false
            }
        }
    }

    //..goTo..
    private fun goToHome() {
        val intent: Intent = Intent(this, PrincipalMenuActivity::class.java)
        this.startActivity(intent)
        this.overridePendingTransition(
            R.anim.right_toleft_into_windows,
            R.anim.left_to_right_into_windows
        );
    }

    private fun goToMyProfile() {
        val intent: Intent = Intent(this, MyProfileActivity::class.java)
        this.startActivity(intent)
        this.overridePendingTransition(
            R.anim.right_toleft_into_windows,
            R.anim.left_to_right_into_windows
        );
    }

    private fun goToMyLawyers() {
        val intent: Intent = Intent(this, MyProfileActivity::class.java)
        this.startActivity(intent)
        this.overridePendingTransition(
            R.anim.right_toleft_into_windows,
            R.anim.left_to_right_into_windows
        );
    }

    private fun goToMyLegalCases() {
        val intent: Intent = Intent(this, MyLegalCasesActivity::class.java)
        this.startActivity(intent)
        this.overridePendingTransition(
            R.anim.right_toleft_into_windows,
            R.anim.left_to_right_into_windows
        );
    }

    private fun logOut() {
        val intent: Intent = Intent(this, SplashScreenActivity::class.java)
        this.startActivity(intent)
    }
}