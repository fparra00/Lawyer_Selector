package com.example.lawyerselectorv2.adapters

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.lawyerselectorv2.LawyerDetailsActivity
import com.example.lawyerselectorv2.LawyerUser
import com.example.lawyerselectorv2.R
import com.example.lawyerselectorv2.TakeASelfieActivity
import com.example.lawyerselectorv2.classes.Lawyer
import kotlin.random.Random

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
        //Setter the Var of the Lawyers in the List
        holder.txtName.text = arrLaw[position].name
        holder.txtCountry.text = arrLaw[position].country
        holder.txtVal.text =
            "More of " + Random.nextInt(from = 0, until = 256).toString() + " valorations"
        //onClick in View
        holder.itemView.setOnClickListener {
            goToLawyerProfile()
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

        init {
            txtName = itemView.findViewById(R.id.lawName)
            txtCountry = itemView.findViewById(R.id.lawCountry)
            txtVal = itemView.findViewById(R.id.lawValorations)
        }
    }

    private fun goToLawyerProfile(){
        val intent:Intent = Intent(context, LawyerUser::class.java)
        context.startActivity(intent)
        context.overridePendingTransition( R.anim.left_to_right, R.anim.right_to_left );
    }
}