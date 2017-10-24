package me.tsccoding.mongodb;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.Arrays;

public class MongoMain {
    public static void main(String args[]) {
        String uri = "mongodb://Admin:admin@mongodb-shard-00-00-altt9.mongodb.net:27017,mongodb-shard-00-01-altt9.mongodb.net:27017,mongodb-shard-00-02-altt9.mongodb.net:27017/admin?replicaSet=MongoDB-shard-0&ssl=true";
        MongoClientURI clientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(clientURI);

        MongoDatabase mongoDatabase = mongoClient.getDatabase("MongoDB");
        MongoCollection<Document> collection = mongoDatabase.getCollection("test");

        System.out.println("Database Connected");

        Block<Document> printBlock = document -> System.out.println(document.toJson());

        collection.aggregate(
                Arrays.asList(
                        Aggregates.match(Filters.eq("Age", 27)),
                        Aggregates.group("$Race", Accumulators.sum("count", 1)
                        ))
        ).forEach(printBlock);

        System.out.println("Collection Aggregrated.");


    }
}
