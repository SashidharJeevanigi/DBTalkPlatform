
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import talkmongo.DBConnector;
import talkmongo.TableConnectionHelper;
import talkmongo.representation.dbinterface.DBDefinition;
import talkmongo.representation.dbinterface.TableConnector;
import talkmongo.representation.logging.LoggerSettings;
import test.Student;

public class StudentApp {

	public static void main(String args[]) {

		try {

			LoggerSettings.configureLog(7, "db_talk.log");

			List<Student> students = new ArrayList<Student>();

			LoggerSettings.setIndentLevel(0);
			LoggerSettings.logger.log(Level.FINE, "Application program to access Students from Mongo DB\n");

			/*******************
			 * 
			 * Create a Mongo DB connection client
			 * 
			 ******************/
			
			LoggerSettings.logger.log(Level.FINE, "Read the connection Info\n");
			DBDefinition dbDefinition = DBConnector.getInstance().getDBDefinition("MongoConection1");
			// TODO LIST:
			// config.xml, DB Type - DBConnector will per user. Implement a "interface".
			// Multi-tenant system
			// Specify the specific installation

			/*******************
			 * 
			 * Create a Table connector based on a annotated Java class : Student
			 * 
			 ******************/
			LoggerSettings.logger.log(Level.FINE, "Initializing Table connector: \n");

			TableConnector tableConnector = TableConnectionHelper.getInstance().getTableConnector(dbDefinition, Student.class);

			/*******************
			 * 
			 * Retrieve the DB rows/documents as Java POJO "Student" objects
			 * 
			 ******************/
			while (tableConnector.hasNextObject()) {
				Student s = (Student) tableConnector.getNextObject();

				students.add(s);
			}

			/*******************
			 * 
			 * Print the students
			 * 
			 ******************/
			LoggerSettings.setIndentLevel(0);
			LoggerSettings.logger.log(Level.FINE,"\nAll STUDENTS : \n");
			for (Student student : students) {		
				LoggerSettings.logger.log(Level.FINE, "\tStudent: " + student);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


