# ── Stage 1: 빌드 ────────────────────────────────────────────
FROM --platform=$BUILDPLATFORM eclipse-temurin:25-jdk-alpine AS builder

WORKDIR /workspace

COPY . .

RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew :app:bootJar --no-daemon -x test --parallel \
    && java -Djarmode=layertools \
         -jar app/build/libs/app.jar \
         extract --destination /workspace/extracted


# ── Stage 2: 로컬 개발용 ────────────────────────────────────
FROM eclipse-temurin:25-jre-alpine AS development

WORKDIR /app

COPY --from=builder /workspace/extracted/dependencies/          ./
COPY --from=builder /workspace/extracted/spring-boot-loader/    ./
COPY --from=builder /workspace/extracted/snapshot-dependencies/ ./
COPY --from=builder /workspace/extracted/application/           ./

EXPOSE 8080
EXPOSE 5005

ENTRYPOINT ["java", \
    "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", \
    "org.springframework.boot.loader.launch.JarLauncher"]


# ── Stage 3: 운영용 ─────────────────────────────────────────
FROM --platform=$TARGETPLATFORM eclipse-temurin:25-jre-alpine AS production

WORKDIR /app

RUN apk add --no-cache curl \
    && addgroup -S appgroup \
    && adduser  -S appuser -G appgroup

COPY --from=builder --chown=appuser:appgroup /workspace/extracted/dependencies/          ./
COPY --from=builder --chown=appuser:appgroup /workspace/extracted/spring-boot-loader/    ./
COPY --from=builder --chown=appuser:appgroup /workspace/extracted/snapshot-dependencies/ ./
COPY --from=builder --chown=appuser:appgroup /workspace/extracted/application/           ./

USER appuser

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=production

HEALTHCHECK \
    --interval=15s \
    --timeout=5s \
    --start-period=40s \
    --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", \
    "-XX:+UseZGC", \
    "-XX:MaxRAMPercentage=75.0", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", \
    "org.springframework.boot.loader.launch.JarLauncher"]
