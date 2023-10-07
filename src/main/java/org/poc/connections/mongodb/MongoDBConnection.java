package org.poc.connections.mongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.connection.ConnectionPoolSettings;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.poc.entities.Person;

import java.util.concurrent.TimeUnit;

// Enum Singleton class for MongoDB connection
public enum MongoDBConnection implements IMongoDBConnection {
    INSTANCE;
    // MongoDB client and database instances
    private MongoClient client;
    private MongoDatabase database;

    // Configuration constants
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "TestDB";
    private static final String COLLECTION_PEOPLE = "people";
    private static final String COLLECTION_DOCS = "documents";

    // Connection Pool settings
    private static final int MAX_CONNECTION_SIZE = 10;
    private static final int MIN_CONNECTION_SIZE = 5;
    private static final int MAX_CONNECTION_LIFETIME = 30;
    private static final int MAX_CONNECTION_IDLETIME = 30000;
    private static final int CONNECTION_TIMEOUT = 2000;

    // Lazy initialization of MongoClient
    private MongoClient getClient() {
        if (client == null) {
            client = createMongoClient();
        }
        return client;
    }

    // Lazy initialization of MongoDatabase
    @Override
    public MongoDatabase getDatabase() {
        if (database == null) {
            database = getClient().getDatabase(DATABASE_NAME);
        }
        return database;
    }

    // Method to create a MongoClient with various settings
    private static MongoClient createMongoClient() {
        ConnectionString connectionString = new ConnectionString(CONNECTION_STRING);
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .retryWrites(true)
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                // Connection Pool settings
                .applyToConnectionPoolSettings((ConnectionPoolSettings.Builder builder) -> {
                    builder.maxSize(MAX_CONNECTION_SIZE)
                            .minSize(MIN_CONNECTION_SIZE)
                            .maxConnectionLifeTime(MAX_CONNECTION_LIFETIME, TimeUnit.MINUTES)
                            .maxConnectionIdleTime(MAX_CONNECTION_IDLETIME, TimeUnit.MILLISECONDS);
                })
                // Socket settings
                .applyToSocketSettings(builder -> {
                    builder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);
                })
                .build();

        return MongoClients.create(clientSettings);
    }

    public static IMongoDBConnection getInstance() {
        return MongoDBConnection.INSTANCE;
    }

    // Method to get collection for People
    @Override
    public MongoCollection<Person> getPersonCollection() {
        return getDatabase().getCollection(COLLECTION_PEOPLE, Person.class);
    }

    // Method to get collection for Documents
    @Override
    public MongoCollection<Document> getDocumentCollection() {
        return getDatabase().getCollection(COLLECTION_DOCS);
    }

    // Method to get a GridFSBucket
    @Override
    public GridFSBucket getGridFSBucket() {
        return GridFSBuckets.create(getDatabase());
    }

}
