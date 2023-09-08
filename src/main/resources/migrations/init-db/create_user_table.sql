create sequence user_sequence start with 1 increment by 1;

create table app_user
(
    id      bigint primary key default nextVal('user_sequence'),
    status varchar not null,
    email varchar not null,
    password varchar not null,
    role varchar not null,
    username varchar not null
)