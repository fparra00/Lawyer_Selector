package com.example.lawyerselectorv2.classes

import java.io.Serializable

/**
 * LEGAL CASE:
 *
 */
class LegalCase(
//Internal Variables
    tittle: String,
    description: String,
    date: String,
    hour: String,
    outsidePeople:String,
    dscOutsidePeople:String

) : Serializable {
    val tittle: String
    val description: String
    val date: String
    val hour: String
    val outsidePeople: String
    val dscOutsidePeople:String

    //Constructor
    init {
        this.tittle = tittle
        this.description = description
        this.date = date
        this.hour = hour
        this.outsidePeople = outsidePeople
        this.dscOutsidePeople = dscOutsidePeople
    }

    //Serial ID
    companion object {
        const val serialVersionUID = 56769L
    }

    override fun toString(): String {
        return "LegalCase(tittle='$tittle', description='$description', date='$date', hour='$hour', outsidePeople='$outsidePeople', dscOutsidePeople='$dscOutsidePeople')"
    }
}
