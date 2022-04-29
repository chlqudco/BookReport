package com.chlqudco.develop.bookreport.model

import android.net.Uri

data class Book(
    val title : String,
    val date : Long,
    val starRate: Int,
    val description: String,
    val bookImage : Uri
)