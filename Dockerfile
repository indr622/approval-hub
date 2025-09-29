FROM bellsoft/liberica-openjdk-alpine:21-cds AS builder
WORKDIR /builder
COPY mvnw mvnw
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B
COPY src src
RUN ./mvnw package -DskipTests
RUN mkdir -p target
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM bellsoft/liberica-openjre-alpine:21-cds
WORKDIR /


COPY --from=builder /builder/target/dependency/META-INF /app
COPY --from=builder /builder/target/dependency/BOOT-INF/lib /app/lib
COPY --from=builder /builder/target/dependency/BOOT-INF/classes /app

COPY target/classes/com/apik/crm/entities /app/com/apik/crm/entities

ENTRYPOINT ["java","-cp","app:app/lib/*","app/app.jar"]
