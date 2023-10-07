package org.poc.dao;

import java.io.InputStream;
import java.util.List;

// Interface defining methods for file-related operations
public interface FileDAO {

    // Method for uploading a file with a given file name and input stream
    void uploadFile(String fileName, InputStream stream);

    // Method for downloading a file using its unique identifier, returns the file as an InputStream
    InputStream downloadFile(String fileId);

    // Method for deleting a file using its unique identifier
    void deleteFile(String fileId);

    // Method for listing all available files, returns a list of file names
    List<String> listAllFiles();
}
