package com.oivi.imgboxb.controllers

import com.oivi.imgboxb.domain.dto.UserDto
import com.oivi.imgboxb.services.UserService
import com.oivi.imgboxb.toUserDto
import com.oivi.imgboxb.toUserEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userService : UserService) {

    @PostMapping(path = ["v1/users"])
    fun createUser(@RequestBody userDto : UserDto) : UserDto{
        return userService.save(userDto.toUserEntity())
            .toUserDto()

    }
}