# CheckinApi

All URIs are relative to *https://localhost/*

Method | HTTP request | Description
------------- | ------------- | -------------
[**cloneCheckinSession**](CheckinApi.md#cloneCheckinSession) | **POST** /checkinsession/${checkinSessionId} | Clone a checkin session. Useful if more client should have the same setup ie. scan same events and tickettypes
[**createCheckinSession**](CheckinApi.md#createCheckinSession) | **POST** /checkinsession/create | Create checkin session based on organizer api authentication settings.
[**createValidation**](CheckinApi.md#createValidation) | **POST** /validation/create | Create validation for a barcode.
[**deleteCheckinSession**](CheckinApi.md#deleteCheckinSession) | **DELETE** /checkinsession/{checkinSessionId} | Delete checkin session when the checkin session is completed
[**getCheckinSession**](CheckinApi.md#getCheckinSession) | **GET** /checkinsession/{checkinSessionId} | Get details for a checkin session
[**getEvents**](CheckinApi.md#getEvents) | **GET** /checkinsession/{checkinSessionId}/events | Get details of scannable events for checkinsession
[**getOrganizer**](CheckinApi.md#getOrganizer) | **GET** /checkinsession/{checkinSessionId}/organizer | Get organizer related checkinsession
[**getStatus**](CheckinApi.md#getStatus) | **GET** /checkinsession/{checkinSessionId}/status | Get status for a checkinsession with count of tickets and scanned tickets
[**getTicketType**](CheckinApi.md#getTicketType) | **GET** /tickettype/{ticketTypeID} | Get details of tickettype
[**getValidate**](CheckinApi.md#getValidate) | **GET** /validation/{validationId} | Get validation of a barcode
[**ping**](CheckinApi.md#ping) | **GET** /ping | ping
[**searchPurchases**](CheckinApi.md#searchPurchases) | **GET** /checkinsession/${checkinSessionId}/purchases | Search for purchases related to the checkinsession events. Notice this is dependend on running backend system.
[**updateCheckinSession**](CheckinApi.md#updateCheckinSession) | **PUT** /checkinsession/update | Update checkin session to specify the events and tickettypes to scan within the session


<a name="cloneCheckinSession"></a>
# **cloneCheckinSession**
> CheckinSession cloneCheckinSession(checkinSessionId)

Clone a checkin session. Useful if more client should have the same setup ie. scan same events and tickettypes

### Example
```java
// Import classes:
//import io.swagger.client.api.CheckinApi;

CheckinApi apiInstance = new CheckinApi();
String checkinSessionId = "checkinSessionId_example"; // String | checkinSessionId
try {
    CheckinSession result = apiInstance.cloneCheckinSession(checkinSessionId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CheckinApi#cloneCheckinSession");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **checkinSessionId** | **String**| checkinSessionId |

### Return type

[**CheckinSession**](CheckinSession.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createCheckinSession"></a>
# **createCheckinSession**
> CheckinSession createCheckinSession(organizerAuth)

Create checkin session based on organizer api authentication settings.

### Example
```java
// Import classes:
//import io.swagger.client.api.CheckinApi;

CheckinApi apiInstance = new CheckinApi();
OrganizerAuth organizerAuth = new OrganizerAuth(); // OrganizerAuth | organizerAuth
try {
    CheckinSession result = apiInstance.createCheckinSession(organizerAuth);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CheckinApi#createCheckinSession");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **organizerAuth** | [**OrganizerAuth**](OrganizerAuth.md)| organizerAuth |

### Return type

[**CheckinSession**](CheckinSession.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createValidation"></a>
# **createValidation**
> Validation createValidation(createValidation)

Create validation for a barcode.

### Example
```java
// Import classes:
//import io.swagger.client.api.CheckinApi;

CheckinApi apiInstance = new CheckinApi();
CreateValidation createValidation = new CreateValidation(); // CreateValidation | createValidation
try {
    Validation result = apiInstance.createValidation(createValidation);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CheckinApi#createValidation");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **createValidation** | [**CreateValidation**](CreateValidation.md)| createValidation |

### Return type

[**Validation**](Validation.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteCheckinSession"></a>
# **deleteCheckinSession**
> deleteCheckinSession(checkinSessionId)

Delete checkin session when the checkin session is completed

### Example
```java
// Import classes:
//import io.swagger.client.api.CheckinApi;

CheckinApi apiInstance = new CheckinApi();
String checkinSessionId = "checkinSessionId_example"; // String | checkinSessionId
try {
    apiInstance.deleteCheckinSession(checkinSessionId);
} catch (ApiException e) {
    System.err.println("Exception when calling CheckinApi#deleteCheckinSession");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **checkinSessionId** | **String**| checkinSessionId |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getCheckinSession"></a>
# **getCheckinSession**
> CheckinSession getCheckinSession(checkinSessionId)

Get details for a checkin session

### Example
```java
// Import classes:
//import io.swagger.client.api.CheckinApi;

CheckinApi apiInstance = new CheckinApi();
String checkinSessionId = "checkinSessionId_example"; // String | checkinSessionId
try {
    CheckinSession result = apiInstance.getCheckinSession(checkinSessionId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CheckinApi#getCheckinSession");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **checkinSessionId** | **String**| checkinSessionId |

### Return type

[**CheckinSession**](CheckinSession.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getEvents"></a>
# **getEvents**
> List&lt;Event&gt; getEvents(checkinSessionId)

Get details of scannable events for checkinsession

### Example
```java
// Import classes:
//import io.swagger.client.api.CheckinApi;

CheckinApi apiInstance = new CheckinApi();
String checkinSessionId = "checkinSessionId_example"; // String | checkinSessionId
try {
    List<Event> result = apiInstance.getEvents(checkinSessionId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CheckinApi#getEvents");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **checkinSessionId** | **String**| checkinSessionId |

### Return type

[**List&lt;Event&gt;**](Event.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getOrganizer"></a>
# **getOrganizer**
> Organizer getOrganizer(checkinSessionId)

Get organizer related checkinsession

### Example
```java
// Import classes:
//import io.swagger.client.api.CheckinApi;

CheckinApi apiInstance = new CheckinApi();
String checkinSessionId = "checkinSessionId_example"; // String | checkinSessionId
try {
    Organizer result = apiInstance.getOrganizer(checkinSessionId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CheckinApi#getOrganizer");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **checkinSessionId** | **String**| checkinSessionId |

### Return type

[**Organizer**](Organizer.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getStatus"></a>
# **getStatus**
> Status getStatus(checkinSessionId)

Get status for a checkinsession with count of tickets and scanned tickets

### Example
```java
// Import classes:
//import io.swagger.client.api.CheckinApi;

CheckinApi apiInstance = new CheckinApi();
String checkinSessionId = "checkinSessionId_example"; // String | checkinSessionId
try {
    Status result = apiInstance.getStatus(checkinSessionId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CheckinApi#getStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **checkinSessionId** | **String**| checkinSessionId |

### Return type

[**Status**](Status.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getTicketType"></a>
# **getTicketType**
> TicketType getTicketType(ticketTypeID)

Get details of tickettype

### Example
```java
// Import classes:
//import io.swagger.client.api.CheckinApi;

CheckinApi apiInstance = new CheckinApi();
String ticketTypeID = "ticketTypeID_example"; // String | ticketTypeID
try {
    TicketType result = apiInstance.getTicketType(ticketTypeID);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CheckinApi#getTicketType");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ticketTypeID** | **String**| ticketTypeID |

### Return type

[**TicketType**](TicketType.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getValidate"></a>
# **getValidate**
> Validation getValidate(validationId)

Get validation of a barcode

### Example
```java
// Import classes:
//import io.swagger.client.api.CheckinApi;

CheckinApi apiInstance = new CheckinApi();
String validationId = "validationId_example"; // String | validationId
try {
    Validation result = apiInstance.getValidate(validationId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CheckinApi#getValidate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **validationId** | **String**| validationId |

### Return type

[**Validation**](Validation.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="ping"></a>
# **ping**
> PongApi ping()

ping

### Example
```java
// Import classes:
//import io.swagger.client.api.CheckinApi;

CheckinApi apiInstance = new CheckinApi();
try {
    PongApi result = apiInstance.ping();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CheckinApi#ping");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**PongApi**](PongApi.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="searchPurchases"></a>
# **searchPurchases**
> List&lt;Purchase&gt; searchPurchases(checkinSessionId, searchString)

Search for purchases related to the checkinsession events. Notice this is dependend on running backend system.

### Example
```java
// Import classes:
//import io.swagger.client.api.CheckinApi;

CheckinApi apiInstance = new CheckinApi();
String checkinSessionId = "checkinSessionId_example"; // String | checkinSessionId
String searchString = "searchString_example"; // String | searchString
try {
    List<Purchase> result = apiInstance.searchPurchases(checkinSessionId, searchString);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CheckinApi#searchPurchases");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **checkinSessionId** | **String**| checkinSessionId |
 **searchString** | **String**| searchString |

### Return type

[**List&lt;Purchase&gt;**](Purchase.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateCheckinSession"></a>
# **updateCheckinSession**
> updateCheckinSession(checkinSession)

Update checkin session to specify the events and tickettypes to scan within the session

### Example
```java
// Import classes:
//import io.swagger.client.api.CheckinApi;

CheckinApi apiInstance = new CheckinApi();
CheckinSession checkinSession = new CheckinSession(); // CheckinSession | checkinSession
try {
    apiInstance.updateCheckinSession(checkinSession);
} catch (ApiException e) {
    System.err.println("Exception when calling CheckinApi#updateCheckinSession");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **checkinSession** | [**CheckinSession**](CheckinSession.md)| checkinSession |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

