create sequence user_film_sequence start with 1 increment by 1;

create table user_film
(
    id      bigint primary key default nextVal('user_film_sequence'),
    user_id bigint not null references app_user (id),
    film_id bigint not null references film (id)
)
