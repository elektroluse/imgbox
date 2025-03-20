# ImgBox: A Full-Stack Image Repository Platform
ImgBox is a full-stack web application that lets users store, organize, and share images online. Built with a React frontend (WIP slightly behind backend) and a Kotlin SpringBoot backend, it provides an image management solution where users can upload images with descriptions and tags, search by various criteria, and manage their image collections. The platform uses PostgreSQL for data storage and MinIO for scalable image file management, secured with JWT authentication.

It is a hobby project meant to familiarize myself with JVM backend development and REST APIs within the Spring ecosystem. Features are added/removed as I continue to learn and decide how I want the features to be.    


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
 
 - User Controller
     - List endpoint `GET : /api/v1/users` (phasing out but still in use in frontend) 
     - List Endpoint `GET : /api//users`   (Pageable)
     - Me endpoint `GET : /api/v1/me`
       - Responds with user corresponding to authentication token in request 
- Imgbox Controller `/api/imgbox/**`
    - Upload endpoint `POST : /api/imgbox/upload`
    - getById `GET : /api/imgbox/id/{id}`
    - Update `PUT : /api/imgbox/id/{id}`
      - Update everything except id, created_at and file_url (if requesting user is the owner)
      - Works but not happy with implementation will change later
      - Maybe add edited timestamp to db
    - Delete `DELETE : /api/imgbox/id/{id}`
      - Deletes entry from database and the referenced imagefile from the fileserver 
    - getByUsername `GET : /api/imgbox/username/{username}` (Pageable)
        
    - downloadByUsername `GET : /api/imgbox/username/{username}/download`
        
    - downloadImage `GET : /api/imgbox/storage/{objectkey}`
    - downloadImageAlt `GET : /api/imgbox/alternate/{objectkey}`
      - different implementation of above
    - searchByTitle  `GET : /api/imgbox/search/title/{searchTerm}`
      - Returns list of imgboxes where the title contains the search term 
    - List by tag  `GET : /api/imgbox/tag/{tag}` (Pageable)
      - Not used on the frontend but going to replace  `GET : /api/tags/list/imgboxes/{tag}`

 - Tag Controller `/api/tags/**`
    - List all endpoint `GET : /api/tags/list/all`
    - List all with count endpoint `GET : /api/tags/list/count` (Pageable)
    - List imgboxes with tag endpoint `GET : /api/tags/list/imgboxes/{tag}` (to be removed)
   
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
