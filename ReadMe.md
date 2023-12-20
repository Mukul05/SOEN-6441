User Management Crud Operation
==================================

Welcome to the repository of User Manager Pro, an efficient and robust platform for managing user accounts. This application is built with a powerful combination of React for the frontend and Java Spring Framework for the backend, offering a seamless experience in user management operations.

Documentation/Video Link :- https://drive.google.com/drive/folders/1nbBGct91l1YOohoG26N3sL8YSEEpJxxW?usp=sharing

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

To Start Front End :-
# React.js CRUD App with React Router and Axios

Build a React.js CRUD Application to consume Web API, display and modify data with Router, Axios & Bootstrap.

Users Application in that:
- Each User has id, first name, last name, email.
- We can create, retrieve, update, delete Users.
- There is a Search bar for finding Users by name.
- For users list, there is pagination

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

### Set port
.env
```
PORT=8081
```

## Project setup

In the project directory, you can run:

```
npm install
# or
yarn install
```

or

### Compiles and hot-reloads for development

```
npm start
# or
yarn start
```

Open [http://localhost:8081](http://localhost:8081) to view it in the browser.

The page will reload if you make edits.




