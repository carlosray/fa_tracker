--liquibase formatted sql

--changeset carlosray:1
--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:0 select count(1) from information_schema.tables where table_schema = 'public' and table_name = 'group';
create table "group"
(
    id          bigint generated by default as identity
        constraint pk_group
            primary key,
    name        varchar(255),
    config      jsonb     not null,
    description text,
    created     timestamp    default now(),
    created_by  varchar(255) default CURRENT_USER,
    modified    timestamp          default now(),
    modified_by varchar(255)       default CURRENT_USER
);
--rollback drop table "group"
