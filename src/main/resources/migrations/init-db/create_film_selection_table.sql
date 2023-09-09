create sequence film_selection_sequence start with 1 increment by 1;

create table film_selection
(
    id bigint primary key default nextVal('film_selection_sequence'),
    film_id bigint not null references film (id),
    selection_id bigint not null references selection (id)
)