package org.poc.dao;

import org.bson.Document;
import java.util.List;

// Interface defining methods for document-related CRUD operations
public interface DocumentDAO {

    // Method for creating a new Document in the database
    void createDocument(Document doc);

    // Method for reading a Document from the database by ID
    Document readDocument(String docId);

    // Method for updating an existing Document in the database
    void updateDocument(Document doc);

    // Method for deleting a Document from the database by ID
    void deleteDocument(String docId);

    // Method for retrieving a list of all Documents from the database
    List<Document> getDocumentList();
}
