package com.example.lawyerselectorv2.classes

import java.io.Serializable

class Lawyer(
    //Internal Variables
    id: Int,
    name: String,
    telephone: String,
    country: String,
    city: String,
    postalCode: String,
    addres: String,
    administrative:String,
    commercial:String,
    criminal:String,
    family:String,
    labor:String

) : Serializable {
    val id: Int
    val name: String
    val telephone: String
    val country: String
    val city: String
    val postalCode: String
    val addres: String
    val administrative: String
    val commercial: String
    val criminal:String
    val family:String
    val labor:String

    //Constructor
    init {
        this.id = id
        this.name = name
        this.telephone = telephone
        this.country = country
        this.city = city
        this.postalCode = postalCode
        this.addres = addres
        this.administrative = administrative
        this.commercial = commercial
        this.criminal = criminal
        this.family = family
        this.labor = labor
    }
    //Serial ID
    companion object {
        const val serialVersionUID = 56789L
    }
}
