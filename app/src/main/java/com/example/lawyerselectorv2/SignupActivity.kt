package com.example.lawyerselectorv2

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    //Aux Var
    private lateinit var auth: FirebaseAuth

    companion object {
        val IMAGE_REQUEST_CODE = 100
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        //.... onClicks ....
        btnPhoto.setOnClickListener {
            pickImageGallery()
        }
        btnSignIn2.setOnClickListener {
            createUser()
        }


    }

    public override fun onStart() {
        super.onStart()
        auth = Firebase.auth
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
    }

    //Process to Create a User with a Email and a Password
    private fun createUser() {
        if (checkForm()) {
            auth.createUserWithEmailAndPassword(
                signinEmail.text.toString(),
                signinPassword.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        saveSharedPreferences(user?.email.toString())
                        if(cbLawyer.isChecked){
                            showDialog()
                        } else {
                            goToSignUp2()
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        txtError.setText(task.exception!!.message)
                        clearForm()
                    }
                }
        }
    }

    private fun showDialog(){
        AlertDialog.Builder(this, R.style.CustomDialogTheme)
            .setTitle("Are you a Lawyer?")
            .setMessage("Do you want to join to our team of lawyers?")
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->
                    goToJoinLawyer()
                })
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, which ->
                    goToSignUp2()
                })
            .show()
    }

    //Process to Check if the Form is Correctly Formatted
    private fun checkForm(): Boolean {
        if (signinEmail.text.toString().isNullOrBlank()) {
            showError(1)
            return false
        }
        if (signinPassword.text.toString().isNullOrBlank()) {
            showError(2)
            return false
        }
        if (signinEmail.text.toString().isNullOrBlank() && signinPassword.text.toString()
                .isNullOrBlank()
        ) {
            showError(1)
            return false
        }
        return true
    }

    //Process to Check and Show the Errors
    private fun showError(opc: Int) {
        if (opc == 1) {
            txtError.setText("Enter en Email to Continue, please :)")
        }
        if (opc == 2) {
            txtError.setText("Enter a Password to Continue, please :)")
        }
        if (opc == 3) {
            txtError.setText("You must enter both field to continue :)")
        }
    }

    //Process to Clear the Form
    private fun clearForm() {
        signinEmail.setText("")
        signinPassword.setText("")
        getWindow().getDecorView().clearFocus();
    }


    //Process to Pick the Photo
    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            //Aqui guardamos la foto
            //imagevire.setImageURI(data?.data)
        }
    }

    private fun goToSignUp2() {
        val intent = Intent(this, SignupActivity_2::class.java)
        startActivity(intent)
    }

    private fun goToJoinLawyer() {
        val intent = Intent(this, JoinLawyerActivity::class.java)
        startActivity(intent)
    }

    //Process to save the email in shared preferences
    fun saveSharedPreferences(em: String) {
        val prefs: SharedPreferences.Editor? =
            getSharedPreferences("prefsFile", Context.MODE_PRIVATE).edit()
        if (cbLawyer.isChecked) {
            prefs!!.putString("userType", "lawyer")
        } else {
            prefs!!.putString("userType", "user")
        }
        prefs!!.putString("email", em)
        prefs.apply()
    }


}