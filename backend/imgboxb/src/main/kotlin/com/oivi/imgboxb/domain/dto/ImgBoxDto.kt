package com.oivi.imgboxb.domain.dto

import com.oivi.imgboxb.domain.entities.UserEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.sql.Timestamp

data class ImgBoxDto (

    val id : Long?,
    val title : String,
    val description : String,
    var tags : MutableSet<String>,
    var filename : String,
    val user : UserDto,
    val createdAt : Timestamp
)