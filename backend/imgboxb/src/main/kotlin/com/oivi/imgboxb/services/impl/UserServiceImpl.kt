package com.oivi.imgboxb.services.impl

import com.oivi.imgboxb.domain.entities.UserEntity
import com.oivi.imgboxb.repositories.UserRepository
import com.oivi.imgboxb.services.UserService
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository : UserRepository) : UserService {

    override fun save(userEntity: UserEntity): UserEntity {
        return userRepository.save(userEntity)
    }

    override fun list(): List<UserEntity> {
        return userRepository.findAll()
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val userEntity: UserEntity = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("$username not found" )
        return User(userEntity.username, userEntity.password, mapRolesToAuthorities(userEntity))
    }

    private fun mapRolesToAuthorities(u : UserEntity) : Collection<GrantedAuthority>{
       return u.roles.map {
            SimpleGrantedAuthority(it.name)
        }

    }
}