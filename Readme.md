# Proxy Application Example

## Container image building and pushing
Creating a container and publishing to Container Registry (CR) with maven command line example: 
```
export IMAGE_NAME=  && \
export PUBLISH=true && \
export REPO_PWD=xxxxxx && \
export REPO_URL=xxxxxx && \
export REPO_USERNAME=xxxxx && \
mvn spring-boot:build-image -DskipTests
```
We can use the same logic to push to any other CR. However, if the CR uses token authentication (like Github) we have to change the param inside the ``pom.xml``.

## Redis Docker Image
You can run a redis image locally, easily:
```
$ docker run -d -p6379:6379 --name redis redis:latest 
```

## Running application without Redis
```
$ mvn spring-boot:run -DskipTests -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=no-redis"
```

