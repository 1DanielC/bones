#!/usr/bin/env bash
set -euo pipefail

IMAGE_NAME="${IMAGE_NAME:-bones}"
IMAGE_TAG="${IMAGE_TAG:-latest}"
CONTAINER_NAME="${CONTAINER_NAME:-bones}"
HOST_PORT="${HOST_PORT:-26637}"
CONTAINER_PORT="26637"

# Database connection (defaults for local development)
DB_URL="${DB_URL:-jdbc:postgresql://host.docker.internal:5432/bones}"
DB_USER="${DB_USER:-bones}"
DB_PASSWORD="${DB_PASSWORD:-bones}"

# Stop and remove existing container if running
if docker ps -a --format '{{.Names}}' | grep -q "^${CONTAINER_NAME}$"; then
    echo "Stopping existing container: ${CONTAINER_NAME}"
    docker stop "${CONTAINER_NAME}" 2>/dev/null || true
    docker rm "${CONTAINER_NAME}" 2>/dev/null || true
fi

echo "Starting container: ${CONTAINER_NAME}"
docker run -d \
    --name "${CONTAINER_NAME}" \
    -p "${HOST_PORT}:${CONTAINER_PORT}" \
    -e SPRING_DATASOURCE_URL="${DB_URL}" \
    -e SPRING_DATASOURCE_USERNAME="${DB_USER}" \
    -e SPRING_DATASOURCE_PASSWORD="${DB_PASSWORD}" \
    "${IMAGE_NAME}:${IMAGE_TAG}"

echo "Container started successfully"
echo "  Name: ${CONTAINER_NAME}"
echo "  Port: ${HOST_PORT}"
echo "  URL:  http://localhost:${HOST_PORT}"
