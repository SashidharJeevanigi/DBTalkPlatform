import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import talkmongo.representation.MongoDBConnection;
import talkmongo.representation.MongoTableConnector;
import talkmongo.representation.dbinterface.DBConnection;
import talkmongo.representation.dbinterface.TableConnector;
import talkmongo.representation.logging.LoggerSettings;


public class StudentApp {

	public static void main(String args[]) {

		LoggerSettings.configureLog(7, "db_talk.log");
		
		List<Student> students = new ArrayList<Student>();
		
		LoggerSettings.setIndentLevel(0);
		LoggerSettings.logger.log(Level.FINE,"Application program to access Students from Mongo DB\n");
		
		/*******************
		 * 
		 * Create a Mongo DB connection client
		 * 
		 ******************/
		MongoDBConnection dbConnection = new MongoDBConnection("localhost",27017); 
		//TODO LIST:
		//config.xml, DB Type - DBConnector will per user. Implement a "interface".
		//Multi-tenant system
		//Specify the specific installation
		
		
		/*******************
		 * 
		 * Create a Table connector based on a annotated Java class : Student
		 * 
		 ******************/
		LoggerSettings.logger.log(Level.FINE,"Initializing Table connector: \n");
		MongoTableConnector tableConnector  = new MongoTableConnector(dbConnection,Student.class);	
		
		
		/*******************
		 * 
		 * Retrieve the DB rows/documents as Java POJO "Student" objects
		 * 
		 ******************/
		while(tableConnector.hasNextObject()){
			Student s = (Student)tableConnector.getNextObject();
			
			students.add(s);
		}
		
		
		/*******************
		 * 
		 * Print the students
		 * 
		 ******************/
		LoggerSettings.setIndentLevel(0);
		LoggerSettings.logger.log(Level.FINE,"*************************************************** All STUDENTS : \n");
		for(Student student: students) {
			LoggerSettings.setIndentLevel(1);
			LoggerSettings.logger.log(Level.FINE,"Studnet: "+ student);
		}
		
	}
}
