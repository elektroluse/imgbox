package com.oivi.imgboxb

import com.oivi.imgboxb.domain.dto.*
import com.oivi.imgboxb.domain.entities.ImgBoxEntity
import com.oivi.imgboxb.domain.entities.RoleEntity
import com.oivi.imgboxb.domain.entities.UserEntity
import com.oivi.imgboxb.repositories.RoleRepository
import org.springframework.security.crypto.password.PasswordEncoder
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

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

fun RegistrationForm.toUserEntity(encodedPassword : String) = UserEntity(
    id = null,
    username = this.username,
    password = encodedPassword
)

fun UserEntity.toUserDtoOmitP() = UserDto(
    id = this.id,
    username = this.username,
    password = ""
)

fun ImgBoxEntity.toImgBoxDto() = ImgBoxDto(

    id = this.id,
    title = this.title,
    description = this.description,
    tags = this.tags,
    user = this.user.toUserDto(),
    fileUrl = this.fileUrl,
    createdAt = this.createdAt

)

fun ImgBoxEntity.toImgBoxDtoSafe() = ImgBoxDto(

    id = this.id,
    title = this.title,
    description = this.description,
    tags = this.tags,
    user = this.user.toUserDtoOmitP(),
    fileUrl = this.fileUrl,
    createdAt = this.createdAt

)


fun ImgBoxDto.toImgBoxEntity() = ImgBoxEntity(

    id = this.id,
    title = this.title,
    description = this.description,
    tags = this.tags,
    fileUrl = this.fileUrl,
    user = this.user.toUserEntity(),
    createdAt = this.createdAt

)

fun ImgboxFormDto.toImgBoxEntity(userEntity : UserEntity) = ImgBoxEntity(
    id = null,
    title = this.title,
    description = this.description,
    tags = this.tags,
    fileUrl = "",
    user = userEntity,
    createdAt = Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS))
)