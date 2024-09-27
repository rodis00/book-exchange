create sequence author_seq
    start with 1
    increment by 25;

create sequence book_seq
    start with 1
    increment by 25;

create sequence image_seq
    start with 1
    increment by 25;

create sequence order_seq
    start with 1
    increment by 25;

create sequence user_seq
    start with 1
    increment by 25;

create table image_
(
    id            bigint primary key,
    original_name varchar,
    data          oid,
    content_type  varchar,
    updated_at    timestamp default current_timestamp,
    created_at    timestamp default current_timestamp
);

create table user_
(
    id              bigint primary key,
    first_name      varchar,
    last_name       varchar,
    nick            varchar unique not null,
    date_of_birth   date,
    email           varchar unique not null,
    password        varchar        not null,
    image_url       varchar,
    coin            integer,
    referral_number varchar unique not null,
    updated_at      timestamp default current_timestamp,
    created_at      timestamp default current_timestamp
);

create table author_
(
    id            bigint primary key,
    first_name    varchar,
    last_name     varchar,
    nick          varchar unique not null,
    date_of_birth date,
    image_url     varchar,
    updated_at    timestamp default current_timestamp,
    created_at    timestamp default current_timestamp
);

create table order_
(
    id              bigint primary key,
    order_type      varchar,
    user_id         bigint,
    referral_number varchar,
    status          varchar,
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp,
    foreign key (user_id) references user_ (id)
);

create table book_
(
    id           bigint primary key,
    title        varchar,
    category     varchar,
    description  text,
    release_date date,
    price        integer,
    image_url    varchar,
    author_id    bigint,
    is_available boolean,
    condition    varchar,
    updated_at   timestamp default current_timestamp,
    created_at   timestamp default current_timestamp,
    foreign key (author_id) references author_ (id)
);

create table order_book_
(
    order_id bigint,
    book_id  bigint,
    primary key (order_id, book_id),
    foreign key (order_id) references order_ (id),
    foreign key (book_id) references book_ (id)
)
