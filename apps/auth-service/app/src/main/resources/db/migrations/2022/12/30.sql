--liquibase formatted sql

--changeset carlosray:1
--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:0 select count(1) from information_schema.tables where table_schema = 'public' and table_name = 'client';
create table client
(
    id            varchar(50) primary key,
    refresh_token varchar(255) unique not null,
    updated       timestamp default now()
);
--rollback drop table client