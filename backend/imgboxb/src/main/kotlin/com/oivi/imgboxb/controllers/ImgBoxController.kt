package com.oivi.imgboxb.controllers

import com.oivi.imgboxb.domain.dto.ErrorDto
import com.oivi.imgboxb.domain.dto.ImgBoxDto
import com.oivi.imgboxb.domain.dto.ImgboxFormDto
import com.oivi.imgboxb.domain.entities.ImgBoxEntity
import com.oivi.imgboxb.domain.entities.UserEntity
import com.oivi.imgboxb.exceptions.ImageUploadException
import com.oivi.imgboxb.repositories.UserRepository
import com.oivi.imgboxb.services.ImgboxService
import com.oivi.imgboxb.services.UserService
import com.oivi.imgboxb.toImgBoxDto
import com.oivi.imgboxb.toImgBoxDtoSafe
import com.oivi.imgboxb.toImgBoxEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@RestController
@RequestMapping(path = ["/api/imgbox"])
class ImgBoxController(
    val imageboxService : ImgboxService,
    val userService: UserService
) {

    @PostMapping(path = ["upload"])
    fun createImgBox(
        authentication : Authentication,
        @RequestPart("file") mf : MultipartFile,
        @RequestPart("imgboxdto") imgboxDto : ImgboxFormDto
    ): ResponseEntity<String>
    {
        val principalUsername = authentication.name

        try {
            val userEntity : UserEntity = userService.getUser(principalUsername)
            val createdImgBoxEntity = imageboxService.upload(imgboxDto.toImgBoxEntity(userEntity), mf)
            return ResponseEntity<String>("Imgbox with id : " + createdImgBoxEntity.id, HttpStatus.CREATED )

        } catch (e : Exception){
            return when(e){
                is UsernameNotFoundException -> ResponseEntity<String>(e.message, HttpStatus.BAD_REQUEST)
                is ImageUploadException -> ResponseEntity<String>(e.message, HttpStatus.BAD_REQUEST)
                else -> ResponseEntity<String>("Unexpected exception", HttpStatus.INTERNAL_SERVER_ERROR)
            }
        }
    }

    @GetMapping(path = ["{username}"])
    fun getImgBoxesByUsername(@PathVariable("username") username : String) : ResponseEntity<List<ImgBoxDto>>{
        try {
            val result = imageboxService.getImgboxesByUsername(username)
            return ResponseEntity(result.map { it.toImgBoxDtoSafe() }, HttpStatus.OK)

        } catch (e : Exception){
            return when(e){
                is UsernameNotFoundException -> ResponseEntity(HttpStatus.NOT_FOUND)
                else -> ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
            }
        }

    }
}