-- Example initial Flyway migration
-- Uses placeholders defined in application.yml or Gradle flyway {} block
-- Placeholders: ${schema}, ${appuser}

-- Ensure schema exists
create schema if not exists ${schema};

-- Example extension (PostgreSQL)
create extension if not exists "uuid-ossp";

create table realm
(
    id   uuid         not null
        primary key,
    name varchar(255) not null
);

alter table realm
    owner to ${appuser};

create table client
(
    id           uuid         not null
        primary key,
    realm_id     uuid
        constraint fk_client_realm
            references realm,
    callback_url varchar(255) not null,
    hosts        varchar(255) not null
);

alter table client
    owner to ${appuser};

create table config
(
    bool_value   boolean,
    int_value    integer,
    client_id    uuid
        constraint fk_config_client
            references client,
    id           uuid         not null
        primary key,
    realm_id     uuid         not null
        constraint fk_config_realm
            references realm,
    config_type  varchar(255)
        constraint config_config_type_check
            check ((config_type)::text = ANY
                   ((ARRAY ['STRING'::character varying, 'INTEGER'::character varying, 'BOOLEAN'::character varying])::text[])),
    key          varchar(255) not null,
    string_value varchar(255)
);

alter table config
    owner to ${appuser};

create table device
(
    id       uuid         not null
        primary key,
    realm_id uuid         not null
        constraint fk_device_realm
            references realm,
    token    varchar(255) not null
);

alter table device
    owner to ${appuser};

create table groups
(
    id       uuid         not null
        primary key,
    realm_id uuid         not null
        constraint fk_group_realm
            references realm,
    name     varchar(255) not null
);

alter table groups
    owner to ${appuser};

create table permission
(
    id       uuid         not null
        primary key,
    realm_id uuid         not null
        constraint fk_permission_realm
            references realm,
    name     varchar(255) not null
);

alter table permission
    owner to ${appuser};

create index ix_realm_name
    on realm (name);

create table role
(
    id       uuid         not null
        primary key,
    realm_id uuid         not null
        constraint fk_role_realm
            references realm,
    name     varchar(255) not null
);

alter table role
    owner to ${appuser};

create table group_role
(
    group_id uuid not null
        constraint fk_group_group_role
            references groups,
    role_id  uuid not null
        constraint fk_role_group_role
            references role
);

alter table group_role
    owner to ${appuser};

create table role_permission
(
    permission_id uuid not null
        constraint fk_permission_role_permission
            references permission,
    role_id       uuid not null
        constraint fk_role_role_permission
            references role
);

alter table role_permission
    owner to ${appuser};

create table users
(
    id          uuid         not null
        primary key,
    realm_id    uuid         not null
        constraint fk_user_realm
            references realm,
    email       varchar(255) not null
        unique,
    family_name varchar(255),
    given_name  varchar(255),
    password    varchar(255) not null,
    type        varchar(255) not null
        constraint users_type_check
            check ((type)::text = ANY ((ARRAY ['HUMAN'::character varying, 'APPLICATION'::character varying])::text[])),
    username    varchar(255) not null
        unique
);

alter table users
    owner to ${appuser};

create table group_user
(
    group_id uuid not null
        constraint fk_group_group_user
            references groups,
    user_id  uuid not null
        constraint fk_user_group_user
            references users
);

alter table group_user
    owner to ${appuser};

create table user_device
(
    device_id uuid not null
        constraint fk_device_user_device
            references device,
    user_id   uuid not null
        constraint fk_user_user_device
            references users
);

alter table user_device
    owner to ${appuser};

create index ix_user_realm_id
    on users (realm_id, id);

