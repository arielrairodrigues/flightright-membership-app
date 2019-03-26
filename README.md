# FlightRight Membership API Challenge

The project provides a RESTful API to manage the CRUD operations of members.

For the backend an embedded HSQL Database is used as in-memory application. There is no data initialized.

## How to run it

This is a multi-layer Maven project built using **Spring Boot**.

To build the project just execute maven `mvn clean package && mvn spring-boot:run -pl flightRightRest` commands from the project root, with this you'll get a generated war file on target folder.

The project will be automatically exposed to the part `http://localhost:8080/`

To view the swagger documentation, visit `http://localhost:8080/swagger-ui.html`

## RESTful API

The API provides the following interfaces and can be tested using tools like PostMan

### Create Member
**POST http://localhost:8080/members/create**

Request:
```
Headers:
Accept: application/json (or application/xml)
Body (Note that the body must be form data because of the file upload)

### Parameter Names
> picture
> dateOfBirth
> firstName
> lastName
> postalCode

