package com.oivi.imgboxb.controllers

import com.oivi.imgboxb.services.TagService
import com.oivi.imgboxb.toStringOnly
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/tags"])
class TagController(
    private val tagService: TagService) {

    @GetMapping(path = ["list"])
    fun listAllTags() : ResponseEntity<List<String>> {
        val result = tagService.list().map { it.toStringOnly() }
        return ResponseEntity(result, HttpStatus.OK)
    }
}