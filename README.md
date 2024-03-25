# MDD fullstack app

This application is an MVP designed to create a social network dedicated to developers. The MVP app allows users to subscribe to topics related to programming and view their news feed with articles associated to the chosen topics. Users can also react to each article by posting comments.


## Features

- User registration with name, email and password.
- User login with email and password.
- Get the news feed of articles related to the user's subscriptions, sorted by date.
- Display the detail of a topic with the option to post a comment about it.
- Create a new article.
- Get the list of topics with the option to subscribe or unsubscribe.
- Get the details of the currently logged-in user with the option to edit them (name, email, password) and unsubscribe from subscriptions.


## Technologies Used

- Java 17
- Angular CLI 14.2.12
- Spring Boot 3
- Lombok executable jar file
- Spring Security with JWT (JSON Web Tokens) authentication
- MySQL + MySQL Workbench


## Getting Started

### Prerequisites

- Java Development Kit (JDK) 17
- Maven
- Angular CLI 14 and Nodes.js
- MySQL


### Clone the Project

git clone https://github.com/alexia-pratensi/Application-full-stack-complete.git

### Install Dependencies

#### On the frontend:

- Go inside folder:  cd front

- Run this command line:  npm install


#### On the backend:

- Go inside folder:  cd back-app-fullstack

- Run this command line:  mvn clean install


### SQL setup

SQL script to create the schema is available ressources/script-mdd.sql

Don't forget to add your database credentials in application.properties :

- spring.datasource.username=xxxx
  
- spring.datasource.password=xxxx


## Run the project

##### On the backend:

Run this command line: mvn spring-boot:run

##### On the frontend:

Run this command line: npm run start

## Author
Alexia PRATENSI

