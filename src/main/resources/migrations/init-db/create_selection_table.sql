create sequence selection_sequence start with 1 increment by 1;

create table selection
(
    id      bigint primary key default nextVal('selection_sequence'),
    picture_id bigint not null references picture (id),
    status  varchar not null,
    name varchar not null,
    tag varchar not null,
    owner bigint not null
)