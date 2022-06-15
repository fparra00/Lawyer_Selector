package com.example.lawyerselectorv2.adapters


import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.lawyerselectorv2.R
import com.example.lawyerselectorv2.classes.ResultLawyerCase

/**
 * LegalCasesAdapter:
 *
 */
class LawyerPlusLcAdapter(
    private val context: Activity,
    private val arrCase: ArrayList<ResultLawyerCase>
) :
    Adapter<LawyerPlusLcAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LawyerPlusLcAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.legal_case_plus_lawyer_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: LawyerPlusLcAdapter.ViewHolder, position: Int) {
        //Setter Text
        holder.txtName.text = arrCase[position].lyName
        holder.txtLc.text = arrCase[position].lcTittle
        holder.txtNumber.text = arrCase[position].lyTelephone
        holder.txtEmail.text = arrCase[position].lyEmail

        //..onClicks..
        holder.btnCallL.setOnClickListener {
            enviarEmail(position)
        }
        holder.btnEmailL.setOnClickListener {
            onClickLlamada(position)
        }
    }

    override fun getItemCount(): Int {
        return arrCase.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Vars and Initialize the Layout
        var txtName: TextView
        var txtLc: TextView
        var txtNumber: TextView
        var txtEmail: TextView
        var btnCallL: LinearLayout
        var btnEmailL: LinearLayout


        init {
            txtName = itemView.findViewById(R.id.txtName_lc)
            txtLc = itemView.findViewById(R.id.txtCase_lc)
            txtNumber = itemView.findViewById(R.id.txtCase_lc2)
            txtEmail = itemView.findViewById(R.id.txtCase_lc2)
            btnCallL = itemView.findViewById(R.id.btnCall)
            btnEmailL = itemView.findViewById(R.id.btnEmail)
        }
    }


    private fun enviarEmail(i: Int) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        val TO = arrayOf(arrCase[i].lyEmail)

        emailIntent.data = Uri.parse("mailto:")
        emailIntent.type = "text/plain"
        emailIntent.putExtra(
            Intent.EXTRA_EMAIL,
            TO
        )
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Hi " + arrCase[i].lyName)
        emailIntent.putExtra(Intent.EXTRA_REFERRER, "Hifdsfds " + arrCase[i].lyName)

        emailIntent.putExtra(
            Intent.EXTRA_TEXT, "\n" +
                    "I have contacted you through LawyerSelector, and I wanted to comment..."
        )
        try {
            context.startActivity(Intent.createChooser(emailIntent, "Send Email..."))
        } catch (e: ActivityNotFoundException) {

        }
    }

    private fun onClickLlamada(i: Int) {
        val inte = Intent(Intent.ACTION_CALL)
        inte.data = Uri.parse("tel:"+arrCase[i].lyTelephone)
        context.startActivity(inte)
    }

    private fun showContact(i: Int) {
        val layoutInflater = LayoutInflater.from(context)
        val promptView: View = layoutInflater.inflate(R.layout.activity_cd_pay_pal, null)
        val alertDialog: AlertDialog = AlertDialog.Builder(context).create()
        alertDialog.setView(promptView);
        val btnNegative: Button = promptView.findViewById<View>(R.id.btnGoToLawyers) as Button
        btnNegative.setOnClickListener {
        }
        alertDialog.show()
    }
}