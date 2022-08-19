create table if not exists car (
    id serial primary key,
    engine_id int not null unique references engine(id)
);

create table if not exists users (
	id serial primary key,
	name varchar(255) not null,
	login varchar(255) unique,
	password varchar(255) not null
);

create table if not exists advt (
	id serial primary key,
	descrp varchar(255),
	photo bytea,
	saled boolean,
	user_id int not null references users(id)
);