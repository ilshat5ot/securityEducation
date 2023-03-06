--- Dao Authentication ---
create table users (
    id bigserial,
    username varchar(30) not null,
    password varchar(80),
    email varchar(50) UNIQUE ,
    primary key (id)
);

create table roles(
    id serial,
    name varchar(50) not null,
    primary key (id)

);

create table users_roles (
    user_id bigint not null,
    role_id int not null,
    primary key (user_id, role_id),
    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id)
);

insert into roles (name)
values ('ROLE_USER'), ('ROLE_ADMIN');

insert into users (username, password, email)
values ('user', '$2a$10$DZgsBGRJRTbfnqxaVIUW3.ZWOcGk8uxRAN5bmuKotfHtdmiZ1D95q', 'user@gmail.com');

insert into users_roles (user_id, role_id)
values (1, 2);