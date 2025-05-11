# Boundless Books Backend

## Run with Docker

### How to run the project with postgres database:
```sh
docker-compose build

docker-compose up
```

#### .env file
```DATABASE_PLATFORM=postgresql```

### How to run the project h2 database:
```sh
# Build the app image
docker-compose build app --no-deps

# Run the app container
docker-compose up app --no-deps
```

#### .env file
```DATABASE_PLATFORM=h2```
