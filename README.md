# JSON Form Code Generator

This project is a Spring Boot application that serves as a code generator for dynamically producing Angular components and Spring Boot microservice code based on JSON Schemas. The generator takes JSON Schema inputs (for example, the RDA DMP Common Standard) and generates fully functional code ready for integration into larger projects.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)

## Overview

The **JSON Form Code Generator** aims to automate the creation of code for document management systems by generating:
- **Angular Components:** Dynamically generated forms using Angular Material, which reflect the structure and validations defined in the JSON Schema.
- **Spring Boot Microservices:** REST API endpoints, entities, controllers, and services generated to correspond with the schema.
- **OpenAPI Specification:** An automatically generated OpenAPI spec that documents the endpoints, which can be used for auto-generation of client code and interactive API documentation.

This schema-driven approach drastically reduces manual coding and ensures consistency in integrating new document types into larger systems.

## Features

- **Dynamic Schema Handling:** Reads and processes any valid JSON Schema, following the JSON Schema standard.
- **Code Generation:** Produces Angular (TypeScript) components and Spring Boot (Java) code dynamically.
- **Validation:** Implements validations on both the client-side and the server-side, aligned with schema constraints.
- **Template-Driven Generation:** Utilizes FreeMarker templates for flexible and maintainable code generation.
- **OpenAPI Integration:** Generates a complete OpenAPI spec to describe the backend APIs.
- **Docker Support:** Ready to be containerized for consistent deployments across environments.

## Technology Stack

- **Java & Spring Boot:** Backend REST API and business logic.
- **FreeMarker:** Templating engine for generating code files.
- **Angular:** (Generated) for building dynamic forms.
- **OpenAPI:** For API documentation and client code generation.
- **Maven:** For project build and dependency management.
- **Docker:** (Optional) Containerization to ensure consistent runtime environments.
