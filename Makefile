.PHONY: build deploy stop restart lint lint-fix help

# Default target
help:
	@echo "Available targets:"
	@echo "  build    - Build the Docker image"
	@echo "  deploy   - Deploy/run the container"
	@echo "  stop     - Stop and remove the container"
	@echo "  restart  - Stop and redeploy the container"
	@echo "  lint     - Run ktlint to check code style"
	@echo "  lint-fix - Run ktlint and auto-fix issues"

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
