# Imgbox
 Imgbox web application, frontend tbd,  kotlin springboot backend


 ## Endpoints
 - Auth Controller `/api/auth/**`
     - Registration endpoint `POST : /api/auth/register`
     - Login endpoint `POST : /api/auth/login`
       
- Imgbox Controller `/api/imgbox/**`
    - Upload endpoint `POST : /api/imgbox/upload`
    - getById `GET : /api/imgbox/id/{id}`
    - getByUsername `GET : /api/imgbox/{username}`
      - todo : refactor with user/username literal before to avoid conflicts
        
    - downloadByUsername `GET : /api/imgbox/{username}/download`
      - todo : same as above
        
    - downloadImage `GET : /api/imgbox/storage/{objectkey}`
    - downloadImageAlt `GET : /api/imgbox/alternate/{objectkey}`
      - different implementation of above

## Services
 - `ImageStorageService`
    - Upload and get files from MinIO client

 - `UserService`
    - Stores and retrieves Users from the database

- `ImgboxService`
    - Stores and retrieves ImgBoxes from the database

- `JwtTokenService`
    - Generates, parses and validates jwt tokens
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
