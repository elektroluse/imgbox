package com.oivi.imgboxb.services

import com.oivi.imgboxb.domain.dto.ImgboxWithFileDto
import com.oivi.imgboxb.domain.entities.ImgBoxEntity
import org.springframework.web.multipart.MultipartFile

interface ImgboxService {

    fun upload(imgBoxEntity: ImgBoxEntity, mf : MultipartFile) : ImgBoxEntity
    fun getImgBox(id: Long): ImgBoxEntity
    fun getImgboxesByUsername(username: String): List<ImgBoxEntity>
    fun packFileWithBox(imgboxes: List<ImgBoxEntity>): List<ImgboxWithFileDto>
}