package test;

import java.util.ArrayList;
import java.util.List;

import talkmongo.representation.MongoDBConnection;
import talkmongo.representation.MongoTableConnector;
import talkmongo.representation.dbinterface.DBConnection;
import talkmongo.representation.dbinterface.TableConnector;
import test.Student;


public class TestDriver {

	public static void main(String args[]) {

		List<Student> students = new ArrayList<Student>();
		
		MongoDBConnection dbConnection = new MongoDBConnection("localhost",27017); 
		//TODO LIST:
		//config.xml, DB Type - DBConnector will per user. Implement a "interface".
		//Multi-tenant system
		//Specify the specific installation
		
		MongoTableConnector tableConnector  = new MongoTableConnector(dbConnection,Student.class);	
		
		while(tableConnector.hasNextObject()){
			Student s = (Student)tableConnector.getNextObject();
			
			students.add(s);
		}

		System.out.println("*************************************************** All STUDENTS : \n"
				+ students);
	}
}

