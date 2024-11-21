DROP SEQUENCE IF EXISTS "user_id_seq";
CREATE SEQUENCE "user_id_seq" INCREMENT BY 50 START 1;

DROP TABLE IF EXISTS "users";

CREATE TABLE "users"(
    "id" bigint NOT NULL,
    "username" VARCHAR(12) UNIQUE,
    "password" VARCHAR(256),
    CONSTRAINT "users_pkey" PRIMARY KEY("id")
);

DROP SEQUENCE IF EXISTS "roles_id_seq";
DROP TABLE IF EXISTS "roles";
CREATE SEQUENCE "role_id_seq" INCREMENT BY 50 START 1;

CREATE TABLE "roles"(
    id int NOT NULL,
    "name" VARCHAR(32) UNIQUE,
    CONSTRAINT "roles_pkey" PRIMARY KEY("id")
);

DROP SEQUENCE IF EXISTS "imgbox_id_seq";
CREATE SEQUENCE "imgbox_id_seq" INCREMENT BY 50 START 1;

DROP TABLE IF EXISTS "imgboxes";

CREATE TABLE "imgboxes"(
    "id" bigint NOT NULL,
    "title" VARCHAR(255),
    "description" VARCHAR(2048),
    "tags" VARCHAR(32) array,
    "created_at" TIMESTAMP,
    "user_id" bigint,
    CONSTRAINT "imgboxes_pkey" PRIMARY KEY("id")
);