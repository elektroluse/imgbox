package com.oivi.imgboxb.controllers

import com.oivi.imgboxb.domain.dto.UserDto
import com.oivi.imgboxb.domain.dto.UserProfile
import com.oivi.imgboxb.services.UserService
import com.oivi.imgboxb.toUserDto
import com.oivi.imgboxb.toUserEntity
import com.oivi.imgboxb.toUserProfile
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userService : UserService) {


    @GetMapping(path = ["/api/users"])
    fun listUsers(pageable: Pageable) : ResponseEntity<Page<UserProfile>>{
        val result = userService.list(pageable).map {
            it.toUserProfile()
        }
        return ResponseEntity(result, HttpStatus.OK)
    }

    @GetMapping(path = ["/api/v1/users"])
    fun readManyUser() : ResponseEntity<List<UserProfile>>{
          val result : List<UserProfile> =  userService.list().map{
             it.toUserProfile()
         }
        return ResponseEntity(result, HttpStatus.OK);
    }
    @GetMapping(path = ["/api/v1/me"])
    fun readCurrentUser(authentication : Authentication) : ResponseEntity<UserProfile>{
        val loggedInUser = userService.getUser(authentication.name)
        return ResponseEntity(loggedInUser.toUserProfile(),HttpStatus.OK)
    }
}