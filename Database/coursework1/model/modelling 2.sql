drop database if exists Steam_model;
create database Steam_model;
use Steam_model;

drop table if exists `Tag Gmae Library`;
drop table if exists `User Game`;
drop table if exists `tag`;
drop table if exists `Game`;
drop table if exists `Game Developer`;
drop table if exists `User`;

create table User(
    `E-mail` varchar(100) not null primary key,
    `password` varchar(100) not null,
    `username` varchar(100) not null,
    `Personal profile` varchar(500),
    CONSTRAINT user_name UNIQUE (`username`)
);

create table `Game Developer`(
    `ID` INTEGER not null primary key auto_increment,
    `name` varchar(100) not null ,
    `description` varchar(500),
    CONSTRAINT game_dev_name UNIQUE (`name`)
);

create table `Game`(
    `Game_ID` INTEGER not null primary key auto_increment,
    `Developer` varchar(100) not null,
    `name` varchar(100) not null,
    `description` varchar(500),
    `Release date` DATE
    CONSTRAINT game_dev foreign key (`Developer`) references `Game Developer`(name)
);

create table `User_Game`(
    `E-mail` varchar(100) not null,
    `Game_ID` INTEGER not null,
    `Software license` varchar(100) not null unique,
    `comment` varchar(500),
    CONSTRAINT UG_email foreign key (`E-mail`) references  User(`E-mail`),
    CONSTRAINT UG_gid foreign key (`Game_ID`) references Game(`Game_ID`),
    CONSTRAINT primary key (`E-mail`,`Game_ID`)
);

create table `tag`(
  `tag name` varchar(100) not null primary key
);

create table `Tag Game Library`(
    `Game_ID` INTEGER not null,
    `tag name` varchar(100) not null,
    CONSTRAINT TGL_gid foreign key (`Game_ID`) references Game(`Game_ID`),
    CONSTRAINT TGL_tag foreign key (`tag name`) references tag(`tag name`),
    CONSTRAINT primary key (`Game_ID`,`tag name`)
);









