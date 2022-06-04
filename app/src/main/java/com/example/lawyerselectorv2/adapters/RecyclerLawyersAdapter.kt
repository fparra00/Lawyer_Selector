package com.example.lawyerselectorv2.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.lawyerselectorv2.LawyerUser
import com.example.lawyerselectorv2.R
import com.example.lawyerselectorv2.classes.Lawyer
import kotlin.random.Random

/**
 * RecyclerLawyersAdapter:
 *
 */

class RecyclerLawyersAdapter(private val context: Activity, private val arrLaw: ArrayList<Lawyer>) :
    Adapter<RecyclerLawyersAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerLawyersAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.lawyer_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerLawyersAdapter.ViewHolder, position: Int) {
        //Setter Text
        holder.txtName.text = arrLaw[position].name
        holder.txtCountry.text = arrLaw[position].country
        holder.txtDescr.text = concatCarreer(position)
        //Set random valorations
        holder.txtVal.text =
            "More of " + Random.nextInt(from = 0, until = 256).toString() + " valorations"
        //Set the photo of Profile
        if (arrLaw[position].careerDetails.gender.equals("Male")){
            holder.imgLawyer.setImageResource(R.drawable.hombre)
        } else {
            holder.imgLawyer.setImageResource(R.drawable.mujer)
        }

        //onClick in View
        holder.itemView.setOnClickListener {
            goToLawyerProfile(position)
        }
    }

    override fun getItemCount(): Int {
        return arrLaw.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Vars and Initialize the Layout
        var txtName: TextView
        var txtCountry: TextView
        var txtVal: TextView
        var txtDescr: TextView
        var imgLawyer: ImageView

        init {
            txtName = itemView.findViewById(R.id.lawName)
            txtCountry = itemView.findViewById(R.id.lawCountry)
            txtVal = itemView.findViewById(R.id.lawValorations)
            txtDescr = itemView.findViewById(R.id.lawDescription)
            imgLawyer = itemView.findViewById(R.id.imgLawyer)
        }
    }

    private fun goToLawyerProfile(i: Int) {
        val intent: Intent = Intent(context, LawyerUser::class.java)
        intent.putExtra("arrLaw", arrLaw[i]);
        context.startActivity(intent)
        context.overridePendingTransition(R.anim.right_toleft_into_windows, R.anim.left_to_right_into_windows);
    }

    private fun concatCarreer(i: Int): String {
        var conc: String = ""
        if (arrLaw[i].commercial.equals("true")) {
            conc += "Commercial\n"
        }
        if (arrLaw[i].criminal.equals("true")) {
            conc += "Criminal\n"
        }
        if (arrLaw[i].family.equals("true")) {
            conc += "Family\n"
        }
        if (arrLaw[i].labor.equals("true")) {
            conc += "Labor\n"
        }
        return conc
    }
}