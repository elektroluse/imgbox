package com.oivi.imgboxb.controllers

import com.oivi.imgboxb.domain.dto.AuthResponseDto
import com.oivi.imgboxb.domain.dto.LoginDto
import com.oivi.imgboxb.domain.dto.RegistrationForm
import com.oivi.imgboxb.repositories.RoleRepository
import com.oivi.imgboxb.repositories.UserRepository
import com.oivi.imgboxb.security.JwtTokenService
import com.oivi.imgboxb.services.UserService
import com.oivi.imgboxb.toUserEntity
import org.springframework.data.repository.findByIdOrNull
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
    val userRepository : UserRepository,
    val roleRepository : RoleRepository,
    val userService : UserService,
    val passwordEncoder : PasswordEncoder,
    val jwtTokenService : JwtTokenService)
{

    @PostMapping(path = ["login"])
    fun login(@RequestBody loginDto : LoginDto) : ResponseEntity<AuthResponseDto>{

        val authentication : Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
            loginDto.username,
            loginDto.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val token = jwtTokenService.generateToken(authentication)
        return ResponseEntity(AuthResponseDto(token),HttpStatus.OK)
    }

    @PostMapping(path = ["register"])
    fun registerUser(@RequestBody regDto : RegistrationForm) : ResponseEntity<String>{
        if (userRepository.existsByUsername(regDto.username)) {

            return ResponseEntity<String>("Username already exists!",HttpStatus.BAD_REQUEST)
        }

        val userRoleEntity = roleRepository.findByIdOrNull(2)
            ?: return ResponseEntity<String>("There is no role with id 2",HttpStatus.INTERNAL_SERVER_ERROR)

        val savedUser = userService.save(
            regDto.toUserEntity(
                passwordEncoder.encode(regDto.password),
                 mutableSetOf(userRoleEntity)
            ))

        val message = "Created user with name : " +
                savedUser.username + "(" + savedUser.id +")"

        return ResponseEntity<String>(message ,HttpStatus.CREATED)
    }


}