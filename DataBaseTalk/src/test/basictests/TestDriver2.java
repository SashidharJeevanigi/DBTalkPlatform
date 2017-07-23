package test.basictests;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import java.util.Collection;
import java.util.Set;

public class TestDriver2 {
    /**
	 * Java + MongoDB Hello world Example
	 *
	 */
	  public static void main(String[] args) {

		  System.out.println("Hello");
	   try {

		/**** Connect to MongoDB ****/
		// Since 2.10.0, uses MongoClient
		MongoClient mongoClient = new MongoClient("localhost",27017);
		System.out.println("Hello2");
        //MongoDatabase db = mongoClient.getDatabase("test");
        
        Collection<DB> dbs = mongoClient.getUsedDatabases();
        
        for (DB db : dbs) {
            System.out.println("- Database: " + db);
             
            //DB db = mongoClient.getDB(dbName);
             
            Set<String> collections = db.getCollectionNames();
            for (String colName : collections) {
                System.out.println("\t + Collection: " + colName);
            }
        }         
        mongoClient.close();
         
    } catch (Exception ex) {
        ex.printStackTrace();
    }
	  }
	  
	  
}
