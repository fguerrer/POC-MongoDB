package org.poc;

import org.bson.Document;
import org.poc.dao.DocumentDAO;
import org.poc.dao.PersonDAO;
import org.poc.dao.mongodb.DocumentDAOMongoImpl;
import org.poc.dao.mongodb.PersonDAOMongoImpl;
import org.poc.entities.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(PersonDAOMongoImpl.class);

    public static void main(String[] args) {

        // POJO Person related examples

        // Create PersonDAOImpl instance
        PersonDAO personDAO = new PersonDAOMongoImpl();

        // Create new Person object
        Person newPerson = new Person();
        newPerson.setPersonId("1");
        newPerson.setName("John");
        newPerson.setAge(25);

        // Create User
        personDAO.createUser(newPerson);

        // Read User by ID
        Person readPerson = personDAO.readUser("1");
        if(readPerson != null) {
            logger.info("Person read: " + readPerson.getName());
        } else {
            logger.info("Person not found.");
        }

        // Update User
        newPerson.setAge(26);
        personDAO.updateUser(newPerson);

        // Get List of Persons
        List<Person> persons = personDAO.getPersonList();
        if (persons != null) {
            for (Person person : persons) {
                logger.info("Listed person: " + person.getName());
            }
        } else {
            logger.info("No persons found.");
        }

        // Delete User by ID
        personDAO.deleteUser("1");

        // Document related examples

        // Create DocumentDAO instance
        DocumentDAO documentDAO = new DocumentDAOMongoImpl();

        // Create New Document
        Document newDoc = new Document("docId", "1");
        documentDAO.createDocument(newDoc);

        // Read Document by ID
        Document readDoc = documentDAO.readDocument("1");
        if (readDoc != null) {
            logger.info("Document read: " + readDoc.toJson());
        } else {
            logger.info("Document not found.");
        }

        // Update Document
        newDoc.append("Msg", "Hello World");
        documentDAO.updateDocument(newDoc);

        // Get List of Documents
        List<Document> docs = documentDAO.getDocumentList();
        if (docs != null) {
            for (Document doc : docs) {
                logger.info("Listed document: " + doc.toJson());
            }
        } else {
            logger.info("No documents found.");
        }

        // Delete Document by ID
        documentDAO.deleteDocument("1");
        

    }
}