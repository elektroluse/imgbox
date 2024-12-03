package com.oivi.imgboxb.services

import org.springframework.web.multipart.MultipartFile

interface ImageStorageService {
    fun uploadImage(username : String, private : Boolean, f : MultipartFile) : String
    fun generateUniqueName(f : MultipartFile) : String
}