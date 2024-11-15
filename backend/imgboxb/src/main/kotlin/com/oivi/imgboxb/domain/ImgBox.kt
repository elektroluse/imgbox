package com.oivi.imgboxb.domain

import java.sql.Timestamp

data class ImgBox(
    val id : Long?,
    val title : String,
    val description : String,
    var tags : MutableSet<String>,
    val user : User,
    val createdAt : Timestamp
    )
