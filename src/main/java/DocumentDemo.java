import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;

import java.util.Arrays;



public class DocumentDemo {

    public static void main(String[] args) {
        /* Connecting to EC2 instance */
        MongoClient mongoClient = MongoClients.create("mongodb://ec2-54-175-223-164.compute-1.amazonaws.com");
        System.out.println(mongoClient);

        /* perform the same operations set without creating a POJO using  Document API to perform few operations */

        /*Define databse and collectiom */
        String dbName = "upgrad_doc_demo";
        String collectionName = "students";

        MongoDatabase db = mongoClient.getDatabase(dbName);
        MongoCollection<Document> collection = db.getCollection(collectionName);

        /* Inserting the record *//*
        Document document = new Document("_id" , 125)
                .append("name" , "Prajwal").append("courseId" , 12).append("age" , 23);
        System.out.println(collection.insertOne(document));*/

        /* Reading the document back */
        MongoCursor cursor = collection.find().cursor();

        while (cursor.hasNext()){
            System.out.println(cursor.next());
        }

        /*To find the average age of students in each course */
        cursor = collection.aggregate( Arrays.asList(Aggregates.group("$courseId",
                Accumulators.avg("averageAge", "$age")) )).cursor();

        while(cursor.hasNext()){
            System.out.println(cursor.next());

        }

        /* Get list of all data */
        for ( Document document : collection.find()){
            System.out.println(document.toJson());
        }

        /* To find only the first two records of the given collection */

        for ( Document document : collection.find().limit(2)){
            System.out.println(document.toJson());
        }


    }
}
