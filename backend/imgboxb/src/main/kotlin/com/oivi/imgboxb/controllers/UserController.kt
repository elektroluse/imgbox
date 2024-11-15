package com.oivi.imgboxb.controllers

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    @PostMapping(path = ["v1/users"])
    fun createUser(){
        
    }
}