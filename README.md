Execute this commands in those projects:
1 - Create container guest-spring-security-jwt
```
docker build --tag guest-spring-security-jwt .
```
2 - Run all containeres
```
docker-compose up -d
```
or
```
docker-compose up -d --build
```

3 - Access URL to create table [PGAdmin](http://localhost:15432/browser/)
Click on Register
On General write a name
Select Connection

|Field|Value|
|-----:|-----------|
|Host name/address:| postgresdb|
|Port:| 5432|
|Maintenance database:| guests|
|Username:| postgres|
|Password:| postgres|

Backup connection
```
docker cp pgadmin:/var/lib/pgadmin/pgadmin4.db ./pgadmin4-backup.db
```
Restore backup
```
docker cp ./pgadmin4-backup.db pgadmin:/var/lib/pgadmin/pgadmin4.db
```
4 - Create a user
```
curl --location 'http://localhost:8080/users/register' \
--header 'Content-Type: application/json' \
--data '{
    "username": "user1",
    "password": "u123456"
}'
```
5 - Login
```
curl --location 'http://localhost:8080/users/login' \
--header 'Content-Type: application/json' \
--data '{
    "username": "user1",
    "password": "u123456"
}'
```
6 - Create a user by Postman
```
curl --location 'http://localhost:8080/guests' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer [TOKEN CREATED IN STEP 5]' \
--data '{
    "legalEntityNumber": "97771595014",
    "name": "Guest 97771595014",
    "birthday": "2026-04-05"
}'
```
7 - Find Guest by legal entity number
```
curl --location 'http://localhost:8080/guests/97771595014' \
--header 'Authorization: Bearer [TOKEN CREATED IN STEP 5]'
```
8 - Update Guest
```
curl --location --request PUT 'http://localhost:8080/guests/97771595014' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer [TOKEN CREATED IN STEP 5]' \
--data '{
    "name": "First Guest proxy 97771595014",
    "birthday": "2020-01-07"
}'
```
9 - Delete Guest
```
curl --location --request DELETE 'http://localhost:8080/guests/97771595014' \
--header 'Authorization: Bearer [TOKEN CREATED IN STEP 5]'
```