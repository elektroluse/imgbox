package com.oivi.imgboxb.services.impl

import com.oivi.imgboxb.repositories.UserRepository
import com.oivi.imgboxb.testUserEntityA
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class UserServiceImplTest @Autowired constructor(
    private val underTest : UserServiceImpl,
    private val userRepository : UserRepository){

    @Test
    fun`test that save stores the user in db`(){
        val savedUser = underTest.save(testUserEntityA())
        assertThat(savedUser.id).isNotNull();
        val retrievedFromDb = userRepository.findByIdOrNull(savedUser.id);
        assertThat(retrievedFromDb).isNotNull();
        assertThat(retrievedFromDb).isEqualTo(
            testUserEntityA(id = savedUser.id)
        )

    }

}