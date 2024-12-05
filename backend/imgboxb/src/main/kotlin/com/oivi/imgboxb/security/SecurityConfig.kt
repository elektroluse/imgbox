package com.oivi.imgboxb.security

import com.oivi.imgboxb.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.servlet.HandlerExceptionResolver

@Configuration
@EnableWebSecurity
class SecurityConfig @Autowired constructor(
    private val userService: UserService,
    private val authEntryPoint : JwtAuthEntryPoint,
    private val jwtTokenService: JwtTokenService,
    @Qualifier("handlerExceptionResolver")
    private val resolver : HandlerExceptionResolver) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {

            authorizeHttpRequests {
                authorize(AntPathRequestMatcher("/api/secure/testuser"),  hasAuthority("USER"))
                authorize(AntPathRequestMatcher("/api/secure/testadmin"),  hasAuthority("ADMIN"))
                authorize(anyRequest, permitAll)

            }

            csrf { disable() }
            exceptionHandling {
                    authenticationEntryPoint = authEntryPoint
            }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }

            formLogin { }
            httpBasic { }

            addFilterBefore<UsernamePasswordAuthenticationFilter>(filter = jwtAuthenticationFilter())
        }
        return http.build()
    }

    @Bean
    fun authenticationManager(
        authConfig : AuthenticationConfiguration) : AuthenticationManager{
         return authConfig.authenticationManager
    }

    @Bean
    fun passwordEncoder() : PasswordEncoder{
        return BCryptPasswordEncoder()
    }
    @Bean
    fun jwtAuthenticationFilter() : JwtAuthenticationFilter{
        return JwtAuthenticationFilter(jwtTokenService,userService, resolver)
    }

}