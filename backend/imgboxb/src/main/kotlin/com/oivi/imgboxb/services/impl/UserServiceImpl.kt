package com.oivi.imgboxb.services.impl

import com.oivi.imgboxb.domain.entities.UserEntity
import com.oivi.imgboxb.exceptions.RoleRepositoryException
import com.oivi.imgboxb.repositories.RoleRepository
import com.oivi.imgboxb.repositories.UserRepository
import com.oivi.imgboxb.services.UserService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository : UserRepository,
    private val roleRepository: RoleRepository) : UserService {

    @Transactional
    override fun create(userEntity: UserEntity): UserEntity {

        // Throws an IllegalStateException if username exists in db
        check(!userRepository.existsByUsername(userEntity.username))

        val userRoleEntity = roleRepository.findByIdOrNull(2)
            ?: throw RoleRepositoryException("Role repository has no row with id 2 (USER)")

        userEntity.roles.add(userRoleEntity)
        return userRepository.save(userEntity)

    }

    override fun getUser(username : String) : UserEntity{
        return userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("No user with \'$username\' in database")
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