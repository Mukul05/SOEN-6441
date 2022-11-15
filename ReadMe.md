SOEN 6441 API Fetcher
==================================

This is the repository for the API_Fetch project,


Project description
-------------------

Our goal is to  develop a system that can read API/JSON data and produce results to the application and store the data into database and perform CRUD Opertaions.

Requirements
--------------
MySql Workbench,
Eclipse,
TomCat


SQL Example
--------------
Create connections with the following details
Port :- 3306
username root
password admin
Open MySQL and create database DummyDb or execute the below queries
create database DummyDb;

use DummyDb
 

CREATE TABLE UserDetail(id bigint PRIMARY KEY 
auto_increment, 
firstName varchar(200) not null,
lastName varchar(200) not null,
email varchar(200) not null,
UNIQUE (email));

Jar Used
----------------
Gson 2.8.6.jar,
mysql-connector-java-8.0.29.jar,
servlet-api.jar,


Building
========

To build, open Eclipse,

Start Server.




