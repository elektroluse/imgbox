package com.oivi.imgboxb.services

import com.oivi.imgboxb.domain.entities.ImgBoxEntity
import org.springframework.web.multipart.MultipartFile

interface ImgboxService {

    fun upload(imgBoxEntity: ImgBoxEntity, mf : MultipartFile) : ImgBoxEntity
    fun getImgBox(id: String): ImgBoxEntity
}