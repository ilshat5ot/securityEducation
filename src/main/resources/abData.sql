create table users (
                       username varchar(255) primary key,
                       password varchar(255) not null,
                       enabled boolean not null
);

create table authorities(
                            username varchar(255) not null,
                            authority varchar(255) not null,
                            foreign key(username) references users (username), unique (username, authority)
);

insert into authorities (username, authority)
values (
           'admin',
           'READ_PROFILE'
       );

insert into authorities (username, authority)
values (
           'user',
           'READ_PROFILE'
       );