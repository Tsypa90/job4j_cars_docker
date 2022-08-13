create table if not exists engine (
    id serial primary key
);

create table if not exists car (
    id serial primary key,
    engine_id int not null unique references engine(id)
);

create table if not exists driver (
    id serial primary key,
    name varchar(255)
);

create table if not exists history_owner (
    id serial primary key,
    driver_id int not null references driver(id),
    car_id int not null references car(id)
);

create table if not exists advt (
	id serial primary key,
	descrp varchar(255),
	brand varchar(255),
	body varchar(255),
	photo bytea,
	saled boolean,
	user_id int not null references users(id)
);

create table if not exists users (
	id serial primary key,
	name varchar(255) unique,
	password varchar(255) not null
);