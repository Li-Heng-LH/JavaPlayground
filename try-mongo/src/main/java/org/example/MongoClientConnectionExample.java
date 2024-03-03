package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MongoClientConnectionExample {
    public static void main(String[] args) throws Exception {

        Properties prop = new Properties();
        //load a properties file from class path, inside static method
        prop.load(new FileInputStream("src/main/resources/mongo.properties"));

        String connectionString = prop.getProperty("connectionString");

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        // Create a new client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Send a ping to confirm a successful connection
                MongoDatabase database = mongoClient.getDatabase("admin");
                database.runCommand(new Document("ping", 1));
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");

                List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
                databases.forEach(db -> System.out.println(db.toJson()));

                MongoDatabase newDatabase = mongoClient.getDatabase("testDatabase");
                MongoCollection<Document> collection = newDatabase.getCollection("testCollection");
                Document doc1 = new Document("color", "red").append("qty", 5);
                InsertOneResult result = collection.insertOne(doc1);
                System.out.println("Inserted a document with the following id: "
                        + result.getInsertedId().asObjectId().getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

