package com.example.lawyerselectorv2

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.LayoutAnimationController
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.lawyerselectorv2.classes.Lawyer
import com.example.lawyerselectorv2.classes.LegalCase
import com.example.lawyerselectorv2.navmenu_left_activities.MyLegalCasesActivity
import com.example.lawyerselectorv2.navmenu_left_activities.MyProfileActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_lawyer_user.*

class LawyerUser : AppCompatActivity() {
    //Aux Vars
    private lateinit var lawyerUser: Lawyer
    private lateinit var lyKnowledgeAnim: LinearLayout
    private lateinit var NavView: NavigationView
    private var legalCase: LegalCase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lawyer_user)
        //Initialize Vars
        lyKnowledgeAnim = lyViewCard
        topAppBar.menu.removeItem(R.id.iconFilterBot)
        NavView = navView

        //Check Intent
        if (intent != null) {
            if (intent.getSerializableExtra("arrLaw") != null) {
                lawyerUser = intent.getSerializableExtra("arrLaw") as Lawyer
                setDatesLawyer()
            }
            if (intent.getSerializableExtra("casoLegal") != null) {
                legalCase = intent.getSerializableExtra("casoLegal") as LegalCase
            }
        }

        //Functions Aux
        checkTopMenu()
        checkLeftMenu()

        //..onClicks..
        lyCreditCard.setOnClickListener {
            showLy(lyKnowledgeAnim, lyCreditCard, lawName4)
        }
        lyOnBio.setOnClickListener {
            showLy(lyBio, lyOnBio, lawBio)
        }
        lyOnStudies.setOnClickListener {
            showLy(lyStudies, lyOnStudies, lawStudies)
        }
        btnContactLawyer.setOnClickListener {
            if (legalCase != null) contactDialog(true) else contactDialog(false)
        }
    }


    private fun contactDialog(ch: Boolean) {
        if (!ch) showDialog() else NotificationLawyer()

    }

    private fun showDialog() {
        MaterialAlertDialogBuilder(this, R.style.CustomDialogTheme)
            .setTitle("UPS! :(")
            .setMessage("You haven't created any legal case yet, do you want to do it now?")
            .setNegativeButton("No, thanks") { dialog, which ->
            }
            .setPositiveButton("Okay!") { dialog, which ->
                goToCreateLegalCase()
            }.show()
    }


    private fun NotificationLawyer() {
        val layoutInflater = LayoutInflater.from(this)
        val promptView: View = layoutInflater.inflate(R.layout.cd_notification_waitanswer, null)
        val alertDialog: AlertDialog = AlertDialog.Builder(this).create()
        alertDialog.setView(promptView);
        val btnNegative: Button = promptView.findViewById<View>(R.id.btnGoToLawyers) as Button
        btnNegative.setOnClickListener {
            goToHome()
        }
        alertDialog.show()
    }



    private fun setDatesLawyer() {
        lwName.text = lawyerUser.name
        lwLocation.text = lawyerUser.country
        lwBio.text = lawyerUser.careerDetails!!.description
        lyStudie1.text = lawyerUser.careerDetails!!.studie1
        lwStudie1.text = lawyerUser.careerDetails!!.studie1_description
        lyStudie2.text = lawyerUser.careerDetails!!.studie2
        lwStudie2.text = lawyerUser.careerDetails!!.studie2_description
        lyStudie3.text = lawyerUser.careerDetails!!.studie3
        lwStudie3.text = lawyerUser.careerDetails!!.studie3_description
        lawEnglish.text = lawyerUser.careerDetails!!.level_english
        lawSpanish.text = lawyerUser.careerDetails!!.level_spanish
        lawFrench.text = lawyerUser.careerDetails!!.level_french
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

    private fun showLy(ly: LinearLayout, cly: LinearLayout, tv: TextView) {
        var set: AnimationSet = AnimationSet(true)
        var animation: Animation
        if (ly.visibility == View.GONE) {
            ly.visibility = View.VISIBLE
            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up_icon, 0)
            cly.setBackgroundColor(resources.getColor(R.color.white))
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
        } else {
            ly.visibility = View.GONE
            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_icon, 0)
            cly.setBackgroundColor(resources.getColor(R.color.bgGrey))
            animation = TranslateAnimation(
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f,
                Animation.RELATIVE_TO_SELF,
                0.0f
            )
        }
        animation.duration = 300
        set.addAnimation(animation)
        var controller: LayoutAnimationController = LayoutAnimationController(set, 0.25f)
        ly.layoutAnimation = controller
        ly.startAnimation(animation)
    }

    // .. goTo..
    private fun goToHome() {
        val intent: Intent = Intent(this, PrincipalMenuActivity::class.java)
        this.startActivity(intent)
        this.overridePendingTransition(
            R.anim.right_toleft_into_windows,
            R.anim.left_to_right_into_windows
        );
    }

    private fun goToCreateLegalCase() {
        val intent: Intent = Intent(this, CreateLegalCaseActivity::class.java)
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