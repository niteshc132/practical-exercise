package com.ensi.demo;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InMemoryUserDB extends CrudRepository<User, String> {}
