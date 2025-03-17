package com.oivi.imgboxb.security

import com.oivi.imgboxb.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
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
import org.springframework.security.web.util.matcher.RequestMatchers
import org.springframework.util.AntPathMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsUtils
import org.springframework.web.cors.CorsUtils.isPreFlightRequest
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
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
                
                authorize(CorsUtils::isPreFlightRequest,permitAll)
                authorize(AntPathRequestMatcher("/api/auth/register"), permitAll)
                authorize(AntPathRequestMatcher("/api/auth/login"), permitAll)
                authorize(AntPathRequestMatcher("/api/v1/users"), permitAll)
                authorize(AntPathRequestMatcher("/api/users"), permitAll)


                authorize(AntPathRequestMatcher("/api/secure/testuser"),  hasAuthority("USER"))
                authorize(AntPathRequestMatcher("/api/secure/testadmin"),  hasAuthority("ADMIN"))
                authorize(anyRequest, authenticated)

            }


            csrf { disable() }
            cors { configurationSource = corsConfigurationSource() }
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
    fun corsConfigurationSource() : UrlBasedCorsConfigurationSource{
        val config = CorsConfiguration()
        config.allowedOrigins = listOf("http://localhost:5173","https://localhost:5173")
        config.allowedMethods = listOf("GET","POST","PUT","DELETE","OPTIONS")
        config.allowedHeaders = listOf("authorization", "content-type")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**",config)
        return source
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