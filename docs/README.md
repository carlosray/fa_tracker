## Project
#### Finance Tracker application.
Simple graphs and financial overviews provide actionable insights into the state of your finances, including accounts, credit and debit cards, debts, and cash. Arrange your bills and keep track of their due dates. Examine upcoming payments and how they will affect your cash flow.
## Stack
- Java / Kotlin
- Spring Cloud (Microservice Architecture)
- Reactive stack (Project Reactor)
- PostgreSQL
- MongoDB
- Kafka
- Liquibase Migrations
- Kubernetes Support

## Structure
#### [.github](../.github) Contains the workflows for GitHub Action and a file for dependabot.
#### [.helm](../.helm) Helm charts for k8s deploy.
#### [.tools](../.tools) Contains different tools, e.g. .http files to call API's or docker-compose with dev stubs
#### [apps](../apps) All applications (services).
#### [docs](../docs) Contains all the documentation, internal and external documentation.
#### [libs](../libs) All the common libraries.

## Services
| Service                                        | Dev Port | CI |
|------------------------------------------------|----------|-------|
| [Api Gateway](../apps/api-gateway)             | 8001     |![api-gateway-ci](https://github.com/carlosray/fa_tracker/actions/workflows/app-api-gateway.yaml/badge.svg)
| [Auth Service](../apps/auth-service)           | 8002     |![auth-service-ci](https://github.com/carlosray/fa_tracker/actions/workflows/app-auth-service.yaml/badge.svg)
| [Category Service](../apps/category-service)   | 8003     |![category-service-ci](https://github.com/carlosray/fa_tracker/actions/workflows/app-category-service.yaml/badge.svg)
| [Group Service](../apps/group-service)         | 8004     |![group-service-ci](https://github.com/carlosray/fa_tracker/actions/workflows/app-group-service.yaml/badge.svg)
| [Operation Service](../apps/operation-service) | 8005     |![operation-service-ci](https://github.com/carlosray/fa_tracker/actions/workflows/app-operation-service.yaml/badge.svg)
| [User Service](../apps/user-service)           | 8006     |![user-service-ci](https://github.com/carlosray/fa_tracker/actions/workflows/app-user-service.yaml/badge.svg)
| [Account Service](../apps/account-service)     | 8007     |![account-service-ci](https://github.com/carlosray/fa_tracker/actions/workflows/app-account-service.yaml/badge.svg)
| [Balance Service](../apps/balance-service)     | 8008     |![balance-service-ci](https://github.com/carlosray/fa_tracker/actions/workflows/app-balance-service.yaml/badge.svg)
