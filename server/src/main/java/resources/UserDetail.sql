create database DummyDb;

use DummyDb

CREATE TABLE UserDetail(id bigint PRIMARY KEY auto_increment, firstName varchar(200) not null, lastName varchar(200) not null, email varchar(200) not null, UNIQUE (email));

insert into UserDetail values ('Mukul','Kumar','mukulsharma1998@gmail.com');
insert into UserDetail values ('Gurjeet','Singh','Sgurjeet99@gmail.com');

commit;