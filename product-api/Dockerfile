FROM maven:3.8.4-jdk-11

WORKDIR /usr/app

COPY . /usr/app

RUN mvn clean install -D skipTests

EXPOSE 3001

CMD ["mvn", "spring-boot:run"]