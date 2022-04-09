# Multi-tenant sample

Application with multi databases, determine which database 
use with username. Username given by Spring Security authentication.

# Technologies

 - Java 11
 - Spring-boot
 - Docker, [install guide](https://docs.docker.com/engine/install/)
 - MySql [dockerized](https://hub.docker.com/_/mysql/)

# Some information

### UserDetailsServiceImpl

Used for http basic authentication, initialize users and provide user by name
when asked by spring security.

### DataSourceProperties

Get properties ```tenants``` from spring, and initialize the DataSource objects.

### CurrentTenantIdentifierResolverImpl

Determines which tenant id to use, given by spring security

### DataSourceBasedMultiTenantConnectionProviderImpl

Determines which data source to use with a tenant id.

### TenantDatabaseConfig

All configurations for data source, entities and repositories.

### MultiTenantUser

Extend User.class (used by spring security) to add a tenant property.

# Run application

### Step 1: Run MySql instances
Inside docker/mysql run ```sh start-mysql-instances.sh``` or ```./start-mysql-instances.sh```

To stop: ```sh stop-mysql-instances.sh``` or ```./stop-mysql-instances.sh```

### Step 2: Compile api module
Inside api module (folder)

```
../mvnw clean install 
```

### Step 3: Run application
Inside impl module (folder)

```
../mvnw clean spring-boot:run
```

# Compile application

### Step 1: Run MySql instances
Inside docker/mysql run ```sh start-mysql-instances.sh``` or ```./start-mysql-instances.sh```

To stop: ```sh stop-mysql-instances.sh``` or ```./stop-mysql-instances.sh```

### Step 2: Compile api module
Inside api module (folder)

```
../mvnw clean install 
```

### Step 3: Compile
Inside impl module (folder)

```
../mvnw clean package spring-boot:repackage
```

### Step 4: Run application

```
java -jar target/multitenant-mysql-impl-0.0.1-SNAPSHOT.jar
```

# Test application with curl

```
Tenant 1 (ds1): curl -u donald:pw http://localhost:8080/user

Tenant 2 (ds2): curl -u ralph:pw http://localhost:8080/user
```

# Example project links

 - [Postgre Jdbc template sample](https://www.youtube.com/watch?v=hjJ6ODxgYqE)
 - [Mysql spring JPA](https://turkogluc.com/database-based-multitenant-applications-with-spring-jpa/) - [Github sample](https://github.com/amran-bd/Dynamic-Multi-Tenancy-Using-Java-Spring-Boot-Security-JWT-Rest-API-MySQL-Postgresql-full-example)