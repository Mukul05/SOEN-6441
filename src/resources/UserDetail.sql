create database DummyDb;

Use DummyDb;


CREATE TABLE UserDetail(firstName varchar(200),
lastName varchar(200) ,
email varchar(200) );

insert into UserDetail values ('Mukul','Kumar','mukulsharma1998@gmail.com');
insert into UserDetail values ('Gurjeet','Singh','Sgurjeet99@gmail.com');

commit;