package com.oivi.imgboxb.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class FilterExceptionHandler {

    @ExceptionHandler(AuthenticationCredentialsNotFoundException::class)
    fun handleExpiredTokenException(
        e : AuthenticationCredentialsNotFoundException) : ResponseEntity<String>{

        return ResponseEntity<String>("Token is invalid or expired", HttpStatus.UNAUTHORIZED)
    }
}