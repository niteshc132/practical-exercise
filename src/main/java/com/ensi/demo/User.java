package com.ensi.demo;

import org.springframework.data.annotation.Id;

public class User{	
   
    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;
    
    
    //user object constructor
    public User(
            String name,
            String price,
            String firstName,
            String lastName
    ) {
        
        this.email = name;
        this.password = price;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    @Id //id assignment for spring 
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public User updateWith(User user) {
        return new User(
            this.email,            
            user.password,
            user.firstName,
            user.lastName
        );
    }
}