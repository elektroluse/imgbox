package com.oivi.imgboxb

import com.oivi.imgboxb.domain.dto.ImgBoxDto
import com.oivi.imgboxb.domain.dto.RegistrationForm
import com.oivi.imgboxb.domain.dto.UserDto
import com.oivi.imgboxb.domain.dto.UserProfile
import com.oivi.imgboxb.domain.entities.ImgBoxEntity
import com.oivi.imgboxb.domain.entities.RoleEntity
import com.oivi.imgboxb.domain.entities.UserEntity
import com.oivi.imgboxb.repositories.RoleRepository
import org.springframework.security.crypto.password.PasswordEncoder
import java.sql.Timestamp

fun UserEntity.toUserDto() = UserDto(
        id = this.id,
        username = this.username,
        password = this.password
    )

fun UserEntity.toUserProfile() = UserProfile(
    username = this.username
)

fun UserDto.toUserEntity() = UserEntity(
    id = this.id,
    username = this.username,
    password = this.password
)

fun RegistrationForm.toUserEntity(encodedPassword : String, roles : MutableSet<RoleEntity>) = UserEntity(
    id = null,
    username = this.username,
    password = encodedPassword,
    roles = roles
)

fun ImgBoxEntity.toImgBoxDto() = ImgBoxDto(

    id = this.id,
    title = this.title,
    description = this.description,
    tags = this.tags,
    user = this.user.toUserDto(),
    createdAt = this.createdAt

)


fun ImgBoxDto.toImgBoxEntity() = ImgBoxEntity(

    id = this.id,
    title = this.title,
    description = this.description,
    tags = this.tags,
    user = this.user.toUserEntity(),
    createdAt = this.createdAt

)