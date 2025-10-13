FROM maven:3.9.9-amazoncorretto-17-al2023 AS build

LABEL maintainer="João Pedro <joaoprestupa11@gmail.com>"

WORKDIR /app
COPY . .

# Configurar encoding UTF-8 para o Maven
ENV MAVEN_OPTS="-Dfile.encoding=UTF-8"

RUN mvn clean package -DskipTests

FROM amazoncorretto:17-al2023
WORKDIR /app
COPY --from=build /app/target/biblioteca-api.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
