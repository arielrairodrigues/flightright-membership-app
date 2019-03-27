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

You can click here to see various screenshots for the RESTful API methods

### Create Member
**POST http://localhost:8080/members/create**

Request:
```
Headers:
Accept: application/json (or application/xml)
Body (Note that the body must be form data because of the file upload)

### Parameter Names
> picture (file)
> dateOfBirth
> firstName
> lastName
> postalCode
```

Create Member Response:
```
Code: 200
Body: {"responseCode":"00","responseMessage":"Successful"}
```

### Update Member
**POST http://localhost:8080/members/update/{memberId}**

Request:
```
Headers:
Accept: application/json (or application/xml)
Body (Note that the body must be form data because of the file upload)

### Parameter Names
> picture (file)
> dateOfBirth
> firstName
> lastName
> postalCode
```

Update Member Response:
```
Code: 200
Body: {"responseCode":"00","responseMessage":"Successful"}
```

### Get Member
**POST http://localhost:8080/members/get/{memberId}**

Request:
```
Headers:
Accept: application/json (or application/xml)
```

Get Member Response:
```
Code: 200
Body: { "id": 1, "firstName": "ela333", "lastName": "dfdfdfdffdf", "dateOfBirth": "2014-09-06", "postalCode": "100212", "picture": "cert2_nkRPFsgAdocb.jpg", "createdAt": "2019-03-26T18:58:13.885+0000", "updatedAt": "2019-03-26T18:58:13.885+0000" }
```

### Get Members
**POST http://localhost:8080/members**

Request:
```
Headers:
Accept: application/json (or application/xml)
```

Get Members Response:
```
Code: 200
Body: [ { "id": 1, "firstName": "ela333", "lastName": "dfdfdfdffdf", "dateOfBirth": "2014-09-06", "postalCode": "100212", "picture": "cert2_nkRPFsgAdocb.jpg", "createdAt": "2019-03-26T18:58:13.885+0000", "updatedAt": "2019-03-26T18:58:13.885+0000" } ]
```

### Delete Member
**POST http://localhost:8080/members/delete/{memberId}**

Request:
```
Headers:
Accept: application/json (or application/xml)
```

Delete Members Response:
```
Code: 200
Body: { "responseCode": "00", "responseMessage": "Successful" }
```

## How to test it

To verify if it's correctly working you can use postman to test each of the endpoints. To see screenshots please click here [**https://drive.google.com/file/d/1uRSlgMVCfVtYV86fZqJ7PTq-nquMTg68/view?usp=sharing**]:


