create sequence film_sequence start with 1 increment by 1;

create table film
(
    id      bigint primary key default nextVal('film_sequence'),
    picture_id bigint not null references picture (id),
    status  varchar not null,
    country varchar not null,
    director varchar not null,
    fees bigint not null ,
    plot varchar not null,
    release_year integer not null ,
    title varchar not null,
    rating real
)