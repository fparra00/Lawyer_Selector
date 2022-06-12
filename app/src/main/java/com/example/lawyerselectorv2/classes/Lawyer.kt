package com.example.lawyerselectorv2.classes

import java.io.Serializable

/**
 * LAWYER:
 */
class Lawyer(
    //Internal Variables
    email: String,
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
    labor:String,
    careerDetails:LawyerCarreer,


    ) : Serializable {
    val email:String
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
    val careerDetails:LawyerCarreer
    //Constructor
    init {
        this.email = email
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
        this.careerDetails = careerDetails
    }
    //Serial ID
    companion object {
        const val serialVersionUID = 56789L
    }

    override fun toString(): String {
        return "Lawyer(id=$id, name='$name', telephone='$telephone', country='$country', city='$city', postalCode='$postalCode', addres='$addres', administrative='$administrative', commercial='$commercial', criminal='$criminal', family='$family', labor='$labor', careerDetails=$careerDetails)"
    }
}
