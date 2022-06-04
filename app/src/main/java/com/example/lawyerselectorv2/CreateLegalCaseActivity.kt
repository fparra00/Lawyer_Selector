package com.example.lawyerselectorv2

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.LayoutAnimationController
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.android.synthetic.main.activity_create_legal_case.*
import kotlinx.android.synthetic.main.activity_lawyer_details.*
import java.text.SimpleDateFormat

class CreateLegalCaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_legal_case)
        //Aux Functions
        auxParameters()
        // .. onClicks..
        txtDate_law.setOnClickListener {
            showCalendar()
        }
        txtHour_law.setOnClickListener {
            showHour()
        }
        rge1.setOnClickListener{
            if(rge1.isChecked && lyPerson.visibility==View.GONE) showLy(lyPerson)
        }
        rge2.setOnClickListener {
            if(lyPerson.visibility==View.VISIBLE) lyPerson.visibility=View.GONE
        }
    }

    private fun showHour() {
        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(10)
                .build()
        val timeformat = SimpleDateFormat("HH:mm a")
        picker.show(supportFragmentManager, "tag");
    }

    private fun showCalendar() {
        var dateFin: String = ""
        val simpleFormat = SimpleDateFormat("dd/mm/yy")
        val datePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select date")
                .build()

        datePicker.show(supportFragmentManager, "tag")
        datePicker.addOnPositiveButtonClickListener {
            dateFin =
                simpleFormat.format(it.first).toString() + " - " + simpleFormat.format(it.second)
                    .toString()
            txtDate_law.setText(dateFin)
        }
    }

    private fun auxParameters() {
        txtDate_law.setEnabled(true);
        txtDate_law.setTextIsSelectable(true);
        txtDate_law.setFocusable(false);
        txtDate_law.setFocusableInTouchMode(false);

        txtHour_law.setEnabled(true);
        txtHour_law.setTextIsSelectable(true);
        txtHour_law.setFocusable(false);
        txtHour_law.setFocusableInTouchMode(false);
    }

    private fun showLy(ly: LinearLayout) {

        var set: AnimationSet = AnimationSet(true)
        var animation: Animation? = null

        if (ly.visibility == View.GONE) {
            ly.visibility = View.VISIBLE
            animation = TranslateAnimation(
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                -0.2f,
                Animation.RELATIVE_TO_SELF,
                0.0f
            )
        }
        animation!!.duration = 300
        set.addAnimation(animation)
        var controller: LayoutAnimationController = LayoutAnimationController(set, 0.25f)
        ly.layoutAnimation = controller
        ly.startAnimation(animation)
    }
}