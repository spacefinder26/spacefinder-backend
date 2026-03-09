# ================================
# Stage 1: Build
# ================================
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy pom.xml first for dependency caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests -B

# ================================
# Stage 2: Run
# ================================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Add non-root user for security
RUN addgroup -S spacefinder && adduser -S spacefinder -G spacefinder

# Copy built jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

RUN chown spacefinder:spacefinder app.jar

USER spacefinder

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
