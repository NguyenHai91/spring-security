
FROM amazoncorretto:17-alpine3.20-full

# copy source code from local to docker
WORKDIR /app
COPY pom.xml .
COPY src ./src

# build source code with maven on docker
RUN mvn package -DskipTests

# command to run the applicaiton
ENTRYPOINT ["java", "-jar", "./target/*.jar"]