package com.example.lawyerselectorv2.classes

class User(id:Int, name:String, telephone:String, country:String, city:String, postalCode:String, addres:String) {
    val id:Int
    val name:String
    val telephone:String
    val country:String
    val city:String
    val postalCode:String
    val addres:String
    init {
        this.id = id
        this.name = name
        this.telephone = telephone
        this.country = country
        this.city = city
        this.postalCode = postalCode
        this.addres = addres
    }
}
