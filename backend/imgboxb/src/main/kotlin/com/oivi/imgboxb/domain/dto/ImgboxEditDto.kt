package com.oivi.imgboxb.domain.dto

data class ImgboxEditDto(
    val id : Long?,
    val title : String,
    val description : String,
    var tags : MutableSet<String>
)