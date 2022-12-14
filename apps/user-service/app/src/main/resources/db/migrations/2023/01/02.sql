--liquibase formatted sql

--changeset carlosray:1
--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:0 select count(1) from information_schema.tables where table_schema = 'public' and table_name = 'user';
create table "user"
(
    id         bigint generated by default as identity
        constraint pk_user
            primary key,
    login      varchar(60) unique  not null,
    email      varchar(100) unique not null,
    password   varchar(100)        not null,
    first_name varchar(255),
    last_name  varchar(255),
    deleted    timestamp null,
    created    timestamp default now()
);
--rollback drop table "user"