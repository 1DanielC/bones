#!/usr/bin/env bash
set -euo pipefail

usage() {
    echo "Usage: $0 <project-name>"
    echo ""
    echo "Publishes a Docker image to AWS ECR."
    echo ""
    echo "Arguments:"
    echo "  project-name    The ECR repository name to publish to"
    echo ""
    echo "Environment variables:"
    echo "  AWS_ACCOUNT_ID  AWS account ID (required)"
    echo "  AWS_REGION      AWS region (default: us-east-1)"
    echo "  IMAGE_NAME      Local image name (default: bones)"
    echo "  IMAGE_TAG       Image tag (default: latest)"
    exit 1
}

if [[ $# -lt 1 ]]; then
    usage
fi

PROJECT_NAME="$1"
AWS_ACCOUNT_ID="${AWS_ACCOUNT_ID:?AWS_ACCOUNT_ID environment variable is required}"
AWS_REGION="${AWS_REGION:-us-east-1}"
IMAGE_NAME="${IMAGE_NAME:-bones}"
IMAGE_TAG="${IMAGE_TAG:-latest}"

ECR_REGISTRY="${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
ECR_REPOSITORY="${ECR_REGISTRY}/${PROJECT_NAME}"

echo "Publishing image to ECR"
echo "  Source: ${IMAGE_NAME}:${IMAGE_TAG}"
echo "  Target: ${ECR_REPOSITORY}:${IMAGE_TAG}"

# Note: Using docker-credential-ecr-login helper configured in ~/.docker/config.json
# If not using the credential helper, uncomment the following:
# aws ecr get-login-password --region "${AWS_REGION}" | \
#     docker login --username AWS --password-stdin "${ECR_REGISTRY}"

# Create repository if it doesn't exist
echo "Ensuring repository exists..."
aws ecr describe-repositories --repository-names "${PROJECT_NAME}" --region "${AWS_REGION}" 2>/dev/null || \
    aws ecr create-repository --repository-name "${PROJECT_NAME}" --region "${AWS_REGION}"

# Tag and push the image
echo "Tagging image..."
docker tag "${IMAGE_NAME}:${IMAGE_TAG}" "${ECR_REPOSITORY}:${IMAGE_TAG}"

echo "Pushing image..."
docker push "${ECR_REPOSITORY}:${IMAGE_TAG}"

echo "Successfully published ${ECR_REPOSITORY}:${IMAGE_TAG}"
