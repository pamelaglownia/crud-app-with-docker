# Etap 1: Budowanie aplikacji
FROM maven:3.9.9-amazoncorretto-21 AS build
WORKDIR /app

# Kopiowanie pliku pom.xml i pobranie zależności
COPY pom.xml .
RUN mvn dependency:go-offline

# Kopiowanie całego kodu źródłowego i budowanie aplikacji
COPY src ./src
RUN mvn clean package -DskipTests

# Etap 2: Uruchamianie aplikacji
FROM openjdk:21-jdk
WORKDIR /app

# Kopiowanie zbudowanego pliku JAR z etapu budowania
COPY --from=build /app/target/crudCompanyApp-0.0.1-SNAPSHOT.jar company-service.jar

# Otwieranie portu 8080, na którym działa aplikacja
EXPOSE 8080

# Uruchamianie aplikacji
ENTRYPOINT ["java", "-jar", "company-service.jar"]
