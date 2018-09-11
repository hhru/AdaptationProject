FROM node:8-alpine AS builder

ARG ADAPT_BUILD_MODE

COPY . /opt/frontend
WORKDIR /opt/frontend
RUN yarn && yarn build

FROM nginx:alpine

COPY nginx/nginx.conf /etc/nginx/
COPY --from=builder /opt/frontend/dist /opt/frontend
