package com.oivi.imgboxb.controllers

import com.oivi.imgboxb.domain.dto.ImgboxFormDto
import com.oivi.imgboxb.domain.entities.ImgBoxEntity
import com.oivi.imgboxb.domain.entities.UserEntity
import com.oivi.imgboxb.repositories.UserRepository
import com.oivi.imgboxb.services.ImgboxService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
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
    val userRepository: UserRepository
) {

    @PostMapping(path = ["upload"])
    fun createImagebox(
        authentication : Authentication,
        @RequestPart("file") mf : MultipartFile,
        @RequestPart("imgboxdto") imgboxDto : ImgboxFormDto
        ) : ResponseEntity<String>{
        val principalUsername = authentication.name
        val userEntity : UserEntity = userRepository.findByUsername(principalUsername)
            ?: return ResponseEntity<String>("User does not exist" ,HttpStatus.BAD_REQUEST )

        val tempImgboxEntity  = ImgBoxEntity(null,imgboxDto.title,imgboxDto.description,imgboxDto.tags,"",userEntity,
            Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS)))

        val finishedImgBox = imageboxService.upload(tempImgboxEntity, mf);
        return ResponseEntity<String>("Imgbox with id : " + finishedImgBox.id, HttpStatus.CREATED )
    }

    @PostMapping(path = ["filetest"])
    fun test(@RequestPart("file") mf : MultipartFile) : ResponseEntity<String>{
        println(mf.name)
        return ResponseEntity<String>("we did it", HttpStatus.OK)
    }
}