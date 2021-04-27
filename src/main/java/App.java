import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;


public class App {

    public static void main(String[] args) {

        /* Connecting to EC2 instance */
        MongoClient mongoClient = MongoClients.create("mongodb://ec2-54-175-223-164.compute-1.amazonaws.com");
        System.out.println(mongoClient);

        /* Creating the database */
        MongoDatabase mongoDatabase = mongoClient.getDatabase("upgrad_demo");

        /* Creating the collection */
        MongoCollection<Document>  mongoCollection = mongoDatabase.getCollection("students");
        System.out.println("Collection :" + mongoCollection);

        /* Insert new record */
        Student student = new Student(1 , "Prajwal" , 12 , 23);
        System.out.println(mongoCollection.insertOne(student.createDBOject()));

        /* Bulk insertion in the database */
        Student student1  = new Student(2 , "Prajwal" , 12 , 25);
        Student student2  = new Student(3 , "Pra" , 12 , 25);
        Student student3  = new Student(4 , "Praj" , 12 , 24);
        Student student4  = new Student(5 , "Prajw" , 12 , 23);

        List<Document> documentList = new ArrayList<>();
        documentList.add(student1.createDBOject());
        documentList.add(student2.createDBOject());
        documentList.add(student3.createDBOject());
        documentList.add(student4.createDBOject());

        System.out.println(mongoCollection.insertMany(documentList));

        /* Reading all records */
        for(Document document : mongoCollection.find()){
            System.out.println(document.toJson());
        }

        /* Reading specific records */
        System.out.println(" <-----Reading based on filters ------>");
        Bson filter =eq("name" , "Prajwal");
        System.out.println(mongoCollection.find(filter).first().toJson());

        /* updating the records */
        System.out.println(" <-----updating document based on the filter ------>");
        Bson updateOperation = set("age" , 123);
        System.out.println(mongoCollection.updateOne(filter,updateOperation));
        System.out.println(mongoCollection.find(filter).first().toJson());

        /* Deleting the record */
        System.out.println(" <-----Deleting based on the filter ------>");
        System.out.println(mongoCollection.deleteOne(eq("name" , "Prajwal")));

        System.out.println(" <-----Dropping the collection ------>");
        mongoCollection.drop();

    }
}
