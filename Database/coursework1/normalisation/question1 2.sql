drop database if exists student;
create database student;
use student;

drop table if exists `grade`;
drop table if exists `stuId`;

create table `stuId`(
    `stuId` varchar(100) not null primary key,
    `name` varchar(100) not null,
    `gender` varchar(1) not null
);

create table `grade`(
    `stuId` varchar(100) not null primary key ,
    `unit` varchar(100) not null,
    `grade` INTEGER not null ,
    CONSTRAINT copop_city foreign key (`stuId`) references stuId(`stuId`)
);