FROM node:8-alpine AS builder

COPY . /opt/frontend
WORKDIR /opt/frontend
RUN yarn && yarn build

FROM nginx:alpine

COPY nginx/nginx.conf /etc/nginx/
COPY --from=builder /opt/frontend/dist /opt/frontend
