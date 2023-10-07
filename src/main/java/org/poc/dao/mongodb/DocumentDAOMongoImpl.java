package org.poc.dao.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.poc.connections.mongodb.MongoDBConnection;
import org.poc.dao.DocumentDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

// Class implementing DocumentDAO interface for MongoDB
public class DocumentDAOMongoImpl implements DocumentDAO {

    // Logger for capturing and storing logs during program execution
    private static final Logger logger = LoggerFactory.getLogger(DocumentDAOMongoImpl.class);

    // Method to create a new document in MongoDB
    @Override
    public void createDocument(Document doc) {
        logger.info("Inserting document...");
        try {
            // Check if the document is null
            if (doc != null) {
                // Get MongoDB collection
                MongoCollection<Document> collection = MongoDBConnection.getInstance().getDocumentCollection();

                // Insert the document
                InsertOneResult result = collection.insertOne(doc);

                // Log the inserted ID
                logger.info("Inserted with id: " + result.getInsertedId().asObjectId().getValue());
            } else {
                logger.error("Null value error");
            }
        } catch (Exception e) {
            logger.error("Error: " + e.getMessage());
        }
    }

    // Method to read a document by ID from MongoDB
    @Override
    public Document readDocument(String docId) {
        logger.info("Retrieving document...");
        try {
            // Get MongoDB collection
            MongoCollection<Document> collection = MongoDBConnection.getInstance().getDocumentCollection();

            // Find and return the document
            return collection.find(eq("docId", docId)).first();
        } catch (Exception e) {
            logger.error("Error: " + e.getMessage());
        }
        return null;
    }

    // Method to update a document in MongoDB
    @Override
    public void updateDocument(Document doc) {
        logger.info("Updating document...");
        try {
            // Check if the document is null
            if (doc != null) {
                // Get MongoDB collection
                MongoCollection<Document> collection = MongoDBConnection.getInstance().getDocumentCollection();

                // Update the document
                UpdateResult result = collection.replaceOne(eq("docId", doc.get("docId")), doc);

                // Log the result
                logger.info("Updated: " + result.toString());
            } else {
                logger.error("Null value error");
            }
        } catch (Exception e) {
            logger.error("Error: " + e.getMessage());
        }
    }

    // Method to delete a document by ID from MongoDB
    @Override
    public void deleteDocument(String docId) {
        logger.info("Deleting document...");
        try {
            // Get MongoDB collection
            MongoCollection<Document> collection = MongoDBConnection.getInstance().getDocumentCollection();

            // Delete the document
            DeleteResult result = collection.deleteOne(eq("docId", docId));

            // Log the result
            logger.info("Deleted: " + result.toString());
        } catch (Exception e) {
            logger.error("Error: " + e.getMessage());
        }
    }

    // Method to get a list of all documents from MongoDB
    @Override
    public List<Document> getDocumentList() {
        logger.info("Retrieving list...");
        try {
            // Get MongoDB collection
            MongoCollection<Document> collection = MongoDBConnection.getInstance().getDocumentCollection();

            // Initialize the list
            List<Document> docs = new ArrayList<>();

            // Load documents into the list
            collection.find().into(docs);

            // Log the names
            logger.info("List:");
            for (Document doc : docs) {
                logger.info(doc.getString("docId"));
            }
            return docs;
        } catch (Exception e) {
            logger.error("Error: " + e.getMessage());
        }
        return null;
    }
}
