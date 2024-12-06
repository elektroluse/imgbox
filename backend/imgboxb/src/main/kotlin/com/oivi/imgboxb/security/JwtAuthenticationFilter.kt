package com.oivi.imgboxb.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.oivi.imgboxb.services.UserService
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter


class JwtAuthenticationFilter @Autowired constructor(
    private val tokenProvider : JwtTokenService,
    private val userService : UserService)
    : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain) {
        val token  = tokenFromRequest(request)
        try {
            if(StringUtils.hasText(token) && tokenProvider.validateToken(token!!)){
                val username : String = tokenProvider.usernameFromJwt(token)
                val userDetails = userService.loadUserByUsername(username)
                val authToken = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities)
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authToken

            }
        } catch (e: RuntimeException){
            handleFailedAuthentication(request,response,e)
            return
        }
        filterChain.doFilter(request,response)

    }

    private fun handleFailedAuthentication(
        request : HttpServletRequest, response : HttpServletResponse,failed : RuntimeException) {
        val responseEntity = ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body<String>("Error: " + failed.message)

        response.contentType = "application/json"
        response.status = responseEntity.statusCode.value()
        response.writer.write(ObjectMapper().writeValueAsString(responseEntity.body))
    }

    private fun tokenFromRequest(request : HttpServletRequest) : String?{
        val bearerToken = request.getHeader("Authorization")
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length)
        }
        return null
    }
}