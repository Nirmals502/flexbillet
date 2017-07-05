# swagger-android-client

## Requirements

Building the API client library requires [Maven](https://maven.apache.org/) to be installed.

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn deploy
```

Refer to the [official documentation](https://maven.apache.org/plugins/maven-deploy-plugin/usage.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
    <groupId>io.swagger</groupId>
    <artifactId>swagger-android-client</artifactId>
    <version>1.0.0</version>
    <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "io.swagger:swagger-android-client:1.0.0"
```

### Others

At first generate the JAR by executing:

    mvn package

Then manually install the following JARs:

* target/swagger-android-client-1.0.0.jar
* target/lib/*.jar

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

import io.swagger.client.api.CheckinApi;

public class CheckinApiExample {

    public static void main(String[] args) {
        CheckinApi apiInstance = new CheckinApi();
        String checkinSessionId = "checkinSessionId_example"; // String | checkinSessionId
        try {
            CheckinSession result = apiInstance.cloneCheckinSession(checkinSessionId);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling CheckinApi#cloneCheckinSession");
            e.printStackTrace();
        }
    }
}

```

## Documentation for API Endpoints

All URIs are relative to *https://localhost/*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*CheckinApi* | [**cloneCheckinSession**](docs/CheckinApi.md#cloneCheckinSession) | **POST** /checkinsession/${checkinSessionId} | Clone a checkin session. Useful if more client should have the same setup ie. scan same events and tickettypes
*CheckinApi* | [**createCheckinSession**](docs/CheckinApi.md#createCheckinSession) | **POST** /checkinsession/create | Create checkin session based on organizer api authentication settings.
*CheckinApi* | [**createValidation**](docs/CheckinApi.md#createValidation) | **POST** /validation/create | Create validation for a barcode.
*CheckinApi* | [**deleteCheckinSession**](docs/CheckinApi.md#deleteCheckinSession) | **DELETE** /checkinsession/{checkinSessionId} | Delete checkin session when the checkin session is completed
*CheckinApi* | [**getCheckinSession**](docs/CheckinApi.md#getCheckinSession) | **GET** /checkinsession/{checkinSessionId} | Get details for a checkin session
*CheckinApi* | [**getEvents**](docs/CheckinApi.md#getEvents) | **GET** /checkinsession/{checkinSessionId}/events | Get details of scannable events for checkinsession
*CheckinApi* | [**getOrganizer**](docs/CheckinApi.md#getOrganizer) | **GET** /checkinsession/{checkinSessionId}/organizer | Get organizer related checkinsession
*CheckinApi* | [**getStatus**](docs/CheckinApi.md#getStatus) | **GET** /checkinsession/{checkinSessionId}/status | Get status for a checkinsession with count of tickets and scanned tickets
*CheckinApi* | [**getTicketType**](docs/CheckinApi.md#getTicketType) | **GET** /tickettype/{ticketTypeID} | Get details of tickettype
*CheckinApi* | [**getValidate**](docs/CheckinApi.md#getValidate) | **GET** /validation/{validationId} | Get validation of a barcode
*CheckinApi* | [**ping**](docs/CheckinApi.md#ping) | **GET** /ping | ping
*CheckinApi* | [**searchPurchases**](docs/CheckinApi.md#searchPurchases) | **GET** /checkinsession/${checkinSessionId}/purchases | Search for purchases related to the checkinsession events. Notice this is dependend on running backend system.
*CheckinApi* | [**updateCheckinSession**](docs/CheckinApi.md#updateCheckinSession) | **PUT** /checkinsession/update | Update checkin session to specify the events and tickettypes to scan within the session


## Documentation for Models

 - [CheckinSession](docs/CheckinSession.md)
 - [CreateValidation](docs/CreateValidation.md)
 - [Event](docs/Event.md)
 - [Href](docs/Href.md)
 - [IdAndHref](docs/IdAndHref.md)
 - [Organizer](docs/Organizer.md)
 - [OrganizerAuth](docs/OrganizerAuth.md)
 - [PongApi](docs/PongApi.md)
 - [Purchase](docs/Purchase.md)
 - [ScannableBarcode](docs/ScannableBarcode.md)
 - [ScannableBarcodesGroup](docs/ScannableBarcodesGroup.md)
 - [Status](docs/Status.md)
 - [TicketType](docs/TicketType.md)
 - [Validation](docs/Validation.md)


## Documentation for Authorization

All endpoints do not require authorization.
Authentication schemes defined for the API:

## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author



