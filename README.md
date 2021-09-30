# Technology Exponent Common Utilities - net - Version 2

* Author Abdul Habra

A library to simplify network programming. Currently, it has email and 
http utilities.

## Quick Documentation


## 1. `com.tek271.util2.net.email` Package
Classes in this package help with sending emails

### 1.1 `Emailer` Class
The main _email_ class. It has properties to configure email's client for sending.

### 1.2 `EmailFormatEnum` Enum
Type of email to be sent: text, text with attachments, or html with attachments.

### 1.3 `EmailUtils` Class
General email related methods.

#### 1.3.1 `String validateEmailAddress(String address)`
Validate that the given address is a syntactically valid email address.
It does not check if the address actually exists.
Returns null/empty if the address is valid, else returns the text of the error message.


## 2. `com.tek271.util2.net.http` Package
Classes to help with http interactions.
Note that some classes names have `Htp` prefix with a single _t_,
this is intentional to help users with importing the right class, when
other libraries have similarly named classes


### 2.1 `HtpClient` Class
Simplify Establishing and sending requests over HTTP or HTTPS, utilizing a
fluent API.

### 2.2 `HtpMediaType` Enum
Different supported media types like text, html, json, ...

### 2.3 `HtpRequest` Class
Encapsulates an HTTP request.

### 2.4 `HtpResponse` Class
Contains the response for an HTTP request.

### 2.5 `HttpTools` Class
#### 2.5.1 `boolean isHttp(String path)`
Check if given path starts with `http` or `https`

#### 2.5.2 `String get(String path)`
Do a simple HTTP get for the given path and return the response as a string

### 2.6 `RequestParams` Class
Abstracts request's parameters

### 2.7 `Url` Class
Parse a url string or convert to a `URL` object.




## Change Log
* 2021.09.29 First public release
