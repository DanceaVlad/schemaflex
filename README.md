# SchemaFlex

SchemaFlex is a demo of dynamically generating and validating forms based on JSON-schemas. Under the hood it uses Angular + JsonForms for the frontend and Spring for the backend.

## Getting Started

Follow the instructions below to set up and run the application.

### Running in Production

To run the application in production mode, use the following command:

```bash
docker-compose --profile prod up --build
```

Navigate to http://localhost/80 to access the demo.
The backend lies under http://localhost/8080.

### Running in Development

To run the application in development mode, use the following command:

```bash
docker-compose --profile dev up --build
```

Navigate to http://localhost/4200 to access the demo.
The backend lies under http://localhost/8080.
Port 5005 is used for debugging.

When running the application with the dev profile, the backend is configured to wait for a debugger to connect before starting. This allows for interactive debugging during development.

#### VSCode Debugging Setup

To connect VSCode's debugger to the Spring Boot application:

1. Make sure the application is running with the dev profile
2. Use the following launch configuration (in [backend/.vscode/launch.json](backend/.vscode/launch.json)):

```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "name": "Attach to Spring Boot",
            "type": "java",
            "request": "attach",
            "hostName": "localhost",
            "port": 5005,
            "projectName": "schemaflex"
        }
    ]
}
```

To connect VSCode's debugger to the Angular application:

1. Make sure the application is running with the dev profile
2. Open Chrome with this command:

```bash
open -a "Google Chrome" --args --remote-debugging-port=9222 http://localhost:4200
```

3. Use the following launch configuration (in [frontend/.vscode/launch.json](fontend/.vscode/launch.json)):

```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "name": "Attach to Angular in Docker",
            "type": "chrome",
            "request": "attach",
            "url": "http://localhost:4200",
            "port": 9222,

            "webRoot": "${workspaceFolder}",

            "sourceMaps": true,
            "sourceMapPathOverrides": {
                "webpack:///src/*": "${webRoot}/src/*",
                "webpack:///./*": "${webRoot}/*",
                "webpack:///*": "${webRoot}/*"
            }
        }
    ]
}

```

## Requirements

- [Docker Desktop](https://www.docker.com/)

## License

This project is licensed under the MIT License.
