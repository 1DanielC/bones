.PHONY: build deploy stop restart help

# Default target
help:
	@echo "Available targets:"
	@echo "  build    - Build the Docker image"
	@echo "  deploy   - Deploy/run the container"
	@echo "  stop     - Stop and remove the container"
	@echo "  restart  - Stop and redeploy the container"

build:
	./scripts/build-image.sh

deploy:
	./scripts/deploy.sh

stop:
	./scripts/stop.sh

restart: stop deploy
