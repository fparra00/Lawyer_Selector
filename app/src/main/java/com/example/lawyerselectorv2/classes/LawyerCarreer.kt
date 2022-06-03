package com.example.lawyerselectorv2.classes

import java.io.Serializable

class LawyerCarreer(
//Internal Variables
    description: String,
    gender: String,
    level_english: String,
    level_french: String,
    level_spanish: String,
    studie1: String,
    studie1_description: String,
    studie2: String,
    studie2_description: String,
    studie3: String,
    studie3_description: String,

    ) :Serializable {
    val description: String
    val gender: String
    val level_english: String
    val level_french: String
    val level_spanish: String
    val studie1: String
    val studie1_description: String
    val studie2: String
    val studie2_description: String
    val studie3: String
    val studie3_description: String
    //Constructor
    init {
        this.description = description
        this.gender = gender
        this.level_english = level_english
        this.level_french = level_french
        this.level_spanish = level_spanish
        this.studie1 = studie1
        this.studie1_description = studie1_description
        this.studie2 = studie2
        this.studie2_description = studie2_description
        this.studie3 = studie3
        this.studie3_description = studie3_description
    }
    //Serial ID
    companion object {
        const val serialVersionUID = 56789L
    }
}
