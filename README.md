---
modified: 2021-12-16T12:35:33.331Z
title: spring-security-oauth2-salesforce
---

# spring-security-oauth2-salesforce

Spring Boot Security OAuth Authorisation Code Flow (Web Server) with Salesforce using Reactive Stack. If you are looking for same OAuth implementation using Servlet Stack, then check this repo. https://github.com/gouravsood/spring-security-oauth2-salesforce-servlet

Steps to run this project

1. Create a Connected App in Salesforce and copy the Consumer Key and Consumer Secret
2. In application.yml file (src/main/resources/application.yml)
   1. replace `client-id` with Consumer Key from Connected App
   2. replace `client-secret` with Consumer Secret from Connected App
3. Run `mvn clean install`
4. Start the server with `mvn spring-boot:run`
5. Go to URL http://localhost:8080/accounts access the protected Accounts records from Salesforce.
