FROM eclipse-temurin:21-jdk AS build

WORKDIR /workspace
COPY . .
RUN chmod +x ./mvnw && ./mvnw --batch-mode --no-transfer-progress verify

FROM eclipse-temurin:21-jre

RUN groupadd --system app && useradd --system --gid app --home-dir /app app
WORKDIR /app
COPY --from=build /workspace/target/draw-it-or-lose-it.jar app.jar

USER app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
