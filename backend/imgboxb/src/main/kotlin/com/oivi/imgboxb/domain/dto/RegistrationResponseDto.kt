package com.oivi.imgboxb.domain.dto

data class RegistrationResponseDto(
    val username : String,
    val message : String,
    val completed : Boolean = false
)