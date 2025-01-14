package com.oivi.imgboxb.domain.dto

data class LoginResponse(
    val message : String,
    val authenticated : Boolean,
    val token : String,
    val tokenType : String = " Bearer"

)
