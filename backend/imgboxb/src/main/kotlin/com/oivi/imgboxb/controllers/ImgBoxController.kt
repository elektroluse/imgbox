package com.oivi.imgboxb.controllers

import com.oivi.imgboxb.domain.dto.ErrorDto
import com.oivi.imgboxb.domain.dto.ImgBoxDto
import com.oivi.imgboxb.domain.dto.ImgboxFormDto
import com.oivi.imgboxb.domain.dto.ImgboxWithFileDto
import com.oivi.imgboxb.domain.entities.ImgBoxEntity
import com.oivi.imgboxb.domain.entities.UserEntity
import com.oivi.imgboxb.exceptions.ImageUploadException
import com.oivi.imgboxb.repositories.UserRepository
import com.oivi.imgboxb.services.ImgboxService
import com.oivi.imgboxb.services.UserService
import com.oivi.imgboxb.toImgBoxDto
import com.oivi.imgboxb.toImgBoxDtoSafe
import com.oivi.imgboxb.toImgBoxEntity
import org.apache.commons.io.IOUtils
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
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
import java.io.InputStream
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
    @GetMapping(path = ["{username}/download"])
    fun downloadImgBoxesByUsername(
        @PathVariable("username") username : String) : ResponseEntity<List<ImgboxWithFileDto>>{
            val result = imageboxService.packFileWithBox(
                imageboxService.getImgboxesByUsername(username)
            )
        return ResponseEntity(result, HttpStatus.OK)

    }


    @GetMapping(path = ["storage/{objectkey}"])
    fun downloadImage(@PathVariable("objectkey") objKey : String) : ResponseEntity<ByteArrayResource> {

        val inputStream = imageboxService.downloadFile(objKey)
        val byteArrayResource = ByteArrayResource(IOUtils.toByteArray(inputStream))
        inputStream.close()
        val headers = HttpHeaders()
        headers.contentType = MediaType.IMAGE_PNG

        return ResponseEntity.ok()
            .headers(headers)
            .body(byteArrayResource)

    }

    @GetMapping(path = ["alternate/{objectkey}"])
    fun downloadImageAlt(@PathVariable("objectkey") objKey : String) : ResponseEntity<InputStreamResource> {

        /*
            NOTE :

            Not sure whether the inputstream is closed appropriately,
            but it seems to be the case
            ref : https://stackoverflow.com/questions/48660011/how-to-handle-io-streams-in-spring-mvc/48660203#48660203

            Not sure what the best way to return image data to client is currently, but for now
            either bytearrayresource or inputstreamresource seems fine for files that are intended to be used in the
            application

         */

        val inputStream = imageboxService.downloadFile(objKey)
        val headers = HttpHeaders()
        headers.contentType = MediaType.IMAGE_PNG

        return ResponseEntity.ok()
            .headers(headers)
            .body(InputStreamResource(inputStream))



    }
}