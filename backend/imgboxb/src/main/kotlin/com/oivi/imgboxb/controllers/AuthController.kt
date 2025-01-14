package com.oivi.imgboxb.controllers

import com.oivi.imgboxb.domain.dto.*
import com.oivi.imgboxb.exceptions.RoleRepositoryException
import com.oivi.imgboxb.repositories.RoleRepository
import com.oivi.imgboxb.repositories.UserRepository
import com.oivi.imgboxb.security.JwtTokenService
import com.oivi.imgboxb.services.ImageStorageService
import com.oivi.imgboxb.services.UserService
import com.oivi.imgboxb.toUserEntity
import jakarta.servlet.Registration
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/auth"])
class AuthController(
    val authenticationManager : AuthenticationManager,
    val userService : UserService,
    val passwordEncoder : PasswordEncoder,
    val jwtTokenService : JwtTokenService)
{

    @PostMapping(path = ["login"])
    fun login(@RequestBody loginDto : LoginDto) : ResponseEntity<LoginResponse>{

        val authentication : Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
            loginDto.username,
            loginDto.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val token = jwtTokenService.generateToken(authentication)
        val loginResponse = LoginResponse("Login successful !",true, token);
        return ResponseEntity(loginResponse,HttpStatus.OK)
    }

    @PostMapping(path = ["register"])
    fun registerUser(@RequestBody regDto : RegistrationForm) : ResponseEntity<RegistrationResponseDto>{
        try {
            val savedUser = userService.create(
                regDto.toUserEntity(passwordEncoder.encode(regDto.password)))

            val message = "Created user with name : " + savedUser.username + " (" + savedUser.id +")"

            return ResponseEntity<RegistrationResponseDto>(
                RegistrationResponseDto(savedUser.username,message, true), HttpStatus.CREATED)

        } catch (e : Exception){

            return when(e){
                is IllegalStateException -> ResponseEntity<RegistrationResponseDto>(
                    RegistrationResponseDto(regDto.username,"Username is already in use"),
                    HttpStatus.BAD_REQUEST)
                is RoleRepositoryException -> ResponseEntity<RegistrationResponseDto>(
                    RegistrationResponseDto(regDto.username,e.message!!),
                    HttpStatus.INTERNAL_SERVER_ERROR)
                else -> ResponseEntity<RegistrationResponseDto>(
                    RegistrationResponseDto(regDto.username,"Unexpected exception"),
                    HttpStatus.INTERNAL_SERVER_ERROR)
            }
        }
    }


}