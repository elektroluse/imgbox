package com.oivi.imgboxb.services.impl

import com.oivi.imgboxb.domain.entities.ImgBoxEntity
import com.oivi.imgboxb.exceptions.ImageUploadException
import com.oivi.imgboxb.repositories.ImgBoxRepository
import com.oivi.imgboxb.services.ImageStorageService
import com.oivi.imgboxb.services.ImgboxService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class ImgboxServiceImpl(
    private val imageStorageService : ImageStorageService,
    private val imgBoxRepository: ImgBoxRepository
)  : ImgboxService {

    @Transactional
    override fun upload(imgBoxEntity: ImgBoxEntity, mf : MultipartFile): ImgBoxEntity {
        if(mf.isEmpty){
            throw ImageUploadException("File is empty")
        }
        val fileUrl : String = imageStorageService.uploadImage(imgBoxEntity.user.username,false,mf)
        imgBoxEntity.fileUrl = fileUrl
        return imgBoxRepository.save(imgBoxEntity)
    }
}