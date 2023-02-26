--liquibase formatted sql

--changeset carlosray:1
--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:0 select count(1) from information_schema.tables where table_schema = 'public' and table_name = 'role';
create table "role"
(
    id         bigint generated by default as identity
        constraint pk_role
            primary key,
    user_id    bigint       not null,
    permission varchar(255) not null,
    created    timestamp default now(),

    foreign key (user_id) references "user" (id)
);
--rollback drop table "role"

--changeset carlosray:2
--preconditions onFail:MARK_RAN
create unique index if not exists role_user_id_permission_key on "role" (user_id, permission);
--rollback drop index role_user_id_permission_key