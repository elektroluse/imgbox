package com.oivi.imgboxb.services

import org.springframework.web.multipart.MultipartFile
import java.io.InputStream

interface ImageStorageService {
    fun uploadImage(username : String, private : Boolean, f : MultipartFile) : String
    fun generateUniqueName(f : MultipartFile) : String
    fun getInputStream(fileUrl: String): InputStream
    fun getInputStreamFromKey(objKey: String): InputStream
}