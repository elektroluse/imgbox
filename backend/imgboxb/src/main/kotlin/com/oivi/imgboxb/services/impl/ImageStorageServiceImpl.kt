package com.oivi.imgboxb.services.impl

import com.oivi.imgboxb.services.ImageStorageService
import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.Instant

@Service
class ImageStorageServiceImpl @Autowired constructor(
    private val minioClient : MinioClient,

    @Value("\${minio.bucket.name}")
    var bucketName : String,

    @Value("\${minio.url}")
    val minioUrl : String

) : ImageStorageService {

    override fun uploadImage(username : String, private : Boolean, f : MultipartFile) : String{
        val filename = generateUniqueName(f)
        try {


            val inputStream = f.inputStream
            val putObjectArgs = PutObjectArgs.builder()
                .bucket(getOrCreateBucketName(username,private))
                .`object`(filename).stream(inputStream, f.size, -1)
                .contentType(f.contentType)
                .build()

            minioClient.putObject(putObjectArgs)

            return "$minioUrl $bucketName $filename"
        }
        catch (e : Exception){
            throw RuntimeException("Image was not stored", e)
        }
    }

    private fun getOrCreateBucketName(username : String, private : Boolean) : String{
        val privateBucketName = "$bucketName/$username/private"
        val publicBucketName = "$bucketName/$username/public"

        val privateBucketExists = minioClient.bucketExists(BucketExistsArgs.builder()
            .bucket(privateBucketName)
            .build());

        val publicBucketExists = minioClient.bucketExists(BucketExistsArgs.builder()
            .bucket(publicBucketName)
            .build());

        if(private){
            if (!privateBucketExists){
                minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(privateBucketName)
                    .build())
            }
            return privateBucketName
        }
        else{
            if (!publicBucketExists){
                minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(privateBucketName)
                    .build())
            }
            return publicBucketName
        }
    }

    override fun generateUniqueName(f: MultipartFile) : String {
        return Instant.now().toString() + "-" + (f.originalFilename?.replace(" ", "_"))
    }
}