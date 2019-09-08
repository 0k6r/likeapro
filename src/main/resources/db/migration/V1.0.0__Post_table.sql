-- V1.0.0__Post_table.sql

-------------------------------------------------
-- Post table
-------------------------------------------------

CREATE SEQUENCE post_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1;

CREATE TABLE post
(
    "id"                 BIGINT       NOT NULL DEFAULT nextval('post_id_seq'),
    "title"              VARCHAR(255) NOT NULL,
    "slug"               VARCHAR(50)  NOT NULL,
    "language"           INT          NOT NULL DEFAULT 0,
    "vote"               INTEGER      NOT NULL DEFAULT 0,
    "text"               jsonb        NOT NULL,
    "enabled"            BOOLEAN      NOT NULL DEFAULT TRUE,
    "created_by"         VARCHAR(255),
    "created_date"       TIMESTAMP WITHOUT TIME ZONE,
    "last_modified_by"   VARCHAR(255),
    "last_modified_date" TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT post_pkey PRIMARY KEY (id),
    CONSTRAINT uk_post_slug UNIQUE (slug)
)
    WITH (OIDS = FALSE);

ALTER SEQUENCE post_id_seq OWNED BY post.id;

-------------------------------------------------
-- Tag table
-------------------------------------------------

CREATE SEQUENCE tag_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1;

CREATE TABLE tag
(
    "id"    BIGINT       NOT NULL DEFAULT nextval('tag_id_seq'),
    "title" VARCHAR(255) NOT NULL,
    CONSTRAINT tag_pkey PRIMARY KEY (id),
    CONSTRAINT uk_tag_title UNIQUE (title)
)
    WITH (OIDS = FALSE);

ALTER SEQUENCE tag_id_seq OWNED BY tag.id;

-------------------------------------------------
-- Comment table
-------------------------------------------------

CREATE SEQUENCE comment_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1;

CREATE TABLE comment
(
    "id"                 BIGINT       NOT NULL DEFAULT nextval('comment_id_seq'),
    "author"             VARCHAR(255) NOT NULL,
    "post_id"            BIGINT       NOT NULL,
    "parent_id"          BIGINT,
    "rating"             INTEGER      NOT NULL DEFAULT 0,
    "text"               TEXT         NOT NULL,
    "created_by"         VARCHAR(255),
    "created_date"       TIMESTAMP WITHOUT TIME ZONE,
    "last_modified_by"   VARCHAR(255),
    "last_modified_date" TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT comment_pkey PRIMARY KEY (id),
    CONSTRAINT fk_comment_post FOREIGN KEY (post_id) REFERENCES post ON DELETE CASCADE ON UPDATE CASCADE
)
    WITH (OIDS = FALSE);

ALTER SEQUENCE comment_id_seq OWNED BY comment.id;

-------------------------------------------------
-- Post tag table for many to many mapping
-------------------------------------------------

CREATE TABLE post_tag
(
    "post_id" BIGINT NOT NULL,
    "tag_id"  BIGINT NOT NULL,
    CONSTRAINT post_tag_pkey PRIMARY KEY (post_id, tag_id),
    FOREIGN KEY (post_id) REFERENCES post ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag ON DELETE CASCADE ON UPDATE CASCADE
)
    WITH (OIDS = FALSE);
