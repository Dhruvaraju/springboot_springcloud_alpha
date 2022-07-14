- [springboot springcloud alpha](#springboot-springcloud-alpha)
  - [What is a web service?](#what-is-a-web-service)
    - [SOAP](#soap)
    - [REST](#rest)
    - [REST VS SOAP](#rest-vs-soap)
  - [Springboot Restful web services](#springboot-restful-web-services)
    - [Example services that will be created](#example-services-that-will-be-created)
    - [Creating a hello world service](#creating-a-hello-world-service)
    - [AutoConfiguration and dispatcher servlet](#autoconfiguration-and-dispatcher-servlet)
      - [What is dispatcher servlet](#what-is-dispatcher-servlet)
    - [Path parameters](#path-parameters)
    - [Creating a user object and service](#creating-a-user-object-and-service)
    - [Creating a controller class for the same.](#creating-a-controller-class-for-the-same)
    - [Custom exception handling](#custom-exception-handling)
    - [Custom exception object for all exceptions](#custom-exception-object-for-all-exceptions)
    - [Delete a user](#delete-a-user)

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

## Springboot Restful web services

- Go to [Spring Initializr](https://start.spring.io/)
- Choose maven, language as java, choose a spring boot version which do not have snapshot or m1 or m2 in its name.
- Under project metadata choose a group name eg: com.initialExample, artifact name as sample app
- Packaging as jar and java version as 11.
- Dependencies: Spring web, Devtools, Spring data JPA and H2.

### Example services that will be created

- Example application that need will be created:
- A user and posts that are created by a user

| Description                   | HTTP method | URI                         | Example  |
| ----------------------------- | ----------- | --------------------------- | -------- |
| Retrieve all users            | GET         | /users                      |
| Create a User                 | POST        | /users                      |
| Retrieve one user             | GET         | /users/{id}                 | /users/1 |
| Delete a User                 | DELETE      | /users/{id}                 | /users/1 |
| Retrieve all posts for a user | GET         | /users/{id}/posts           |
| Create a post for a user      | POST        | /users/{id}/posts           |
| Retrieve details of a post    | GET         | /users/{id}/posts/{post_id} |

### Creating a hello world service

- Create a java class and annotate it with `@RestController` to let springboot know that this class handles requests.
- Create a method and annotate it with `@GetMapping` which will let springboot know that this method will respond to a get call.
- We need to specify a path in get mapping to let springboot know for which endpoint this method should respond. Eg: `@GetMapping(path="hello-world-bean")`
- Another annotation can be used `@RequestMapping(path = "/hello-world", method = RequestMethod.GET)` where we have to specify the path to respond and the request method that needed to be responded.

```java
@RestController
public class HelloWorldController {
    @RequestMapping(path = "/hello-world", method = RequestMethod.GET)
    public String helloWorld(){
        return "Hello World";
    }

    @GetMapping(path="hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("This is from bean");
    }
}
```

### AutoConfiguration and dispatcher servlet

- To enable debug logging for springboot add `logging.level.org.springframework=DEBUG` to application.properties.
- Now we can see AutoConfigurationReport, Current project has springboot starter as dependency
- Springboot starter has spring mvc as a dependency, hence DispatcherServlet will be available.
- Springboot looks at all the jars available in classpath and once it finds them it will auto configure them example:
  - Dispatcher servlet.
  - ErrorMVC
    - Basic error controller
    - Basic error attributes
    - Default view resolver
    - White label error configuration
    - Http message converters
    - Jackson auto configuration and many more.

#### What is dispatcher servlet

- Dispatcher servlet will handle all the requests coming in.
- Dispatcher servlet will have a map of all the urls corresponding methods.
- When a request comes in dispatcher servlet will see which is the path and method that need to respond and redirect to it.
- The above mentioned is called Front controller pattern.
- We can see a log as `mapping servlet: 'dispatcherServlet' to [/]`
- Springboot autoConfiguration will configure dispatcher servlet.

> Because of springboot autoConfiguration objects are converted to json with help of jackson. Basic error mapping is also done using auto configuration.

### Path parameters

#@PathVariable annotation

- We can take parameters from path using `@PathVariable` annotation.
- Path variable should be mentioned in curly braces like `path = "/hello-world/path/{name}`.
- It can be taken as an input to the method.

```java
@RequestMapping(path = "/hello-world/path/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello %s", name));
    }
```

> When we do not mention a method for request mapping it is considered as GET by default.

### Creating a user object and service

- Define a user object with id, name and date of birth.
- Create constructors with all parameters and non of the parameters.
- Add a toString method, getters and setters.

```java
package com.springexample.initialexample.user;

import java.util.Date;

public class User {
    private Integer id;
    private String name;
    private Date birthDate;

    public User() {
    }

    public User(Integer id, String name, Date birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
```

- Creating a user service which will have list of users, we can get user based on id and save a new user.
- As of now implement it using static list, later it can be moved to spring data jpa.

```java
package com.springexample.initialexample.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserDAOService {

    private static List<User> users = new ArrayList<>();
    private static int userCount = 3;

    static {
        users.add(new User(1, "superman", new Date()));
        users.add(new User(2, "batman", new Date()));
        users.add(new User(3, "wonder-woman", new Date()));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if (null == user.getId()) {
            user.setId(++userCount);
        }
        users.add(user);
        return user;
    }

    public User findOne(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
}
```

### Creating a controller class for the same.

- Create a java class and annotate it with `@RestController`
- Autowire `UserDAOService'
- Create a method to retrieve all user by calling findAll method from the service
- Use `@GetMapping` or `@RequestMapping` to attach the method to `/users` endpoint.

```java
@RequestMapping(path="/users", method = RequestMethod.GET)
    public List<User> fetchAllUsers(){
        return userService.findAll();
    }
```

- To get individual user use findOne method and attach it to uri `/users/{id}` where id is a path variable.

```java
@RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public User findUser(@PathVariable int id){
        return userService.findOne(id);
    }
```

> Date can come in complete numeric form to change it use configuration `spring.jackson.serialization.write-dates-as-timestamps=false` in application.properties #timestamp

- Creating a method to save a user, we can use save method from service.
- Use the POST request method and attach path `/users`

```java
@RequestMapping(path = "/users", method = RequestMethod.POST)
    public void createUser(@RequestBody User user){
        userService.save(user);
    }
```

- This will return http status as 200 when creating a user.
- Generally we have to return `201 Created` status and location of resource created for that we have to use HTTP Entity.
- To provide the required response status, we can use Response Entity
- `ServletUriComponentsBuilder` is used to create a location for the new user just created.
- We can access the location from the response header.

```java
@RequestMapping(path = "/users", method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody User user) {
        User savedUser = userService.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
```

### Custom exception handling

- We can create a custom exception and annotate with `@ResponseStatus()` to throw the required exception status.
- While considering an user not found exception when a specific user id is passed, we can through UserNotFoundException.

```java
//UserNotFoundException.java
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
```

- The above can be invoke as

```java
@RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public User findUser(@PathVariable int id) {
        User user = userService.findOne(id);
        if (null == user){
            throw  new UserNotFoundException("Id: " + id);
        }
        return user;
    }
```

### Custom exception object for all exceptions

- Create exception object
- ResponseExceptionEntityHandler is a class which can be used to handle exceptions all over your application.
- Create a class and extend ResponseExceptionEntityHandler.
- Annotate it with `@RestController` as it is going to respond to rest calls.
- Annotate it with `@ControllerAdvice` as it allows it to perform few common functions across controllers like
  - Adding custom model fields
  - Exception Handling
- Here we are trying to perform exception handling.
- We need to override `handleException` method to respond for all exceptions
- To respond for specific exception annotate it with `@ExceptionHandler` and mention the exception that you want to respond for.
- Example method mentioned below.

```java
@ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) throws Exception {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(), ex.getMessage(), request.getDescription(false)
        );
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
```

- Every exception will return an internal server http status code with the Exception Response object.
- Example for Handling `UserNotFoundException` is mentioned below

```java
@ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> userNotFoundException(
            UserNotFoundException ex, WebRequest request
    ) throws Exception {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(), ex.getMessage(), request.getDescription(false)
        );
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }
```

### Delete a user

- Iterate through the list and remove the user with provided id
- Use Iterator to remove it.

```java
public User deleteUser(int id) {
        Iterator<User> iterator = users.iterator();
        while(iterator.hasNext()){
            User user = iterator.next();
            if(user.getId() == id){
                iterator.remove();
                return user;
            }
        }
        return null;
    }
```

- in controller we can add a method for delete uri

```java
@RequestMapping(path = "/users/{id}", method = RequestMethod.DELETE)
    public User deleteUser(@PathVariable int id) {
        User user = userService.deleteUser(id);
        if (null == user){
            throw  new UserNotFoundException("Id: " + id);
        }
        return user;
    }
```

- Generally we will not send the user details while deleting so we can update the above method as below.

```java
@RequestMapping(path = "/users/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable int id) {
        User user = userService.deleteUser(id);
        if (null == user){
            throw  new UserNotFoundException("Id: " + id);
        }
    }
```
