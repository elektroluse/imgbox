
DROP SEQUENCE IF EXISTS "user_id_seq";
CREATE SEQUENCE "user_id_seq" INCREMENT BY 50 START 1;

DROP TABLE IF EXISTS "users";
CREATE TABLE "users" (
    "id" bigint NOT NULL,
    "username" VARCHAR(20) UNIQUE,
    "password" VARCHAR(72),
    CONSTRAINT "users_pkey" PRIMARY KEY ("id")
);

DROP SEQUENCE IF EXISTS "imgbox_id_seq";
CREATE SEQUENCE "imgbox_id_seq" INCREMENT BY 50 START 1;

DROP TABLE IF EXISTS "imgboxes";
CREATE TABLE "imgboxes" (
    "id" bigint NOT NULL,
    "title" VARCHAR(255),
    "description" VARCHAR(2048),
    "tags" VARCHAR(32) array,
    "created_at" TIMESTAMP(6),
    "user_id" bigint,
    CONSTRAINT "imgboxes_pkey" PRIMARY KEY("id"),
    CONSTRAINT "fk_user_id" FOREIGN KEY ("user_id") REFERENCES users(id)
);


DROP TABLE IF EXISTS "roles";
CREATE TABLE "roles" (
    "id" integer NOT NULL,
    "name" character varying(255),
    CONSTRAINT "roles_name_key" UNIQUE ("name"),
    CONSTRAINT "roles_pkey" PRIMARY KEY ("id")
);

INSERT INTO "roles" VALUES
(1,'ADMIN'),
(2,'USER');


DROP TABLE IF EXISTS "user_roles";
CREATE TABLE "user_roles" (
    "role_id" integer NOT NULL,
    "user_id" bigint NOT NULL,
    CONSTRAINT "user_roles_pkey" PRIMARY KEY ("role_id", "user_id"),
    CONSTRAINT "fk_role_id" FOREIGN KEY ("role_id") REFERENCES roles(id),
    CONSTRAINT "fk_user_id" FOREIGN KEY ("user_id") REFERENCES users(id)
);
