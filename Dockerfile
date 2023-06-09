FROM maven:3.6.3-openjdk-17

RUN mkdir job4j_cars

WORKDIR job4j_cars

COPY . .

RUN mvn package -Dmaven.test.skip=true

CMD ["mvn", "liquibase:update", "-Pdocker"]

CMD ["java", "-jar", "target/cars.jar"]