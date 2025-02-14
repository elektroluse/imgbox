package com.oivi.imgboxb.services.impl

import com.oivi.imgboxb.domain.entities.ImgBoxEntity
import com.oivi.imgboxb.domain.entities.TagEntity
import com.oivi.imgboxb.repositories.TagRepository
import com.oivi.imgboxb.services.TagService
import com.oivi.imgboxb.services.UserService
import org.springframework.stereotype.Service

@Service
class TagServiceImpl(
    private val tagRepository : TagRepository) : TagService {
        
    override fun getIfExistsOrCreate(name : String) : TagEntity{
         val tagEntity = tagRepository.findByName(name)
             ?:  return create(
                 TagEntity(null,name, mutableSetOf())
             )
        return tagEntity
    }

    override fun create(tagEntity : TagEntity) : TagEntity{
        return tagRepository.save(tagEntity)
    }
    override fun list() : List<TagEntity>{

        return tagRepository.findAll()
    }

    override fun listImgboxesWithTag(tag: String): List<ImgBoxEntity> {
        val tagEntity = tagRepository.findByName(tag)
            ?: return emptyList()
        return tagEntity.imgboxes.toList()
    }
}
