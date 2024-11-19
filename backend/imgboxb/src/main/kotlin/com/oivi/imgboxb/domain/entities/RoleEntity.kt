package com.oivi.imgboxb.domain.entities

import jakarta.persistence.*

@Entity
@Table(name = "roles")
data class RoleEntity (
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
    val id : Long?,

    @Column(name = "name", unique = true)
    val name : String
)