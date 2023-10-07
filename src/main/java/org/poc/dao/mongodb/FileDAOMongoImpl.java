package org.poc.dao.mongodb;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.poc.connections.mongodb.MongoDBConnection;
import org.poc.dao.FileDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

// Class implementing FileDAO interface for MongoDB
public class FileDAOMongoImpl implements FileDAO {

    // Logger for capturing and storing logs during the program execution
    private static final Logger logger = LoggerFactory.getLogger(FileDAOMongoImpl.class);

    // Method to upload a file to MongoDB GridFS
    @Override
    public void uploadFile(String fileName, InputStream stream) {
        logger.info("Uploading file...");
        try {
            // Get GridFSBucket object from MongoDB connection
            GridFSBucket gridFSBucket = MongoDBConnection.getInstance().getGridFSBucket();

            // Upload file stream to GridFSBucket
            ObjectId fileId = gridFSBucket.uploadFromStream(fileName, stream);

            // Log success
            logger.info("Uploaded with id: " + fileId.toString());
        } catch (Exception e) {
            // Log failure
            logger.error("Error: " + e.getMessage());
        }
    }

    // Method to download a file from MongoDB GridFS
    @Override
    public InputStream downloadFile(String fileId) {
        logger.info("Downloading file...");
        try {
            // Get GridFSBucket object from MongoDB connection
            GridFSBucket gridFSBucket = MongoDBConnection.getInstance().getGridFSBucket();

            // Open download stream for specified file ID
            GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(new ObjectId(fileId));

            // Return download stream
            return downloadStream;
        } catch (Exception e) {
            // Log failure
            logger.error("Error: " + e.getMessage());
        }
        return null;
    }

    // Method to delete a file from MongoDB GridFS
    @Override
    public void deleteFile(String fileId) {
        logger.info("Deleting file...");
        try {
            // Get GridFSBucket object from MongoDB connection
            GridFSBucket gridFSBucket = MongoDBConnection.getInstance().getGridFSBucket();

            // Delete file by ID
            gridFSBucket.delete(new ObjectId(fileId));

            // Log success
            logger.info("Deleted file with id: " + fileId);
        } catch (Exception e) {
            // Log failure
            logger.error("Error: " + e.getMessage());
        }
    }

    // Method to list all files stored in MongoDB GridFS
    @Override
    public List<String> listAllFiles() {
        logger.info("Listing files...");
        try {
            // Get GridFSBucket object from MongoDB connection
            GridFSBucket gridFSBucket = MongoDBConnection.getInstance().getGridFSBucket();

            // Find all files in GridFSBucket
            GridFSFindIterable files = gridFSBucket.find();

            // Create list to hold file names
            List<String> fileNames = new ArrayList<>();

            // Populate list with file names
            for (GridFSFile file : files) {
                fileNames.add(file.getFilename());
            }

            // Return list of file names
            return fileNames;
        } catch (Exception e) {
            // Log failure
            logger.error("Error: " + e.getMessage());
        }
        return null;
    }
}
