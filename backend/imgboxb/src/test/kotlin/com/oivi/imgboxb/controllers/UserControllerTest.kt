package com.oivi.imgboxb.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.oivi.imgboxb.services.UserService
import com.oivi.imgboxb.testUserDtoA
import com.oivi.imgboxb.toUserEntity
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest @Autowired constructor (
    private val mockMvc : MockMvc,
    @MockkBean val userService : UserService) {


    @BeforeEach
    fun beforeEach(){
        every {
            userService.create(any())
        } answers {
            firstArg()
        }
    }

    val objectMapper : ObjectMapper = ObjectMapper();
    // deprecated
    /*@Test
    fun `test that create returns 201 on success`(){
        mockMvc.post("/v1/users"){
            contentType = MediaType.APPLICATION_JSON;
            accept = MediaType.APPLICATION_JSON;
            content = objectMapper.writeValueAsString(testUserDtoA())
        }.andExpect {
            status { isCreated() } }
    }

    @Test
    fun `test that create User saves the user`(){



        val testUser1 = testUserDtoA()
        mockMvc.post("/v1/users"){
            contentType = MediaType.APPLICATION_JSON;
            accept = MediaType.APPLICATION_JSON;
            content = objectMapper.writeValueAsString(testUser1)
        }
        val expected = testUser1.toUserEntity();
        verify{userService.create(expected)}
    }

     */
}

