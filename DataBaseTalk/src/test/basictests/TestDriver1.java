package test.basictests;

import com.mongodb.MongoClient;
import com.mongodb.operation.FindOperation;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

import com.mongodb.ServerAddress;
import java.util.Arrays;

public class TestDriver1 {

   public static void main( String args[] ) {
	
      try{
		
    	  long start = 0;
    	  long end = 0;
    	  System.out.println("Connect to database successfully... 111"); 
         // To connect to mongodb server
    	 start = System.currentTimeMillis();
         MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
         end = System.currentTimeMillis();
         System.out.println("New Mongo - Time : " + (end - start) + " MS");
         
         // Now connect to your databases
         start = System.currentTimeMillis();
         DB db = mongoClient.getDB( "test" );
         end = System.currentTimeMillis();
         System.out.println("Connect to database successfully : " + (end - start) + " MS");

         start = System.currentTimeMillis();
         DBCollection coll = db.getCollection("test");
         end = System.currentTimeMillis();
         System.out.println("Collection mycol selected successfully : " + (end - start) + " MS");
			
         //GET ALL THE ROWS IN THE TABLE - test
         start = System.currentTimeMillis();
         DBCursor cursor = coll.find();
         int i = 1;			
         while (cursor.hasNext()) { 
            System.out.println("Inserted Document: "+i); 
            System.out.println(cursor.next()); 
            i++;
         }
         end = System.currentTimeMillis();
         System.out.println("First Cursor : " + (end - start) + " MS");
         
         //GET SPECIFIC ROWS IN THE TABLE
         start = System.currentTimeMillis();
         BasicDBObject query = new BasicDBObject();
         BasicDBObject searchFeilds  = new BasicDBObject();
         searchFeilds.put("_id", 1);
         DBCursor cursor1 = coll.find(query,searchFeilds);
         i = 1;
         while (cursor1.hasNext()) { 
             System.out.println("Inserted Document: "+i); 
             System.out.println(cursor.next()); 
             i++;
         }
         end = System.currentTimeMillis();
         System.out.println("First Cursor : " + (end - start) + " MS");

			
      }catch(Exception e){
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         e.printStackTrace();
      }
   }
}