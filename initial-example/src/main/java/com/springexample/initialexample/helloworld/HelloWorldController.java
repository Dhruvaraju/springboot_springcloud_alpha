package com.springexample.initialexample.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {
    @Autowired
    private MessageSource messageSource;
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

    @RequestMapping(path = "/hello-world/i18n", method = RequestMethod.GET)
    public String helloWorldI18n(
            @RequestHeader(name = "Accept-Language")Locale locale
            ){
        return messageSource.getMessage("morning.greeting",null,locale);
    }
}
