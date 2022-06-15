package com.example.lawyerselectorv2.navmenu_left_activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lawyerselectorv2.R
import com.example.lawyerselectorv2.adapters.LawyerPlusLcAdapter
import com.example.lawyerselectorv2.classes.ResultLawyerCase
import com.example.lawyerselectorv2.utilityPermissions.TelephoneUtility
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_my_legal_cases.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


class MyLawyersActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    //Aux Var
    private lateinit var lc: ResultLawyerCase
    private lateinit var refCase: CollectionReference
    private lateinit var emailPref: String
    private lateinit var userType: String
    private lateinit var getPrefs: SharedPreferences
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<LawyerPlusLcAdapter.ViewHolder>? = null
    private var caseArr = ArrayList<ResultLawyerCase>()
    private var TELEPHONE_REQUEST_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_lawyers)
        //Init Shared Preferences
        getPrefs = getSharedPreferences("prefsFile", Context.MODE_PRIVATE)
        emailPref = getPrefs.getString("email", "null").toString()
        userType = getPrefs.getString("userType", "null").toString()
        topAppBar.menu.removeItem(R.id.iconFilterBot)

        //Init Aux Vars
        refCase = Firebase.firestore.collection("User").document(emailPref).collection("MyLawyers")

        //Aux Functions
        getAllLawyerCases()
        requestPermissions()
    }

    private fun getAllLawyerCases() {
        refCase.get().addOnSuccessListener { result ->
            for (case in result) {
                lc = ResultLawyerCase(
                    case.data.getValue("lawyerEmail") as String,
                    case.data.getValue("lawyerName") as String,
                    case.data.getValue("lawyerTelephone") as String,
                    case.data.getValue("legalCase") as String,
                )
                caseArr.add(lc)
                //Set the Lawyers in the Adapter
                layoutManager = LinearLayoutManager(this)
                lawyerRecycler.layoutManager = layoutManager
                adapter = LawyerPlusLcAdapter(this, caseArr)
                lawyerRecycler.adapter = adapter
            }
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        if (TelephoneUtility.hasTelephonePermissions(this)) {
            return
        }
        EasyPermissions.requestPermissions(
            this,
            "You need to accept the Telephone Permissions",
            TELEPHONE_REQUEST_CODE,
            android.Manifest.permission.CALL_PHONE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

}