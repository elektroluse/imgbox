package com.oivi.imgboxb.repositories

import com.oivi.imgboxb.domain.ImgBox
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ImgBoxRepository : JpaRepository<ImgBox,Long?> {
}