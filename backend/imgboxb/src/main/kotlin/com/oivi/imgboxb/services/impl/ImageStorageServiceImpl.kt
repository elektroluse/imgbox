package com.oivi.imgboxb.services.impl

import com.oivi.imgboxb.exceptions.ImageDownloadException
import com.oivi.imgboxb.exceptions.ImageUploadException
import com.oivi.imgboxb.services.ImageStorageService
import io.minio.*
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.InputStream
import java.time.Instant
import java.time.temporal.ChronoUnit


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
                .bucket(createBucketIfNotExist())
                .`object`(filename).stream(inputStream, f.size, -1)
                .contentType(f.contentType)
                .build()

            minioClient.putObject(putObjectArgs)

            return filename
        }
        catch (e : Exception){
            throw ImageUploadException("Image was not uploaded to storage service")
        }
    }

    override fun getImage(filename : String) : ByteArray {

        val fStream : InputStream
        val byteContent : ByteArray
        try {
            fStream = minioClient.getObject(
                GetObjectArgs.builder()
                    .bucket(bucketName)
                    .`object`(filename)
                    .build()
            )
            byteContent = IOUtils.toByteArray(fStream)
        }catch (e : Exception){
            throw ImageDownloadException("Could not retrieve the image from file storage")
        }
        return byteContent
    }

    private fun createBucketIfNotExist() : String {

        val bucketExists = minioClient.bucketExists(BucketExistsArgs.builder()
            .bucket(bucketName)
            .build());
        if(bucketExists) return bucketName

        minioClient.makeBucket(MakeBucketArgs.builder()
            .bucket(bucketName)
            .build())
        return bucketName
    }


    override fun generateUniqueName(f: MultipartFile) : String {
        return Instant.now().truncatedTo(ChronoUnit.SECONDS).toString() + "-" + (f.originalFilename?.replace(" ", "_"))
    }
}