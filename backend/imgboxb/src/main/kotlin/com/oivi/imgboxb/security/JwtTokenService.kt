package com.oivi.imgboxb.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
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
        } catch (e: RuntimeException){ throw AuthenticationCredentialsNotFoundException("Token is invalid or expired") }
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