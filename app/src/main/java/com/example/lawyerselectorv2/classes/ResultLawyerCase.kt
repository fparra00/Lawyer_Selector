package com.example.lawyerselectorv2.classes

import java.io.Serializable

/**
 * LEGAL CASE + DATES OF LAWYER:
 *
 */
class ResultLawyerCase(
//Internal Variables
    lyEmail: String,
    lyName: String,
    lyTelephone: String,
    lcTittle: String

) : Serializable {
    val lyEmail: String
    val lyName: String
    val lyTelephone: String
    val lcTittle: String

    //Constructor
    init {
        this.lyEmail = lyEmail
        this.lyName = lyName
        this.lyTelephone = lyTelephone
        this.lcTittle = lcTittle
    }

    //Serial ID
    companion object {
        const val serialVersionUID = 56763L
    }

    override fun toString(): String {
        return "ResultLawyerCase(lyEmail='$lyEmail', lyName='$lyName', lyTelephone='$lyTelephone', lcTittle='$lcTittle')"
    }


}
