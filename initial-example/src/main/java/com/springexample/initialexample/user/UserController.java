package com.springexample.initialexample.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserDAOService userService;
    private URI location;

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> fetchAllUsers() {
        return userService.findAll();
    }

    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public EntityModel<User> findUser(@PathVariable int id) {
        User user = userService.findOne(id);
        if (null == user) {
            throw new UserNotFoundException("Id: " + id);
        }
        EntityModel<User> resource = EntityModel.of(user);
        WebMvcLinkBuilder userLink =
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).fetchAllUsers());
        resource.add(userLink.withRel("all-users"));
        return resource;
    }

    @RequestMapping(path = "/users/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable int id) {
        User user = userService.deleteUser(id);
        if (null == user) {
            throw new UserNotFoundException("Id: " + id);
        }
    }

    @RequestMapping(path = "/users", method = RequestMethod.POST)
    public ResponseEntity createUser(@Valid @RequestBody User user) {
        User savedUser = userService.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
