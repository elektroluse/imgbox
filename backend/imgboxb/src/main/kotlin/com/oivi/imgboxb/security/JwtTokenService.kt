package com.oivi.imgboxb.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.Date
import javax.crypto.SecretKey


@Service
class JwtTokenService(
) {

    fun signingKey() : SecretKey {
        val keyBytes = Decoders.BASE64.decode(SecurityConstants.JWT_SECRET)
        return Keys.hmacShaKeyFor(keyBytes)
    }



    fun generateToken(authentication : Authentication) : String{
        val username  = authentication.name
        val expiryDate = Date(Date().time + SecurityConstants.JWT_EXPIRATION)
        val token  = Jwts.builder()
            .signWith(signingKey())
            .subject(username)
            .issuedAt(Date())
            .expiration(expiryDate)
            .compact()
        return token
    }

    fun validateToken(token : String) : Boolean{
        try {
            Jwts.parser().verifyWith(signingKey()).build().parseSignedClaims(token)
        } catch (e: Exception){
            throw AuthenticationCredentialsNotFoundException("Token expired or invalid")
        }
        return true
    }

    fun usernameFromJwt(token : String) : String{
        val claims = extractAllClaims(token)
        return claims.subject
    }

    fun extractAllClaims(token : String) : Claims{
        return Jwts.parser()
            .verifyWith(signingKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }


}