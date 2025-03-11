FROM openjdk:25-ea-4-jdk-oraclelinux9
WORKDIR /app

COPY target/mini1.jar app.jar

RUN mkdir -p /app/data

COPY src/main/java/com/example/data/*.json /app/data/

ENV USERS_JSON=/app/data/users.json
ENV PRODUCTS_JSON=/app/data/products.json
ENV CARTS_JSON=/app/data/carts.json
ENV ORDERS_JSON=/app/data/orders.json

EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "app.jar"]