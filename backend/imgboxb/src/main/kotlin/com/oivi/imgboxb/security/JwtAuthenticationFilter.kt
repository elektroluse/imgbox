package com.oivi.imgboxb.security

import com.oivi.imgboxb.services.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter @Autowired constructor(
    private val tokenProvider : JwtTokenProvider,
    val userService : UserService)
    : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain) {
        val token  = tokenFromRequest(request)
        if(StringUtils.hasText(token) && tokenProvider.validateToken(token!!)){
            val username : String = tokenProvider.usernameFromJwt(token)
            val userDetails = userService.loadUserByUsername(username)
            val authToken : UsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.authorities)
            authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authToken
        }
        filterChain.doFilter(request,response)
    }

    private fun tokenFromRequest(request : HttpServletRequest) : String?{
        val bearerToken = request.getHeader("Authorization")
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length)
        }
        return null
    }
}