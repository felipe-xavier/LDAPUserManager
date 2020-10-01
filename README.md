# LDAP User Manager

This project implements a Spring Boot REST API for the OpenLDAP server.

## Quick Start

Run the following command to build and to run the API and the OpenLDAP Server containers:
```
$ docker-compose up -d
```
Load the OrganizationalUnit Users on the server:
```
$ bash load_ldif.sh
```
Now you are good to go: <http://localhost:8080/users>
