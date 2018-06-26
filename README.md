# Program: Full-Stack implementation of a simple Mail Publisher application 

`Technologies`: Spring Boot, VueJs, SendGrid & MailJet API Integration, Gradle

## Task Description

Implement a complete end-2-end email publisher sample with independent frontent & backend with integration with email publishing providers like SendGrid & MailJet.

With the kind of strategic fallback mechanism available, we can ensure that email that is published to the target recipients are delivered by one of the configured providers. There might be cases if all the providers are down. In such cases, error message is shown for the same.

## Steps to Test the app

* It's ideal to use Unix stack for testing this out. But we can get it working with Windows by tweaking code if any issue arises 
* Clone this repository
* Ensure Java 8 SDK is installed and gradle is downloaded and configured in the system path and working
* Run `gradle build` from the project root folder to build both API & UI Projects
    * This would build UI & API project in sequence and build the final jar file available for execution to test the application 
    * If any issues observed during node modules download for UI project, fixing them would be trivial by running npm or yarn commands in ui folder
* Ensure that api jar is created successfully post gradle build is successfully completed.
* Execute the following command to kickstart the spring boot application which will start the mail publisher example
```
java -jar api-0.0.1-SNAPSHOT.jar --sendgrid.api.key="Bearer <SENDGRID_API_KEY>" --mailjet.api.key.public="<MAILJET_PUBLIC_API_KEY>" --mailjet.api.key.private="<MAILJET_PRIVATE_API_KEY>

Replace Placeholders for SendGrid & MailJet API Keys to test the application
```

* Accessing http://localhost:8080 will open the email publisher UI client application
* UI artifacts are copied to spring boot src/resources/static folder during build process and thus available as single artefact.

## Test Cases
* Publish mail without mandatory fields and verify if appropriate error messages are displayed
* Publish mail with one or more recipients in To, Cc & Bcc fields without subject and email body
* Publish mail with one or more recipients in To, Cc & Bcc fields with valid subject and email body

## TODO's
* Test class implementation by mocking Spring beans using jUnit & Mockito to validate rest interface for all possible test cases
* Unit & e2e test cases for User Interface
* Containerizing the application with Docker
* CI using Jenkins and CD using Terraform on AWS

## Screenshorts

* User Interface of Email Publisher client
![](/assets/ui.png)

* Validatin errors displayed when email form is published without providing mandatory fields
![](/assets/validation-failure.png)

* Success message displayed when email is published successfully
![](/assets/success.png)

* Sample stats retrieved from SendGrid
![](/assets/sendgrid-stats.png)