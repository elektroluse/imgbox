package com.oivi.imgboxb.security

import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class JwtTokenProvider {

    fun generateToken(authentication : Authentication) : String{
        val name  = authentication.name
        // current and expiration date
        return ""
    }
}