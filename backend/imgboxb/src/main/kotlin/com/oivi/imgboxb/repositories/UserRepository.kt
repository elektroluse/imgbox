package com.oivi.imgboxb.repositories

import com.oivi.imgboxb.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User,Long?>{
}