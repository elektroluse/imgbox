package com.oivi.imgboxb.repositories

import com.oivi.imgboxb.domain.entities.ImgBoxEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ImgBoxRepository : JpaRepository<ImgBoxEntity,Long?> {
}