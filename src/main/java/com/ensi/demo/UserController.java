package com.ensi.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/menu/users") //address of stored users

public class UserController {
	  private final UserService service;

	  public UserController(UserService service) {
	    this.service = service;
	  }
	  
	  @GetMapping //returns a list of all users
	    public ResponseEntity<List<User>> findAll() {
	        List<User> users = service.findAll();
	        return ResponseEntity.ok().body(users);
	    }

	  @GetMapping("/{email}") //return user identified by email
	  	public ResponseEntity<User> find(@PathVariable("email") String email) {
	        Optional<User> user = service.find(email);
	        return ResponseEntity.of(user);
	    }
	  
	  @PostMapping //create a user, email is the primary key
	    public ResponseEntity<User> create(@RequestBody User user) {
	        User created = service.create(user);
	        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
	                .path("/{email}")
	                .buildAndExpand(created.getEmail())
	                .toUri();
	        return ResponseEntity.created(location).body(created);
	    }
	  
	    @PutMapping("/{email}") //update user details using email to search for existing users
	    public ResponseEntity<User> update(
	            @PathVariable("email") String email,
	            @RequestBody User updatedItem) { //updated user object

	        Optional<User> updated = service.update(email, updatedItem);

	        return updated
	                .map(value -> ResponseEntity.ok().body(value))
	                .orElseGet(() -> {
	                    User created = service.create(updatedItem);
	                    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
	                            .path("/{email}")
	                            .buildAndExpand(created.getEmail())
	                            .toUri();
	                    return ResponseEntity.created(location).body(created);
	                });
	    }

	    
	    @DeleteMapping("/{email}")
	    public ResponseEntity<User> delete(@PathVariable("email") String email) {
	        service.delete(email);
	        return ResponseEntity.noContent().build();
	    }
	 
}
