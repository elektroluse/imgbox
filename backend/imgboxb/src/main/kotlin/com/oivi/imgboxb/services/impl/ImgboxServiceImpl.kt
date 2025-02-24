package com.oivi.imgboxb.services.impl

import com.oivi.imgboxb.domain.dto.ImgBoxDto
import com.oivi.imgboxb.domain.dto.ImgboxWithFileDto
import com.oivi.imgboxb.domain.dto.UserDto
import com.oivi.imgboxb.domain.entities.ImgBoxEntity
import com.oivi.imgboxb.domain.entities.UserEntity
import com.oivi.imgboxb.exceptions.ImageUploadException
import com.oivi.imgboxb.repositories.ImgBoxRepository
import com.oivi.imgboxb.repositories.UserRepository
import com.oivi.imgboxb.services.ImageStorageService
import com.oivi.imgboxb.services.ImgboxService
import com.oivi.imgboxb.services.TagService
import com.oivi.imgboxb.toImgBoxDtoSafe
import com.oivi.imgboxb.update
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream

@Service
class ImgboxServiceImpl(
    private val imageStorageService : ImageStorageService,
    private val imgBoxRepository: ImgBoxRepository,
    private val userRepository: UserRepository,
    private val tagService : TagService,
)  : ImgboxService {

    @Transactional
    override fun upload(imgBoxEntity: ImgBoxEntity, mf : MultipartFile): ImgBoxEntity {
        if(mf.isEmpty){
            throw ImageUploadException("File is empty")
        }
        val fileUrl : String = imageStorageService.uploadImage(imgBoxEntity.user.username,false,mf)
        imgBoxEntity.fileUrl = fileUrl
        imgBoxEntity.tags = imgBoxEntity.tags.map{
            tag -> tagService.getIfExistsOrCreate(tag.name)
        }.toMutableSet()
        return imgBoxRepository.save(imgBoxEntity)
    }

    @Transactional
    override fun update(id : Long, alteredData : ImgBoxDto, usernameOfUpdater : String) : ImgBoxEntity{
        val existingImgbox = getImgBox(id)
        check(existingImgbox.user.username == usernameOfUpdater)
        val updatedEntity = existingImgbox.update(alteredData)
        updatedEntity.tags = updatedEntity.tags.map{
            tag -> tagService.getIfExistsOrCreate(tag.name)
        }.toMutableSet()
        return imgBoxRepository.save(updatedEntity)
    }

    override fun getImgBox(id : Long) : ImgBoxEntity{

        return imgBoxRepository.findByIdOrNull(id)
            ?: throw NoSuchElementException("no imgbox with id : $id in database")
    }

    override fun getImgboxesByUsername(username : String) : List<ImgBoxEntity>{
        val entityWithUsername : UserEntity = userRepository.findByUsername(username)
            ?:throw UsernameNotFoundException(username)

        val userId = entityWithUsername.id
            ?: throw Exception("UNEXPECTED exception")

         return imgBoxRepository.findAllByUserId(userId)

    }
    override fun downloadFile(objKey : String) : InputStream{

        return imageStorageService.getInputStreamFromKey(objKey)
    }

    override fun packFileWithBox (imgboxes : List<ImgBoxEntity>) : List<ImgboxWithFileDto> {
        return imgboxes.map {
            ImgboxWithFileDto(
                it.toImgBoxDtoSafe(),
                IOUtils.toByteArray(imageStorageService.getInputStream(it.fileUrl)))
        }
    }
    override fun getImgboxByTitleSearch(searchCriteria : String) : List<ImgBoxEntity>{
        return imgBoxRepository.findByTitleIgnoreCaseContaining(searchCriteria)
    }
}