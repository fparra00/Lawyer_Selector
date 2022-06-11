package com.example.lawyerselectorv2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signup.*

class LoginActivity : AppCompatActivity() {
    //Aux Var
    private lateinit var auth: FirebaseAuth
    lateinit var emailPref: String
    lateinit var userType: String
    lateinit var getPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //Init Aux Var
        auth = Firebase.auth
        //Init Shared Preferences
        getPrefs = getSharedPreferences("prefsFile", Context.MODE_PRIVATE)
        emailPref = getPrefs.getString("email", "null").toString()
        userType = getPrefs.getString("userType", "null").toString()
        //..onClicks..
        btnSignIn.setOnClickListener {
            login()
        }

    }

    private fun login() {
        auth.signInWithEmailAndPassword(signinEmail.text.toString(), signinPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    saveSharedPreferences(signinEmail.text.toString())
                    val user = auth.currentUser
                    goToPrincipalMenu()

                }
            }
    }

    private fun goToPrincipalMenu() {

        val intent: Intent = Intent(this, TakeASelfieActivity::class.java)
        this.startActivity(intent)
        this.overridePendingTransition(
            R.anim.right_toleft_into_windows,
            R.anim.left_to_right_into_windows
        );
    }

    /**
     * Save the eMail in Shared Preferences
     */
    fun saveSharedPreferences(em: String) {
        val prefs: SharedPreferences.Editor? =
            getSharedPreferences("prefsFile", Context.MODE_PRIVATE).edit()
        prefs!!.putString("userType", "user")
        prefs!!.putString("email", em)
        prefs.apply()
    }
}