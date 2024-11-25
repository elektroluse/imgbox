package com.oivi.imgboxb.domain.entities

import jakarta.persistence.*

@Entity
@Table(name = "roles")
data class RoleEntity (
    @Id
    @Column(name = "id")
    val id : Int,

    @Column(name = "name", unique = true)
    val name : String
)