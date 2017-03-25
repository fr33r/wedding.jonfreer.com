# wedding.jonfreer.com
Emma and I's wedding website.

[![Build Status](https://travis-ci.org/freerjm/wedding.jonfreer.com.svg?branch=development)](https://travis-ci.org/freerjm/wedding.jonfreer.com)


## Domain-Driven Design

Domain-driven design is utilized heavily within the backend Java JAX-RS 2.0 API. Below is some insight into the various domain-driven design elements for this application.

### Ubiquitous Language

| Term        	| Description                                                      	|
|-------------	|------------------------------------------------------------------	|
| Reservation 	| A confirmation that the guest is attending the wedding.          	|
| Guest       	| A single person that has been extended an invite to the wedding. 	|

### Entities

+ Guest
	- The `Guest` was deemed an _entity_ because it is important that we maintain an identity for these objects. The application is concerned with tracking the state of each guest over time. When presented with two guest objects with completely different states but the same identity, the application requires the ability to determine if the two objects represent the same real-world guest.

### Value Objects

+ Reservation
	- The `Reservation` was classified as a _value object_ mainly because the application is not concerned with maintaining an identity for reservations. There is no need to evaluate whether two reservation objects represent the same reservation in real life. The main "value" of reservations in the domain model are to establish a relationship between a `Guest` and a `Reservation`.