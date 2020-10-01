# LDAP User Manager

This project implements a REST API for the OpenLDAP server.

## Quick Start

Run the following command to build and start the API and the OpenLDAP Server containers:
```
$ docker-compose up -d
```
Load the OrganizationalUnit Users on the server:
```
$ bash load_ldif.sh
```
Now you are good to go and access the API <http://localhost:8080/users>
