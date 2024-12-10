package com.oivi.imgboxb.services.impl

import com.oivi.imgboxb.domain.entities.ImgBoxEntity
import com.oivi.imgboxb.exceptions.ImageUploadException
import com.oivi.imgboxb.exceptions.ImgBoxNotFoundException
import com.oivi.imgboxb.repositories.ImgBoxRepository
import com.oivi.imgboxb.services.ImageStorageService
import com.oivi.imgboxb.services.ImgboxService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream

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
        val filename : String = imageStorageService.uploadImage(imgBoxEntity.user.username,false,mf)
        imgBoxEntity.filename = filename
        return imgBoxRepository.save(imgBoxEntity)
    }

    override fun getImgBox(id : String) : ImgBoxEntity{
        val imgboxEntity = imgBoxRepository.findByIdOrNull(id.toLong())
            ?: throw ImgBoxNotFoundException("No imagebox with id : $id")


         val imageByteArray : ByteArray = imageStorageService.getImage(imgboxEntity.filename)
        
    }
}