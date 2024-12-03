package com.oivi.imgboxb.configs

import io.minio.MinioClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class MinioConfig(
    @Value("\${minio.access.key}")
    private val accessKey: String,

    @Value("\${minio.secret.key}")
    private val secretKey: String,

    @Value("\${minio.url}")
    private val minioUrl: String

    ) {

    @Bean
    fun minioClient(): MinioClient {
        return MinioClient.builder()
            .endpoint(minioUrl)
            .credentials(accessKey, secretKey)
            .build()
    }

}