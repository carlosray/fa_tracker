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
#### [.tools](../.tools) Contains different tools, e.g. we had a lot of IntelliJ .http files to call API's.
#### [apps](../apps) All applications (services).
#### [docs](../docs) Contains all the documentation, internal and external documentation.
#### [libs](../libs) All the common libraries.

## Services
| Service           | Port |
|-------------------|------|
| Api Gateway       | 8001 |
| Auth Service      | 8002 |
| Category Service  | 8003 |
| Group Service     | 8004 |
| Operation Service | 8005 |
| User Service      | 8006 |