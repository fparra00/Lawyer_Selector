package com.example.lawyerselectorv2.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.lawyerselectorv2.R
import com.example.lawyerselectorv2.classes.LegalCase

/**
 * LegalCasesAdapter:
 *
 */

class LegalCasesAdapter(private val context: Activity, private val arrCase: ArrayList<LegalCase>) :
    Adapter<LegalCasesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LegalCasesAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.legalcases_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: LegalCasesAdapter.ViewHolder, position: Int) {
        //Setter Text
        holder.txtName.text = arrCase[position].tittle
        holder.txtDescr.text = arrCase[position].description
    }

    override fun getItemCount(): Int {
        return arrCase.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Vars and Initialize the Layout
        var txtName: TextView
        var txtDescr: TextView

        init {
            txtName = itemView.findViewById(R.id.caseTittle)
            txtDescr = itemView.findViewById(R.id.caseDescription)
        }
    }


}