package test.basictests;

import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

import java.util.ArrayList;
import java.util.List;
import test.Student;

public class TestDiverWithObject { 

	public static void main(String args[]) {

		try {

			long start = 0;
			long end = 0;
			System.out.println("Connect to database successfully... 111");
			// To connect to mongodb server
			start = System.currentTimeMillis();
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			end = System.currentTimeMillis();
			System.out.println("New Mongo - Time : " + (end - start) + " MS");

			// Now connect to your databases
			start = System.currentTimeMillis();
			DB db = mongoClient.getDB("student");
			end = System.currentTimeMillis();
			System.out.println("Connect to database successfully : "
					+ (end - start) + " MS");

			start = System.currentTimeMillis();
			DBCollection coll = db.getCollection("student");
			end = System.currentTimeMillis();
			System.out.println("Collection mycol selected successfully : "
					+ (end - start) + " MS");

			// GET ALL THE ROWS IN THE TABLE - test
			start = System.currentTimeMillis();

			List<Student> students = new ArrayList<Student>();
			int i = 0;
			DBCursor cursor = coll.find();
			while (cursor.hasNext()) {
				DBObject studentObj = cursor.next();
				// How to get the DBObject value to ArrayList of Java Object?

				    i++; 
			//	    String idString = (Double)(studentObj.get("_id"));
				    //Double id = Double.parseDouble(idString);
					String firstName = (String)studentObj.get("firstName");
					String lastName = (String)studentObj.get("lastName");
					String age = (String)studentObj.get("age");
					String gender = (String)studentObj.get("gender");

					int ageInt = Integer.parseInt(age);
					Student student = new Student();
					student.setFirstName(firstName);
					student.setLastName(lastName);
					student.setAge(ageInt);
					student.setGender(gender);
					//student.setId(i);

					students.add(student);
				
					System.out.println("**** " + i + studentObj.keySet());

			}
			
			System.out.println("All STUDENTS : \n"
					+ students);
			
			end = System.currentTimeMillis();
			System.out.println("\n\nPrint all : "
					+ (end - start) + " MS");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}


}

//db.student.save({_id : 1, firstName: 'Nayani',lastName: 'Jeevanigi',age: '5',gender: 'F'})
//db.student.save({_id : 2, firstName: 'Sashidhar',lastName: 'Jeevanigi',age: '35',gender: 'M'})
//db.student.save({_id : 3, firstName: 'Rupa',lastName: 'Billur',age: '33',gender: 'F'})
