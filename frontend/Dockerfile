# Stage 1: build & generate API client
FROM node:18-alpine AS builder
WORKDIR /app
COPY package*.json openapi.yaml ./
RUN apk add --no-cache openjdk21-jre
RUN npm ci
COPY . ./
RUN npm run gen:api
RUN npm run build -- --configuration=production

# Stage 2: serve with Nginx on 4200
FROM nginx:alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf
RUN rm -rf /usr/share/nginx/html/*
COPY --from=builder /app/dist/frontend/browser/ /usr/share/nginx/html/

EXPOSE 4200
CMD ["nginx", "-g", "daemon off;"]
