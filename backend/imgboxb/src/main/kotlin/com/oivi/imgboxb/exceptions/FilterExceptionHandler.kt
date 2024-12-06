package com.oivi.imgboxb.exceptions

import com.oivi.imgboxb.domain.dto.ErrorDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@RestControllerAdvice
class FilterExceptionHandler {

    @ExceptionHandler(AuthenticationCredentialsNotFoundException::class)
    fun handleExpiredTokenException(
        e : AuthenticationCredentialsNotFoundException) : ResponseEntity<ErrorDto>{
        val errorDto = ErrorDto("Token is invalid or expired", Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)))
        return ResponseEntity<ErrorDto>(errorDto, HttpStatus.UNAUTHORIZED)
    }
}