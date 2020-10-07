drop database if exists cities_2015;
create database cities_2015;
use cities_2015;

drop table if exists `co_pop`;
drop table if exists `city`;

create table `city`(
    `city` varchar(100) not null primary key,
    `country` varchar(100) not null,
    `pop` INTEGER not null,
    `capital` BOOLEAN not null
);

create table `co_pop`(
    `city` varchar(100) not null primary key ,
    `co_pop` INTEGER not null,
    CONSTRAINT copop_city foreign key (`city`) references city(`city`)
);