.PHONY: build deploy stop restart lint lint-fix publish help

# AWS defaults (use AWS_PROFILE=os-dev)
AWS_ACCOUNT_ID ?= 686640301001
AWS_REGION ?= us-west-2
PROJECT_NAME ?= bones

# Default target
help:
	@echo "Available targets:"
	@echo "  build    - Build the Docker image"
	@echo "  deploy   - Deploy/run the container"
	@echo "  stop     - Stop and remove the container"
	@echo "  restart  - Stop and redeploy the container"
	@echo "  lint     - Run ktlint to check code style"
	@echo "  lint-fix - Run ktlint and auto-fix issues"
	@echo "  publish  - Publish image to ECR (default: bones)"

build:
	./scripts/build-image.sh

deploy:
	./scripts/deploy.sh

stop:
	./scripts/stop.sh

restart: stop deploy

lint:
	./gradlew ktlintCheck

lint-fix:
	./gradlew ktlintFormat

publish:
	AWS_ACCOUNT_ID=$(AWS_ACCOUNT_ID) AWS_REGION=$(AWS_REGION) ./scripts/publish.sh $(PROJECT_NAME)
