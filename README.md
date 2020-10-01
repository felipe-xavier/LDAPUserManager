# LDAP User Manager

This project implements a Spring Boot REST API for the OpenLDAP server.

## Quick Start

With docker and docker-compose both installed on your machine, run the following command. It will build and run the API and the OpenLDAP Server containers as well as load the OrganizationalUnit Users on the server:
```
$ bash start.sh
```

Once succeeded, you are good to go: <http://localhost:8080/users>

## API Endpoints

### 
Try to add a User through a POST request:
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

Update your User sending a PUT request:

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

Remove a User requesting DELETE


```json
{ 
   "uid":"foo",
   "cn":"Foo von Boo",
   "sn":"boo"
}
```