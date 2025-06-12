# SchemaFlex

SchemaFlex is a demo of dynamically generating and validating forms based on JSON-schemas. Under the hood it uses Angular + JsonForms for the frontend and Spring for the backend. To maintain consistency in models and services + endpoints it uses OpenAPI to automatically generate these files.

## Getting Started

Follow the instructions below to set up and run the application.

### Cloning the repository

Use the following command to clone the repository:

```bash
git clone https://github.com/DanceaVlad/schemaflex.git
```

### Running the project

To run the application navigate in the terminal to /schemaflex and use the following command:

```bash
docker-compose up
```

Navigate to http://localhost/4200 to access the demo.
The backend lies under http://localhost/8080.

## Dev experience

Upon changing the openapi.yml file in one of the 2 parts, the change needs to be reflected into the other one.

### Backend

The easiest way to apply the change is to delete the target directory and to run:

```bash
mvn clean install
```

### Frontend

Run the following command before serving:

```bash
npm run gen:api
```

### VSCode Debugging Setup

#### Backend

To connect VSCode's debugger to the Spring Boot application use the following launch.json in the /backend directory:

```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Application",
            "request": "launch",
            "mainClass": "com.dancea.schemaflex.Application",
            "projectName": "schemaflex"
        },
    ]
}
```

#### Frontend

To connect VSCode's debugger to the Angular application use the following launch.json in the /frontend directory:

```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "name": "ng serve",
            "type": "chrome",
            "request": "launch",
            "preLaunchTask": "npm: start",
            "url": "http://localhost:4200/"
        }
    ]
}
```

## Requirements

- [Docker Desktop](https://www.docker.com/)

## License

This project is licensed under the MIT License.
