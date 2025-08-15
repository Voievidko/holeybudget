# holeybudget
Design to track family or personal budget

## Requirements
1. Java 11
2. Maven 
3. MySQL database

## How to build

#### Clone repository

```bash
git clone git@github.com:Voievidko/holeybudget.git
```

#### Add application.properties file
It should be in the folder: `src/main/resources/`.
Example:

```
jdbc.driverClassName = com.mysql.cj.jdbc.Driver
jdbc.url = jdbc:mysql://localhost:3306/holeybudgetdb?createDatabaseIfNotExist=true
jdbc.username = username
jdbc.password = passwordToDB
hibernate.connectionPoolSize = 5
hibernate.dialect = org.hibernate.dialect.MySQLDialect
hibernate.showSQL = true
hibernate.formatSQL = false
spring.liquibase.change-log=classpath:liquibase/db.changelog.xml
```

#### Run maven

```bash
mvn clean install
```

war file will appear in your local .m2 folder.

## Run server
```
java -jar path/to/folder/with/war/holeybudget-1.1.0-SNAPSHOT.war
```
During server start all required tables will automatically create in DB specified in application.properties file

## Launch MySQL database in docker container

You may run MySQL database container in following way:
```
sudo docker run --name holeybudgetdb -p 3306:3306 -e MYSQL_ROOT_PASSWORD=passwordToDb -e MYSQL_DATABASE=holeybudgetdb -d mysql
```
where

`--name holeybudgetdb` - docker container name

`-p 3306:3306` - port mapping in the form port_on_host_machine:port_in_docker_container

`-e MYSQL_ROOT_PASSWORD=passwordToDb` - password for root database user

`-e MYSQL_DATABASE=holeybudgetdb` - database name

Make sure that port, password and database name here are the same as in _application.properties_

You may additionally create a new database user during container startup using following options

```
-e MYSQL_USER=new_user -e MYSQL_PASSWORD=new_user_password
```

Find more information here https://hub.docker.com/_/mysql

## Launch MySQL client in docker container

Find out MySQL database IP in docker network (bridge network is used by default)

```
sudo docker inspect holeybudgetdb
```

Launch MySQL client using IP obtained on prev step (here, 172.17.0.2)

```
sudo docker run -it --network bridge --rm mysql mysql -h 172.17.0.2 -uroot -p
```

## Reach server
Open your browser and type an URL `localhost:8080` 

## Build and deploy docker image
```
mvn package
```
Start docker daemon
```
docker build --platform=linux/amd64 -t voievidko/holeybudget .
```
Login to docker app with master creds
```
docker image tag voievidko/holeybudget voievidko/holeybudget:v1.4.0
docker image tag voievidko/holeybudget voievidko/holeybudget:latest
docker push voievidko/holeybudget:v1.4.0
docker push voievidko/holeybudget:latest
```