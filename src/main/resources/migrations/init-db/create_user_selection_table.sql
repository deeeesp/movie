create sequence user_selection_sequence start with 1 increment by 1;

create table user_selection
(
    id      bigint primary key default nextVal('user_selection_sequence'),
    user_id bigint not null references app_user (id),
        selection_id bigint not null references selection (id)
)
