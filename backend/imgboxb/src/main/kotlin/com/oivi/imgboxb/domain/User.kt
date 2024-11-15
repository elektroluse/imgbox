package com.oivi.imgboxb.domain

import jakarta.persistence.*

@Entity
@Table(name="users")
data class User(

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    val id : Long?,

    @Column(name="username", unique = true)
    val username : String

)