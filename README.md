Author : Tapan Paul
Written using Java 11 and Spring Boot

API exposes Rest interface with the following methods :

/register-vehicle
/unregister-vehicle
/get-vehicles ( used for testing and asserting the above methods)
/add-vehicle
/add-person

Design using MVC pattern

Build :
gradle clean build

Unit Tests
Added tests for each of the exposed interfaces on the rest controller

3rd party libs
lombok
reactive streams

Future scope -
add swagger for api documentation
add security for api - eg spring
testing can add mocks (mockito)