package com.ensi.demo;

import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
@EnableMapRepositories

public class UserService {
	
	  private final CrudRepository<User, String> repository;

	  //db to hold list on users
	  public UserService(CrudRepository<User, String> repository) {
	    this.repository = repository;
	    this.repository.saveAll(defaultItems());
	  }
	  
	  //default users set in the db
	  private static List<User> defaultItems() {
		    return List.of(
		      new User("test@test.com", "password", "John", "Doe"),
		      new User("bob@test.com", "password", "Bob", "Vance")
		    );
		  }
	  
	  
	  //find users in the db and return as list
	   public List<User> findAll() {
	        List<User> list = new ArrayList<>();
	        Iterable<User> items = repository.findAll();
	        items.forEach(list::add);
	        return list;
	    }
	   
	   public Optional<User> find(String email) {
	        return repository.findById(email);
	    }

	   public void delete(String email) {
	        repository.deleteById(email);
	    }
	   
	   public User create(User user) {
	       //createa and append new user object
		   User copy = new User(	                
	                user.getEmail(),
	                user.getPassword(),
	                user.getFirstName(),
	                user.getLastName()
	        );
	        return repository.save(copy);
	    }
	   
	   public Optional<User> update( String email, User newUser) {
	        // Only update an item if it can be found first.
	        return repository.findById(email)
	                .map(oldUser -> {
	                   User updated = oldUser.updateWith(newUser);
	                   return repository.save(updated);
	                });
	    }
	   
	   
}
