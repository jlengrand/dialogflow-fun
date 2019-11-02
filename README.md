# DialogFlow fun

[![CircleCI](https://circleci.com/gh/jlengrand/dialogflow-fun/tree/master.svg?style=svg)](https://circleci.com/gh/jlengrand/dialogflow-fun/tree/master)

This repository contains the code used in my "Hey Google, send 20$ to Mum" talk at [JFuture](https://jfuture.dev/) in November 2019.
You can find the slides of this talk in [this other repository](https://github.com/jlengrand/google-send-money-talk).

It is not directly possible for you to just clone this repository and get it working because it depends on environments that I used for the talk. I deploy my app on Google App Engine and some of the credentials are privately stored.
The whole dialogflow agent will also be created live, except for the contact entity. 

## Technology used

* The backend for this talk is built using **Java 11**. I wanted to use **Kotlin** for a while but decided to have some Java fun instead.
* The backend is a simple [Spring Boot](https://spring.io/projects/spring-boot) REST application. Great to start kicking fast.
* The virtual assistant used is Google's [dialogFlow](https://dialogflow.com/).
* The Banking PSD2 demo is built on top of the [OpenBankProject](https://www.openbankproject.com/). They provide a nice 'Bank as a service' API with cool sandboxes, and some are PSD2 compliant.
  
## Some pointers in the code

The project is composed of several modules.

* The agent folder contains a zipped version of the DialogFlow agent I will build live. You should be able to import it.
* The `dialogflow-fun-agent` module contains a few routines that interact with an agent using the DialogFlow API. 
  * The `CreateAgent` class especially creates an agent based on input from a text file.
* The `dialogflow-fun-api` module contains a simple REST API that the DialogFlow agent will interact with. It is the 'backend' of the application.
  * The backend is essentially a proxy between the DialogFlow fulfillment requests on one hand, and the OpenBankProject sandboxes on the other hand.
  * The entry point of the application is `DialogFlowFunApiApplication`. It hosts the controller used for the Agent fulfillment.
  * Most of the PSD2 magic happens in the `OpenBankClient` class. It is a simple class that makes authenticated REST GET and POST calls to the sandbox under the hood.
  * The `DialogFlowService` class is tasked with converting fulfillment requests into fully fledged PSD2 API calls.
  * The `UserAccountLookup` class is a dirty trick used for the demo to link users and bank accounts. This would have to be done differently for an actual app :).

## Useful references (for potential readers of this repo :)

- [Reference guide](https://cloud.google.com/dialogflow/docs/reference/libraries/java)
- [Full library reference](https://googleapis.dev/java/google-cloud-clients/latest/index.html?com/google/cloud/dialogflow/v2/package-summary.html)
- [Maven dependency](https://search.maven.org/artifact/com.google.cloud/google-cloud-dialogflow/0.114.0-alpha/jar)
- [Authentication](https://cloud.google.com/docs/authentication/getting-started)
- [Transaction Types](https://github.com/OpenBankProject/OBP-API/wiki/Transaction-Requests)
 
## Google Resources (for me to remember before the talk)

- Google Cloud project name : [dialogflow-fun](https://console.cloud.google.com/apis/library?project=dialogflow-fun)
- [View logs](https://console.cloud.google.com/logs/viewer?project=dialogflow-fun&minLogLevel=0&expandAll=false&timestamp=2019-10-23T07:50:18.827000000Z&customFacets=&limitCustomFacetWidth=true&dateRangeStart=2019-10-23T06:50:19.080Z&dateRangeEnd=2019-10-23T07:50:19.080Z&interval=PT1H&resource=gae_app%2Fmodule_id%2Fdefault&logName=projects%2Fdialogflow-fun%2Flogs%2Fstdout&logName=projects%2Fdialogflow-fun%2Flogs%2Fstderr&logName=projects%2Fdialogflow-fun%2Flogs%2Fappengine.googleapis.com%252Frequest_log&scrollTimestamp=2019-10-23T07:48:44.904582000Z)
- [Dialog Flow project](https://dialogflow.cloud.google.com/#/agent/ac522b80-e75b-40cd-9493-269fbb4ef634/intents)
- [App Engine Dashboard](https://console.cloud.google.com/appengine?project=dialogflow-fun&serviceId=default&duration=PT6H)
- [The app in the cloud](https://dialogflow-fun.appspot.com/)