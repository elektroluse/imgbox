package com.oivi.imgboxb.services.impl

import com.oivi.imgboxb.domain.entities.UserEntity
import com.oivi.imgboxb.repositories.UserRepository
import com.oivi.imgboxb.services.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository : UserRepository) : UserService {

    override fun save(userEntity: UserEntity): UserEntity {
        return userRepository.save(userEntity)
    }
}