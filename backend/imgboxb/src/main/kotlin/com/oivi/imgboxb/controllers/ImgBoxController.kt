package com.oivi.imgboxb.controllers

import com.oivi.imgboxb.*
import com.oivi.imgboxb.domain.dto.*
import com.oivi.imgboxb.domain.entities.ImgBoxEntity
import com.oivi.imgboxb.domain.entities.UserEntity
import com.oivi.imgboxb.exceptions.ImageDeleteException
import com.oivi.imgboxb.exceptions.ImageUploadException
import com.oivi.imgboxb.exceptions.InvalidRequestParameterException
import com.oivi.imgboxb.repositories.UserRepository
import com.oivi.imgboxb.services.ImgboxService
import com.oivi.imgboxb.services.TagService
import com.oivi.imgboxb.services.UserService
import io.minio.messages.Upload
import okhttp3.Response
import org.apache.commons.io.IOUtils
import org.simpleframework.xml.Path
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.InputStreamResource
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@RestController
@RequestMapping(path = ["/api/imgbox"])
class ImgBoxController(
    val imageboxService : ImgboxService,
    val userService: UserService,
    val tagService: TagService
) {

    @PostMapping(path = ["upload"])
    fun createImgBox(
        authentication : Authentication,
        @RequestPart("file") mf : MultipartFile,
        @RequestPart("imgboxdto") imgboxDto : ImgboxFormDto
    ): ResponseEntity<UploadResponseDto>
    {
        val principalUsername = authentication.name

        try {
            val userEntity : UserEntity = userService.getUser(principalUsername)
            val createdImgBoxEntity = imageboxService.upload(imgboxDto.toImgBoxEntity(userEntity), mf)
            val responseDto = UploadResponseDto("Imgbox with id : " + createdImgBoxEntity.id);
            return ResponseEntity<UploadResponseDto>(responseDto, HttpStatus.CREATED )

        } catch (e : Exception){
            return when(e){
                is UsernameNotFoundException -> ResponseEntity<UploadResponseDto>(
                    UploadResponseDto(e.message), HttpStatus.BAD_REQUEST)
                is ImageUploadException -> ResponseEntity<UploadResponseDto>(
                    UploadResponseDto(e.message), HttpStatus.BAD_REQUEST)
                else -> ResponseEntity<UploadResponseDto>(
                    UploadResponseDto("Unexpected exception"), HttpStatus.INTERNAL_SERVER_ERROR)
            }
        }
    }
    @GetMapping(path = ["id/{id}"])
    fun getImgboxById(@PathVariable("id") id : Long) : ResponseEntity<ImgBoxDto>{

        try {
            val result = imageboxService.getImgBox(id)
            return ResponseEntity(result.toImgBoxDtoKeyForm(),HttpStatus.OK)
        } catch (e : Exception){
            return when (e){
                    is NoSuchElementException -> ResponseEntity(HttpStatus.NOT_FOUND)
                    else -> ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
            }
        }
    }

    @PutMapping(path = ["id/{id}"])
    fun updateImgbox(
        authentication: Authentication,
        @PathVariable("id") id : Long,
        @RequestBody data: ImgboxEditDto) : ResponseEntity<ImgBoxDto>{
        try {

            val result = imageboxService.update(id, data, authentication.name)
            return ResponseEntity(result.toImgBoxDtoKeyForm(), HttpStatus.OK)

        }catch (e : Exception){
            return when (e){
                is NoSuchElementException -> ResponseEntity(HttpStatus.NOT_FOUND)
                is IllegalStateException -> ResponseEntity(HttpStatus.FORBIDDEN)
                else -> ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
            }
        }
    }
    @DeleteMapping(path = ["id/{id}"])
    fun deleteImgbox(
        authentication: Authentication,
        @PathVariable("id") id : Long) : ResponseEntity<String>{

        try {
            imageboxService.delete(id, authentication.name)
            return ResponseEntity<String>("Deleted successfully", HttpStatus.OK)

        }catch (e : Exception){
            return when (e){
                is NoSuchElementException -> ResponseEntity(HttpStatus.NOT_FOUND)
                is ImageDeleteException -> ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
                is IllegalStateException -> ResponseEntity(HttpStatus.FORBIDDEN)
                else -> ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
            }

        }
    }

    @GetMapping(path = ["username/{username}"])
    fun getImgBoxesByUsername(
        @PathVariable("username") username : String,
        pageable : Pageable) : ResponseEntity<Page<ImgBoxDto>>{
        try {
            val result = imageboxService.getImgboxesByUsername(pageable,username)
            return ResponseEntity(result.map { it.toImgBoxDtoKeyForm() }, HttpStatus.OK)

        } catch (e : Exception){
            return when(e){
                is UsernameNotFoundException -> ResponseEntity(HttpStatus.NOT_FOUND)
                else -> ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
            }
        }

    }
    @GetMapping(path = ["username/{username}/download"])
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
    @GetMapping(path = ["search/title/{searchTerm}"])
    fun searchForImgboxesByTitle(
        @PathVariable("searchTerm") searchTerm : String) : ResponseEntity<List<ImgBoxDto>>{
        val result = imageboxService.getImgboxByTitleSearch(searchTerm);
        return ResponseEntity(
            result.map { it.toImgBoxDtoKeyForm() },
            HttpStatus.OK)
    }

    @GetMapping(path = ["search/{searchTerm}"])
    fun searchForImgboxesByRequestParam(
        @PathVariable("searchTerm") searchTerm : String,
        @RequestParam(name = "searchParam", defaultValue = "title") searchParam : String,
        pageable : Pageable
    ): ResponseEntity<Page<ImgBoxDto>>{
        try{
            val result = imageboxService.getImgboxPageBySearch(searchTerm,searchParam,pageable);
            return ResponseEntity(
                result.map { it.toImgBoxDtoKeyForm() },
                HttpStatus.OK)

        }catch (e : Exception){
            return when(e) {
                is InvalidRequestParameterException -> ResponseEntity(HttpStatus.BAD_REQUEST)
                is UsernameNotFoundException -> ResponseEntity(HttpStatus.NOT_FOUND)
                else -> ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
            }
        }


    }

    @GetMapping(path = ["tag/{tag}"])
    fun getImgboxesByTag(
        @PathVariable("tag") tag : String,
        pageable: Pageable) : ResponseEntity<Page<ImgBoxDto>>{

        return ResponseEntity(
            imageboxService
                .pageImgboxesWithTag(tag, pageable).map { it.toImgBoxDtoKeyForm() },
            HttpStatus.OK)
    }
}