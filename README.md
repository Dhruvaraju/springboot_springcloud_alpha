- [springboot springcloud alpha](#springboot-springcloud-alpha)
  - [What is a web service?](#what-is-a-web-service)
    - [SOAP](#soap)
    - [REST](#rest)
    - [REST VS SOAP](#rest-vs-soap)

# springboot springcloud alpha

## What is a web service?

- A software system designed to support interoperable machine to machine interaction over a network.
- A web-service should be:
  - Designed for machine to machine interaction.
  - Interoperable while platform independent.
  - Allow communications over network.
- Request and response should be platform independent, to make a web-service platform independent.
- Popular data formats used are xml, json.
- **Service definition** will help in identifying request/response format, request structure, response structure, endpoint.

- When app A requires some info from app B
  - App A will send request to App B, Here App A is **consumer** and App B is **provider**.
  - **Service definition** is the contract between App A and App B.
  - **Transport** how a service is called is HTTP or MQ

> SOAP and REST are not comparable, SOAP defines the restrictions on data format and details that need to be sent, while REST is an architectural approach.

### SOAP

- Simple Object Access Protocol
- Uses XML for data transfer.
- SOAP request or response contains the following sections:
  - SOAP-ENV: Envelope
  - SOAP-ENV: Header, contains metadata, authorization related information.
  - SOAP-ENV: Body, contains request and response information.
- Format used: SOAP XML Request, SOAP XML Response.
- Transport: SOAP over MQ or HTTP.
- Service Definition: WSDL (Web services description language), which is an xml representation of inputs and outputs.

### REST

- Representational State Transfer.
- Uses HTTP, does resource abstraction using URI (Uniform Resource Identifier).
- Examples:
  - To create - POST - /user
  - To Delete - DELETE - /user/1
  - To get all users - GET - /users
  - To update - UPDATE - /user/1
- HTTP methods are used for resource abstractions
- Responds using HTTP Status codes like 200, 400 ..
- Data Exchange Format: No restrictions, JSON is popular.
- Transport: Only HTTP.
- Service Definition: No standard, WADL or swagger is generally used.

### REST VS SOAP

| Topic                  | Rest                          | SOAP                   |
| ---------------------- | ----------------------------- | ---------------------- |
| Style                  | Architectural Approach        | Restrictions           |
| Data Exchange Format   | No standard Json is preferred | SOAP XML               |
| Service Definition     | WADL / Swagger                | WSDL                   |
| Transport              | HTTP                          | No standard HTTP or MQ |
| Ease of Implementation | Easy compared to SOAP         | Lots of restrictions   |
