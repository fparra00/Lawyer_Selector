package com.example.lawyerselectorv2.classes

import java.io.Serializable

/**
USER:
Class used to instantiate ONLY PERSONAL DETAILS of a normal user (client) or a Lawyer.
Personal details like Name, city...
The Class is serializable, to pass with a bundle
 */
class User(
    //Internal Variables
    id: Int,
    name: String,
    telephone: String,
    country: String,
    city: String,
    postalCode: String,
    addres: String
) : Serializable {
    val id: Int
    val name: String
    val telephone: String
    val country: String
    val city: String
    val postalCode: String
    val addres: String

    //Constructor
    init {
        this.id = id
        this.name = name
        this.telephone = telephone
        this.country = country
        this.city = city
        this.postalCode = postalCode
        this.addres = addres
    }

    //Serial ID
    companion object {
        const val serialVersionUID = 56789L
    }
}
