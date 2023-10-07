package org.poc.dao;

import org.poc.entities.Person;

import java.util.List;

// Interface for Person Data Access Object (DAO)
public interface PersonDAO {

    // Create a new Person record
    void createUser(Person person);

    // Read a Person record by ID
    Person readUser(String personId);

    // Update an existing Person record
    void updateUser(Person person);

    // Delete a Person record by ID
    void deleteUser(String personId);

    // Get a list of all Person records
    List<Person> getPersonList();

}
