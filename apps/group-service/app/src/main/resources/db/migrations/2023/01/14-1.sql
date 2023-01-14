--liquibase formatted sql

--changeset carlosray:1
--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:0 select count(1) from information_schema.tables where table_schema = 'public' and table_name = 'user_group';
create table user_group
(
    user_id  bigint not null,
    group_id bigint not null,

    foreign key (group_id) references "group" (id)
);
--rollback drop table user_group


--changeset carlosray:2
--preconditions onFail:MARK_RAN
create unique index if not exists user_group_user_id_group_id_key on user_group (user_id, group_id);
--rollback drop index user_group_user_id_group_id_key