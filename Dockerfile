
FROM eclipse-temurin:21-jdk AS build

RUN apt-get update && apt-get install -y maven


WORKDIR /app


COPY pom.xml .
RUN mvn dependency:go-offline


COPY src ./src

RUN mvn package -DskipTests


FROM eclipse-temurin:21-jre


WORKDIR /app


COPY --from=build /app/target/*.jar app.jar


EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]