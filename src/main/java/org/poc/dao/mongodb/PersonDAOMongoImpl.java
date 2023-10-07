package org.poc.dao.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.poc.entities.Person;
import org.poc.connections.mongodb.MongoDBConnection;
import org.poc.dao.PersonDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

// Class implementing PersonDAO interface
public class PersonDAOMongoImpl implements PersonDAO {

    // Logger for logging information and errors
    private static final Logger logger = LoggerFactory.getLogger(PersonDAOMongoImpl.class);

    // Method to create a new Person
    @Override
    public void createUser(Person person) {
        logger.info("Inserting person...");
        try {
            // Check if person is null
            if (person != null) {
                // Get MongoDB collection
                MongoCollection<Person> collection = MongoDBConnection.getInstance().getPersonCollection();
                // Insert person into collection
                InsertOneResult result = collection.insertOne(person);
                // Log success
                logger.info("Inserted a person with the following id: " + result.getInsertedId().asObjectId().getValue());
            } else {
                // Log error for null value
                logger.error("Error saving person: null value");
            }
        } catch (Exception e) {
            // Log exception
            logger.error("Error saving person: " + e.getMessage());
        }
    }

    // Method to read a Person by ID
    @Override
    public Person readUser(String personId) {
        logger.info("Retrieving person...");
        try {
            // Get MongoDB collection
            MongoCollection<Person> collection = MongoDBConnection.getInstance().getPersonCollection();
            // Find and return person
            return collection.find(eq("personId", personId)).first();
        } catch (Exception e) {
            // Log exception
            logger.error("Error retrieving person: " + e.getMessage());
        }
        return null;
    }

    // Method to update an existing Person
    @Override
    public void updateUser(Person person) {
        logger.info("Updating person...");
        try {
            // Check if person is null
            if (person != null) {
                // Get MongoDB collection
                MongoCollection<Person> collection = MongoDBConnection.getInstance().getPersonCollection();
                // Update person in collection
                UpdateResult result = collection.replaceOne(eq("personId", person.getPersonId()), person);
                // Log success
                logger.info("Updated a person: " + result.toString());
            } else {
                // Log error for null value
                logger.error("Error updating person: null value");
            }
        } catch (Exception e) {
            // Log exception
            logger.error("Error updating person: " + e.getMessage());
        }
    }

    // Method to delete a Person by ID
    @Override
    public void deleteUser(String personId) {
        logger.info("Deleting person...");
        try {
            // Get MongoDB collection
            MongoCollection<Person> collection = MongoDBConnection.getInstance().getPersonCollection();
            // Delete person from collection
            DeleteResult result = collection.deleteOne(eq("personId", personId));
            // Log success
            logger.info("Deleting a person: " + result.toString());
        } catch (Exception e) {
            // Log exception
            logger.error("Error deleting person: " + e.getMessage());
        }
    }

    // Method to get a list of all Persons
    @Override
    public List<Person> getPersonList() {
        logger.info("Retrieving person list...");
        try {
            // Get MongoDB collection
            MongoCollection<Person> collection = MongoDBConnection.getInstance().getPersonCollection();
            // Create empty list for persons
            List<Person> persons = new ArrayList<>();
            // Populate list with persons from collection
            collection.find().into(persons);
            // Log each person's name
            logger.info("Person list: ");
            for (Person person : persons) {
                logger.info(person.getName());
            }
            return persons;
        } catch (Exception e) {
            // Log exception
            logger.error("Error retrieving person list: " + e.getMessage());
        }
        return null;
    }
}
