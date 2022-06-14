package com.example.lawyerselectorv2.adapters


import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.LayoutAnimationController
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.lawyerselectorv2.R
import com.example.lawyerselectorv2.classes.LegalCase
import com.example.lawyerselectorv2.payments.PaymentCreditActivity


/**
 * LegalCasesAdapter:
 *
 */
class LegalCasesAdapter(private val context: Activity, private val arrCase: ArrayList<LegalCase>) :
    Adapter<LegalCasesAdapter.ViewHolder>() {

    //Static Vars
    companion object Stlawyer {
         var lc: LegalCase? = null
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LegalCasesAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.legalcases_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: LegalCasesAdapter.ViewHolder, position: Int) {
        //Setter Text
        holder.txtName.text = arrCase[position].tittle
        holder.txtDescr.text = arrCase[position].description
        holder.txtHour.text = arrCase[position].hour
        holder.txtDate.text = arrCase[position].date
        holder.txtPeople.text = arrCase[position].dscOutsidePeople

        //Awaiting Response
        if (arrCase[position].status.equals("0")) {
            holder.txtStatus.text = "AWAITING RESPONSE"
            holder.txtStatus.setTextColor(context.resources.getColor(R.color.btnMain))
        }
        //Accepted Response
        if (arrCase[position].status.equals("1")) {
            holder.txtStatus.text = "ACCEPTED RESPONSE"
            holder.lyContact.visibility = View.VISIBLE
        }
        //Close Response
        if (arrCase[position].status.equals("2")) {
            holder.txtStatus.text = "CLOSE"
            holder.imgIcon.setImageResource((R.drawable.agreement_icon))
            holder.txtStatus.setTextColor(context.resources.getColor(R.color.etYellow))
        }
        //Dismissed Response
        if (arrCase[position].status.equals("3")) {
            holder.txtStatus.text = "DISMISSED RESPONSE"
            holder.txtStatus.setTextColor(context.resources.getColor(R.color.fontError))
        }

        //..onClicks..
        holder.lyContainer.setOnClickListener {
            showLy(holder.lyInfo)
        }

        holder.lyContact.setOnClickListener {
            goToMethodPage(position)
        }
    }

    override fun getItemCount(): Int {
        return arrCase.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Vars and Initialize the Layout
        var txtName: TextView
        var txtDescr: TextView
        var txtStatus: TextView
        var txtHour: TextView
        var txtDate: TextView
        var txtPeople: TextView
        var lyContainer: LinearLayout
        var lyInfo: LinearLayout
        var lyContact: LinearLayout
        var imgIcon: ImageView


        init {
            txtName = itemView.findViewById(R.id.caseTittle)
            txtDescr = itemView.findViewById(R.id.caseDescription)
            txtStatus = itemView.findViewById(R.id.txtStatus)
            txtHour = itemView.findViewById(R.id.txtHourlc)
            txtDate = itemView.findViewById(R.id.txtDatelc)
            txtPeople = itemView.findViewById(R.id.txtPeoplelc)
            lyContainer = itemView.findViewById(R.id.lyCard)
            lyInfo = itemView.findViewById(R.id.lyInfo)
            imgIcon = itemView.findViewById(R.id.imgHammer)
            lyContact = itemView.findViewById(R.id.lyCont2)
        }
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
        } else {
            ly.visibility = View.GONE
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
        animation!!.duration = 300
        set.addAnimation(animation)
        var controller: LayoutAnimationController = LayoutAnimationController(set, 0.25f)
        ly.layoutAnimation = controller
        ly.startAnimation(animation)
    }

    private fun goToMethodPage(i:Int) {
        lc = arrCase[i]
        val layoutInflater = LayoutInflater.from(context)
        val promptView: View = layoutInflater.inflate(R.layout.activity_cd_pay_pal, null)
        val alertDialog: AlertDialog = AlertDialog.Builder(context).create()
        alertDialog.setView(promptView);
        val btnNegative: Button = promptView.findViewById<View>(R.id.btnGoToCases) as Button
        btnNegative.setOnClickListener {
            goToPay()
        }
        alertDialog.show()
    }

    private fun goToPay() {
        val intent: Intent = Intent(context, PaymentCreditActivity::class.java)
        context.startActivity(intent)
        context.overridePendingTransition(
            R.anim.right_toleft_into_windows,
            R.anim.left_to_right_into_windows
        );
    }


}