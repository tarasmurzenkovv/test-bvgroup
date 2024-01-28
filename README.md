# Getting Started

## Architecture

The app architecture is the layered with the three layers
1. controller -- the RESTfull API
2. services -- contain business logic
3. clients -- data access object

The app itself is stateless meaning that it can be scaled horizontally. 

## Prerequisites
JDK 17 and gradle

## Hot to run app

The app has two profiles -- test and prod. In test profile, app will use test fx client. 
To use this profile run app from console
```
./gradlew -Dspring.profiles.active=test bootRun 
```

If you want to get real data then you need to obtain the API from https://app.freecurrencyapi.com. 
After the step above, set this environment variables
```
FREECURRENCYAPP_API_KEY=key_obtained_from_https://app.freecurrencyapi.com
```
and run app from console 
```
./gradlew -Dspring.profiles.active=prod bootRun 
```

Application will be ready to serve requests under `localhost:8080`
Documentation will be available http://localhost:8080/swagger-ui/index.html

## Hot to build app
./gradlew build

## Hot to test app
./gradlew test

## Hot to launch app
./gradlew test

## What is not considered hear
1. OPs:
   1. Monitoring and logging
   2. Packaging into Docker and deploying to k8s
   3. CI/CD pipeline
2. General application arch concerns:
   1. Retrying on errors from the external source
   2. MB to reactive approach?
   3. What if the external provider is really slow? Redesign service so that it has tree component: request handler,
   queue and request processor. 
   4. dont cache non 201 response
3. Security
4. Testing:
   1. add more tests
   2. add test constraints to build (min 85% coverage)
   3. Load testing?
   