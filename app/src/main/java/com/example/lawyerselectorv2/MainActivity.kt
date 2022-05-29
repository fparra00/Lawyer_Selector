package com.example.lawyerselectorv2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {

    //Aux Vars
    private val GOOGLE_SIGN_IN = 100

    enum class ProviderType {
        BASIC,
        GOOGLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // .... onClicks ....
        btnLogIn.setOnClickListener {
            goToLoginView()
        }
        btnLogInGoogle.setOnClickListener {
            goToLogInGoogle()
        }
        txtSignUp.setOnClickListener {
            goToRegisterView()
        }
    }

    /*
    Start the activity to Sign in With Google
     */
    private fun goToLogInGoogle() {
        val googleConf: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_web_id_google))
                .requestEmail()
                .build()

        val googleClient = GoogleSignIn.getClient(this, googleConf)
        startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
    }


    /*
    Recover the eMail of the Google Acccount
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
                val credential: AuthCredential =
                    GoogleAuthProvider.getCredential(account.idToken, null)
                FirebaseAuth.getInstance().signInWithCredential(credential)
            } catch (e: ApiException) {
            }
        }
    }

    fun goToLoginView() {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }

    fun goToRegisterView() {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }


}