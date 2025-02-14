

DROP SEQUENCE IF EXISTS "tag_id_seq";
CREATE SEQUENCE "tag_id_seq" INCREMENT BY 50 START 1;
DROP TABLE IF EXISTS "tags";
CREATE TABLE "tags" (
    "id" integer NOT NULL,
    "name" character varying(32),
    CONSTRAINT "tags_name_key" UNIQUE ("name"),
    CONSTRAINT "tags_pkey" PRIMARY KEY ("id")
);
DROP TABLE IF EXISTS "imgbox_tags";
CREATE TABLE "imgbox_tags" (
    "tag_id" integer NOT NULL,
    "imgbox_id" bigint NOT NULL,
    CONSTRAINT "tags_imgboxes_pkey" PRIMARY KEY ("tag_id", "imgbox_id"),
    CONSTRAINT "fk_tag_id" FOREIGN KEY ("tag_id") REFERENCES tags(id),
    CONSTRAINT "fk_imgbox_id" FOREIGN KEY ("imgbox_id") REFERENCES imgboxes(id)
);

ALTER TABLE "imgboxes"
DROP COLUMN "tags";


