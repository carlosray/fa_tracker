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
| Service                                        | Dev Port |
|------------------------------------------------|----------|
| [Api Gateway](../apps/api-gateway)             | 8001     |
| [Auth Service](../apps/auth-service)           | 8002     |
| [Category Service](../apps/category-service)   | 8003     |
| [Group Service](../apps/group-service)         | 8004     |
| [Operation Service](../apps/operation-service) | 8005     |
| [User Service](../apps/user-service)           | 8006     |
| [Account Service](../apps/account-service)     | 8007     |
| [Balance Service](../apps/balance-service)     | 8008     |