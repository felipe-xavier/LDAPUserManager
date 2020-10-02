# LDAP User Manager

This project implements a Spring Boot REST API for the OpenLDAP server.

## Quick Start

First, make sure you have docker and docker-compose both installed on your machine and then run the following command. It will build and run the API and the OpenLDAP Server containers:
```
$ docker-compose up -d
```
Then, load the OrganizationalUnit Users on the OpenLDAP server:
```
$ load_ldif.sh
```
If you get an error with a message like "*Can't contact LDAP server (-1)*", please wait a couple of seconds, so the server can get accessible on the container and try again. 

Once succeeded, you are good to go: <http://localhost:8080/users>

Also, you can check out the API Documentation on <http://localhost:8080/swagger-ui/> or just keep scrolling to get a quick reference to the endpoints here.

## API Endpoints

#### Try to add a User through a POST request:
```
POST http://localhost:8080/users
```
Payload:
```json
{ 
   "uid":"foo",
   "cn":"Foo Boo",
   "sn":"boo"
}
```

#### Update your User sending a PUT request:

```
PUT http://localhost:8080/users
```

Payload:

```json
{ 
   "uid":"foo",
   "cn":"Foo von Boo",
   "sn":"boo"
}
```

#### You can get a User through a GET request:
```
GET http://localhost:8080/users/{uid}
```

#### Remove a User requesting DELETE

```
DELETE http://localhost:8080/users/{uid}
```
