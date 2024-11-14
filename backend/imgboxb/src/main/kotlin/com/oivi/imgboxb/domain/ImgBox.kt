package com.oivi.imgboxb.domain

data class ImgBox(
    val id : Long?,
    val title : String,
    val description : String,
    var tags : MutableSet<String>,
    val user : User
    )
