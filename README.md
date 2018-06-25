# Program: Full-Stack implementation of a simple Mail Publisher application 

“`Technologies“`: Spring Boot, VueJs, SendGrid & MailJet API Integration, Gradle Build

## Task Description

Implement a complete end-2-end email publisher sample with independent frontent & backend with integration with email publishing providers like SendGrid & MailJet.

With the kind of strategic fallback mechanism available, we can ensure that email that is published to the target recipients are delivered by one of the configured providers. There might be cases if all the providers are down. In such cases, error message is shown for the same.

## Steps to Test the app

* It's ideal to use Unix stack for testing this out. But we can get it working with Windows by tweaking code if any issue arises 
* Clone this repository
* Ensure Java 8 SDK is installed and gradle is downloaded and configured in the system path and working
* Run `gradle build` to build both API & UI Projects
    * This would build UI & API project in sequence and build the final jar file available for execution to test the application 
    * If any issues observed for UI project downloading node modules, fixing them would be trivial by running npm or yarn commands in ui folder
* Ensure that api jar is created successfully post gradle build is successfully completed.
* Execute the following command to kickstart the spring boot process which will start the mail publisher example
“`
java -jar api-0.0.1-SNAPSHOT.jar --sendgrid.api.key="Bearer <SENDGRID_API_KEY>" --mailjet.api.key.public="<MAILJET_PUBLIC_API_KEY>" --mailjet.api.key.private="<MAILJET_PRIVATE_API_KEY>"

Replace Placeholders for SendGrid & MailJet API Keys to test the application
“`
* Access http://localhost:8080 to access the email publisher client application

## Test Cases
* Publish mail without mandatory fields and verify if appropriate error messages are displayed
* Publish mail with one or more recipients in To, Cc & Bcc fields without subject and email body
* Publish mail with one or more recipients in To, Cc & Bcc fields with valid subject and email body

## Screenshorts

* User Interface of Email Publisher client
![](/assets/ui.png)

* Validatin errors displayed when email form is published without providing mandatory fields
![](/assets/validation-failure.png)

* Success message displayed when email is published successfully
![](/assets/success.png)

* Sample stats retrieved from 
![](/assets/sendgrid-stats.png)