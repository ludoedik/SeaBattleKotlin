drop table if exists Client;
create table Client
(
    ID INTEGER auto_increment,
    USERNAME VARCHAR not null,
    PASSWORD VARCHAR not null
);