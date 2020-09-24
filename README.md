# Test for YellowPepper
* I've used clean architecture for the scaffolding using [hexagonal architecture](https://blog.octo.com/en/hexagonal-architecture-three-principles-and-an-implementation-example/)
* I've used a few design patterns like command pattern, builder , singleton and well spring patterns.


## Technologies
 * Java - **Spring Boot**
 * H2
 * Docker 
 
## Upgrades
* You could use more functional programming as [VAVR](https://www.vavr.io/) and  [immutables](https://immutables.github.io/)
* You could use postman and [newman](https://www.npmjs.com/package/newman) to ensure your e2e
* You must create functional test and property testing could be.
* If you want that the app works with workloads you could deploy in [AWS](https://aws.amazon.com/) and use EC2, ELB and RDS
* If you want metrics you should use an agent for this like new newrelic, dynaTrace , appOptics and so on. This approach just was to show my knowledge with SQL 
 
## How to deploy it?
* `docker-compose  -f docker-compose.yml up -d`
* enjoy :)

 
