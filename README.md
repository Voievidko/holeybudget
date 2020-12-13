<h1 align="center">
  <br>
  notspend
</h1>

<h4 align="center">Design to track family or personal budget</h4>

## Requirements
1. Java 11
2. Maven 
3. MySQL database

## How to build

#### Clone repository

```bash
git clone git@github.com:Voievidko/notspend.git
```

####Add application.properties file
It should be in the folder: `src/main/resources/`.<br>
Example:

```
jdbc.driverClassName = com.mysql.cj.jdbc.Driver
jdbc.url = jdbc:mysql://localhost:3306/notspenddb?createDatabaseIfNotExist=true
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
java -jar path/to/folder/with/war/notspend-1.1.0-SNAPSHOT.war
```
During server start all required tables will automatically create in DB specified in application.properties file
##Reach server
Open your browser and type an URL `localhost:8080` 