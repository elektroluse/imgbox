package com.oivi.imgboxb.controllers

import com.oivi.imgboxb.domain.dto.ImgBoxDto
import com.oivi.imgboxb.domain.dto.ImgboxFormDto
import com.oivi.imgboxb.domain.entities.ImgBoxEntity
import com.oivi.imgboxb.domain.entities.UserEntity
import com.oivi.imgboxb.exceptions.ImageUploadException
import com.oivi.imgboxb.repositories.UserRepository
import com.oivi.imgboxb.services.ImgboxService
import com.oivi.imgboxb.services.UserService
import com.oivi.imgboxb.toImgBoxEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.*
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

    @GetMapping(path = ["{imgboxid}"])
    fun getImgboxWithId(@PathVariable("imgboxid") imgboxid : String) : ResponseEntity<ImgBoxDto>{

        val imgBox : ImgBoxDto = imageboxService.getImagebox().toImgBoxDto()
        return ResponseEntity<ImgBoxDto>(imgBox, HttpStatus.OK)
    }
    @PostMapping(path = ["filetest"])
    fun test(@RequestPart("file") mf : MultipartFile) : ResponseEntity<String>{
        println(mf.name)
        return ResponseEntity<String>("we did it", HttpStatus.OK)
    }
}