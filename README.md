# Imgbox
 Imgbox web application, frontend tbd,  kotlin springboot backend


 ## Endpoints
 - Auth Controller `/api/auth/**`
     - Registration endpoint `POST : /api/auth/register`
     - Login endpoint `POST : /api/auth/login`
       
- Imgbox Controller `/api/imgbox/**`
    - Create endpoint `POST : /api/imgbox/create`

## Services
 - ImageStorageService
    - Upload and get files from MinIO client

 - UserService
    - Stores and retrieves Users from the database

- ImgboxService
    - Stores and retrieves ImgBoxes from the database
## Backend
- Kotlin
- Maven
- Spring Boot
- Spring Security
- JWT tokens
- JPA
- Flyway
- MinIO
- Docker-compose with Postgres, Adminer and MinIO
