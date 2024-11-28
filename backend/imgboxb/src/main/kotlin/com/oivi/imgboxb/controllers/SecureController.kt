package com.oivi.imgboxb.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/secure"])
class SecureController {

    @GetMapping(path = ["testadmin"])
    fun testAdminAuth() : ResponseEntity<String>{
        return ResponseEntity("Hello admin", HttpStatus.OK)
    }
    @GetMapping(path = ["testuser"])
    fun testUserAuth() : ResponseEntity<String>{
        return ResponseEntity("Hello users", HttpStatus.OK)
    }

}