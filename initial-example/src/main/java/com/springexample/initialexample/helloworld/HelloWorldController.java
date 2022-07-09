package com.springexample.initialexample.helloworld;

import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(path = "/hello-world/path/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello %s", name));
    }
}
