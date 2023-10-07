package org.poc.connections.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import org.bson.Document;
import org.poc.entities.Person;

// Interface defining methods to interact with MongoDB
public interface IMongoDBConnection {

    // Method to retrieve the MongoCollection specific to 'Person' objects
    public MongoCollection<Person> getPersonCollection();

    // Method to retrieve the MongoCollection specific to Document objects
    public MongoCollection<Document> getDocumentCollection();

    // Method to retrieve a GridFSBucket for file storage and retrieval
    public GridFSBucket getGridFSBucket();

    // Method to get a reference to the MongoDB database
    public MongoDatabase getDatabase();
}

