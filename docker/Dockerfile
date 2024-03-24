# -----------------------------------------------------------------------------
# BUILD STAGE
# -----------------------------------------------------------------------------
FROM maven:3.9.6 as build

# Create app directory
WORKDIR /usr/src/app

# Resolve and cache dependencies
COPY ./pom.xml .
RUN mvn dependency:go-offline

# Copy and build
COPY . .
RUN mvn clean package -DskipTests

# -----------------------------------------------------------------------------
# DEPLOYMENT STAGE
# -----------------------------------------------------------------------------
FROM eclipse-temurin:21.0.2_13-jre-alpine as deployment

WORKDIR /app

# Create user
RUN addgroup -S runners
RUN adduser -S runner -G runners

# Copy from build stage
COPY --from=build ./usr/src/app/target/*.war ./app.war

# Add logs folder
RUN touch app.log && \
    mkdir ./logs && \
    chown runner app.log && \
    chown runner ./logs
VOLUME ./logs

# Exposed ports
EXPOSE 8080
EXPOSE 8000

# Health check
HEALTHCHECK --interval=30s --retries=5 --timeout=10s CMD curl --fail --silent localhost:8080/actuator/health | grep UP || exit 1

# Change user
USER runner

# Run with remote debugging
CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:8000", "-jar", "app.war"]