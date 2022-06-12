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
    outsidePeople: String,
    dscOutsidePeople: String,
    user: String,
    status: String,
    lawyer: String


) : Serializable {
    val tittle: String
    val description: String
    val date: String
    val hour: String
    val outsidePeople: String
    val dscOutsidePeople: String
    val user: String
    val status: String
    val lawyer: String

    //Constructor
    init {
        this.tittle = tittle
        this.description = description
        this.date = date
        this.hour = hour
        this.outsidePeople = outsidePeople
        this.dscOutsidePeople = dscOutsidePeople
        this.user = user
        this.status = status
        this.lawyer = lawyer
    }

    //Serial ID
    companion object {
        const val serialVersionUID = 56769L
    }

    override fun toString(): String {
        return "LegalCase(tittle='$tittle', description='$description', date='$date', hour='$hour', outsidePeople='$outsidePeople', dscOutsidePeople='$dscOutsidePeople', user='$user', status='$status', lawyer='$lawyer')"
    }


}
