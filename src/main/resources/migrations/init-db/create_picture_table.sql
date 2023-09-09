create sequence picture_sequence start with 1 increment by 1;

create table picture
(
    id      bigint primary key default nextVal('picture_sequence'),
    picture_type varchar not null
)