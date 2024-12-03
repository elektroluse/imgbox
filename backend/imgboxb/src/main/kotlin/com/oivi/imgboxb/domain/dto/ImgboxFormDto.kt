package com.oivi.imgboxb.domain.dto

import com.oivi.imgboxb.domain.entities.UserEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

data class ImgboxFormDto(

    val title : String,
    val description : String,
    var tags : MutableSet<String>
)