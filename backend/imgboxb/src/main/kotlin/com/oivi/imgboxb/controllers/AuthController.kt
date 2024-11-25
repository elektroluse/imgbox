package com.oivi.imgboxb.controllers

import com.oivi.imgboxb.domain.dto.RegistrationForm
import com.oivi.imgboxb.domain.dto.UserDto
import com.oivi.imgboxb.domain.entities.RoleEntity
import com.oivi.imgboxb.domain.entities.UserEntity
import com.oivi.imgboxb.repositories.RoleRepository
import com.oivi.imgboxb.repositories.UserRepository
import com.oivi.imgboxb.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
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
    val passwordEncoder : PasswordEncoder)
{
    @PostMapping(path = ["register"])
    fun registerUser(@RequestBody regDto : RegistrationForm) : ResponseEntity<String>{
        if (userRepository.existsByUsername(regDto.username)) {

            return ResponseEntity<String>("Username already exists!",HttpStatus.BAD_REQUEST)
        }
        val newUserEntity : UserEntity = UserEntity(
            id = null,
            username = regDto.username,
            password = passwordEncoder.encode(regDto.password),
            roles = mutableSetOf(roleRepository.findByName("USER")!!)
        )
        /*
            refactor this into the service layer

         */
        return ResponseEntity<String>("fix me",HttpStatus.BAD_REQUEST)
    }


}