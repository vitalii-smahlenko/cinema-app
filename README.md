<p align="center">
<img src="images/Cinema-app.png">
<p>

![GitHub repo size](https://img.shields.io/github/repo-size/vitalii-smahlenko/cinema-app)

<p align="center">
<img src="images/Cinema.gif" width="600" height="400">
<p>

Project description
-

### *This is application is based on Spring and Hibernate frameworks. The application allows you to manage the cinema as an administrator or be a user who can purchase a movie ticket. Cinema-app supports authentication and authorization. All CRUD operations are implemented in application*


## User features in this application :

- Register;
- Get information about:
  - cinema-halls;
  - movies; 
  - orders; 
- Find available movie sessions; 
- Complete order.

## Admin features in this application:

- Create: 
  - cinema halls;
  - movie sessions;
- Add movies;
- Update movie sessions;
- Get information about:
  - cinema halls;
  - movies;
  - available movie sessions;
  - user by email;
- Delete movie sessions.

## Instructions for launching the project:

- Fill in all fields of the db.properties file:
  - add MySQL properties;
  - create and connect to the database;
  - configure Hibernate.
- Install Apache Tomcat, I used version 9.0.50
  Apache Tomcat 9.0.50 you can download here https://archive.apache.org/dist/tomcat/tomcat-9/v9.0.50/bin/
- In order to configure logging into the project. You need to specify the path to the directory
  where you want to store logs. 7 lines in the ***src/main/resources/log4j2.xml***

## The application is developed in N-Tier Architecture 

- Presentation tier, controller layer. 
- Logic tier, service layer;
- Data tier, Dao layer;

## Data Base structure of the project:

<p align="center">
<img src="images/DB-structure.png">
<p>

## Technologies

- Java 11.0.12 2021-07-20 LTS
- Apache Maven 3.8.1
- Apache Tomcat 9.0.50
- Apache Log4j 2.17.2
- Hibernate 5.4.27.Final
- MySQL 8
- Spring 5 
  - Core
  - Security
  - Web MVC
