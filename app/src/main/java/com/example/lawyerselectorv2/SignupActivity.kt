package com.example.lawyerselectorv2

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signup.*
import java.nio.file.Path
import java.util.jar.Manifest

class SignupActivity : AppCompatActivity() {

    companion object {
        val IMAGE_REQUEST_CODE = 100
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        //onClicks
        btnPhoto.setOnClickListener {
            pickImageGallery()
        }

        btnSignIn.setOnClickListener {
            createUser()
        }
    }

    public override fun onStart() {
        super.onStart()
        auth = Firebase.auth

        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){

        }
    }

    //Process to Create a User with a Email and a Password
    private fun createUser() {
        if(checkForm()){
            auth.createUserWithEmailAndPassword(signinEmail.text.toString(), signinPassword.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                    } else {
                        // If sign in fails, display a message to the user.
                        txtError.setText(task.exception!!.message)
                        clearForm()
                    }
                }
        }
    }

    //Process to Check if the Form is Correctly Formatted
    private fun checkForm():Boolean{
        if(signinEmail.text.toString().isNullOrBlank()){
            showError(1)
            return false
        }
        if(signinPassword.text.toString().isNullOrBlank()){
            showError(2)
            return false
        }
        if(signinEmail.text.toString().isNullOrBlank() && signinPassword.text.toString().isNullOrBlank()){
            showError(1)
            return false
        }
        return true
    }

    //Process to Check and Show the Errors
    private fun showError(opc:Int){
        if (opc == 1){
            txtError.setText("Enter en Email to Continue, please :)")
        }
        if (opc == 2){
            txtError.setText("Enter a Password to Continue, please :)")
        }
        if (opc == 3){
            txtError.setText("You must enter both field to continue :)")
        }
    }

    //Process to Clear the Form
    private fun clearForm(){
        signinEmail.setText("")
        signinPassword.setText("")
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


}