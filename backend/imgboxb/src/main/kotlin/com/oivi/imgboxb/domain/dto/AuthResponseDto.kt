package com.oivi.imgboxb.domain.dto

data class AuthResponseDto(
    val accessToken : String,
    val tokenType : String = " Bearer"
)
