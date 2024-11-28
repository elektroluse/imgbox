package com.oivi.imgboxb.domain.entities

import jakarta.persistence.*

@Entity
@Table(name="users")
data class UserEntity(

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    val id : Long?,

    @Column(name="username", unique = true)
    val username : String,
    
    @Column(name = "password")
    var password : String,

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "user_roles", joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    val roles : MutableSet<RoleEntity> = mutableSetOf()
)