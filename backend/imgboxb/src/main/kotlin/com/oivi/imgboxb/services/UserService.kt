package com.oivi.imgboxb.services

import com.oivi.imgboxb.domain.entities.UserEntity
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {

    fun save(userEntity : UserEntity) : UserEntity
    fun list() : List<UserEntity>
}