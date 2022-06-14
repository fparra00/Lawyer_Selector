package com.example.lawyerselectorv2.customDialogs


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lawyerselectorv2.R
import com.example.lawyerselectorv2.payments.PaymentCreditActivity
import kotlinx.android.synthetic.main.activity_cd_pay_pal.*


class cdPayPal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        //..onClicks
        btnGoToCases.setOnClickListener {
            goToPay()
        }
    }

    private fun goToPay(){

        val intent: Intent = Intent(this, PaymentCreditActivity::class.java)
        this.startActivity(intent)
        this.overridePendingTransition(
           R.anim.right_toleft_into_windows,
           R.anim.left_to_right_into_windows
        );
    }

}