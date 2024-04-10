CREATE DATABASE IF NOT EXISTS WishlistDB ;
USE WishlistDB;
CREATE TABLE IF NOT EXISTS Users (
    id int auto_increment primary key,
    name varchar(30) NOT NULL,
    password varchar(50) NOT NULL
    );

CREATE TABLE IF NOT EXISTS Wishlists (
    id int auto_increment primary key,
    name varchar(30) NOT NULL,
    description varchar(255) NOT NULL DEFAULT ' ',
    user_id int
    );

CREATE TABLE IF NOT EXISTS Wish (
    id int auto_increment primary key,
    name varchar(50) NOT NULL,
    description varchar(255) NOT NULL DEFAULT ' ',
    price DOUBLE NOT NULL,
    url varchar(500), -- stackoverflow anbefalede denne længde
    wishlist_id int NOT NULL
)