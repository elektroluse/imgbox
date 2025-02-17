# Imgbox
 Imgbox web application, React frontend, kotlin springboot backend

## How to run
- `git clone https://github.com/elektroluse/imgbox.git`
- Backend
  - `cd imgbox/backend/imgboxb`
  - `docker compose up -d`
  - `./mvnw spring-boot:run`
- Frontend
  - `cd imgbox/frontend`
  - `npm install`
  - `npm run dev`

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
    - searchByTitle  `GET : /api/imgbox/search/title/{searchTerm}`
      - Returns list of imgboxes where the title contains the search term 
 
 - Tag Controller `/api/tags/**`
    - List all endpoint `GET : /api/tags/list/all`
    - List all with count endpoint `GET : /api/tags/list/count`
    - List imgboxes with tag endpoint `GET : /api/tags/list/imgboxes/{tag}`
   
## Services
 - `ImageStorageService`
    - Upload and get files from MinIO client

 - `UserService`
    - Stores and retrieves Users from the database

- `ImgboxService`
    - Stores and retrieves ImgBoxes from the database

- `TagService`
  - Tag related services 

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
