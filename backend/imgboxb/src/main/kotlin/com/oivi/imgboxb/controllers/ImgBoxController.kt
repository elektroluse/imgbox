package com.oivi.imgboxb.controllers

import com.oivi.imgboxb.domain.dto.ImgboxFormDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping(path = ["/api/imgbox"])
class ImgBoxController {

    @PostMapping(path = ["upload"])
    fun createImagebox(@RequestBody imgboxDto : ImgboxFormDto, @RequestPart mf : MultipartFile){
        //imageboxService.post(imgboxEntity, mf);

    }
}