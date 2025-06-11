# Boundless Books Backend

## Run with Docker

### How to run the project with postgres database:
```sh
# Build the app image
docker-compose build

# Run the app, postgresql and kafka containers
docker-compose up
```

#### .env file
```DATABASE_PLATFORM=postgresql```

### How to run the project h2 database and no kafka:
```sh
# Build the app image
docker-compose build app

# Run the app container
docker-compose up app --no-deps
```

#### .env file
```DATABASE_PLATFORM=h2```
