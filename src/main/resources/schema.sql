create table if not exists users(
    id serial,
    username varchar(100) not null,
    password varchar(100) not null,
    enabled boolean default TRUE);

create table if not exists authorities(
    id serial,
    username bigint not null,
    authority varchar(100) not null);