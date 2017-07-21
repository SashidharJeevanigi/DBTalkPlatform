package test.basictests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.lang.Integer;
import talkmongo.representation.TableDefinition;
import talkmongo.representation.DatabaseTableDefinitions;
import talkmongo.representation.MongoDBConnection;

import test.Student;
import talkmongo.representation.Entity;
import talkmongo.representation.annotations.Column;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class TestDriverFullFlow {

	public static void main(String args[]) {
		
		//DBConnector dbc = new DBConnector(host,port);
		
		//TableConnector tbc = new TableConnector(table);
		
		//RowItertator ri = tbc.rowIterator(queryProjection);
		
		//CompleteResultSet 
		
		//Examples of using row Iterator
		/**********************************
		 * 
		 * 1. while(rowIterator.next()){
		 * 		SysOUt ri;
		 * 		}
		 * 
		 * 2. while(rowIterator.next()){
		 * 		SysOUt ri.get("name") + " " ri.get("age");
		 * 		}
		 * 
		 * 2.while (rowIterator.next()){
		 * 		Employee emp = (Employee)ri.getObject();
		 *  	
		 *  	int years = emp.getYearsOfService();	
		 * }
		 * 
		 */
		
		try {

			long start = 0;
			long end = 0;
			System.out.println("Connect to database successfully... 111");
			
			/*******************
			 *DBConnector dbc = new DBConnector(host,port);
			 * 
			 * 			 
			 **************/
			TableDefinition tableDef = DatabaseTableDefinitions.getInstance().getTableDefinition(Student.class);
			System.out.println("Table Def: \n" + tableDef);
			
			start = System.currentTimeMillis();
			MongoDBConnection dbConnection = new MongoDBConnection("localhost", 27017);
			end = System.currentTimeMillis();
			System.out.println("New Mongo - Time : " + (end - start) + " MS");

			/*****************
			 * //TableConnector tbc = new TableConnector(table);
			 * 
			 *******************/
			
			String tableName = tableDef.getTableName();
			// Now connect to your databases
			start = System.currentTimeMillis();
			DB db = dbConnection.getMongoClient().getDB(tableName);
			end = System.currentTimeMillis();
			System.out.println("Connect to database successfully : "
					+ (end - start) + " MS");
			
			
			start = System.currentTimeMillis();
			DBCollection coll = db.getCollection(tableName);
			end = System.currentTimeMillis();
			System.out.println("Collection mycol selected successfully : "
					+ (end - start) + " MS");

			
			// GET ALL THE ROWS IN THE TABLE - test
			start = System.currentTimeMillis();

			Map<String, String> fieldNameToDBColumnNameMap = tableDef.getFieldNameToDBColumnNameMap();
			Map<String, Field> fieldNameToFieldMap = tableDef.getFieldNameToFieldMap();
			
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

					Object object= null;
					//CREATE  a new instance of the class
					//Student student = new Student();
					try{
						String className = Student.class.getName();
						Class<?> clazz = Class.forName(className);
						Constructor<?> constructor = clazz.getConstructor();
						object = constructor.newInstance();
					} catch (Exception e){
						e.printStackTrace();
					}
					

					//LOOP OVER THE fields of the Object
					for (String fieldName : fieldNameToFieldMap.keySet()){
						
						//GET the mapped DB column name for  this feild of the Object
						String dbColumnName = fieldNameToDBColumnNameMap.get(fieldName);
						
						//GET value of that DB column for this record/row in DB
						Object valueFromDbColumn = studentObj.get(dbColumnName);
						
						//SET the value from DB to the Object feild
						Field objectField = fieldNameToFieldMap.get(fieldName);
						if(fieldName.equals("id")){
							objectField.set(object, i);
						}
						/*else if(fieldName.equals("age")){
							int ageInt = Integer.parseInt(age);
							objectField.set(object, ageInt);
						}*/
						else{
							setFieldValue(object, objectField, valueFromDbColumn);
						}
						
						//
					}
					int ageInt = Integer.parseInt(age);
					
					Student student = (Student)object;
					/*student.setFirstName(firstName);
					student.setLastName(lastName);
					student.setAge(ageInt);
					student.setGender(gender);
					student.setId(i);*/

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
	public void makeAnInstance(){
	
		try{
			String className = Student.class.getName();
			Class<?> clazz = Class.forName(className);
			Constructor<?> ctor = clazz.getConstructor();
			Object object = ctor.newInstance();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * Sets a field value on a given object
	 *
	 * @param targetObject the object to set the field value on
	 * @param fieldName    exact name of the field
	 * @param fieldValue   value to set on the field
	 * @return true if the value was successfully set, false otherwise
	 */
	public static boolean setField(Object targetObject, String fieldName, Object fieldValue) {
	    Field field;
	    try {
	        field = targetObject.getClass().getDeclaredField(fieldName);
	    } catch (NoSuchFieldException e) {
	        field = null;
	    }
	    Class superClass = targetObject.getClass().getSuperclass();
	    while (field == null && superClass != null) {
	        try {
	            field = superClass.getDeclaredField(fieldName);
	        } catch (NoSuchFieldException e) {
	            superClass = superClass.getSuperclass();
	        }
	    }
	    if (field == null) {
	        return false;
	    }
	    field.setAccessible(true);
	    try {
	        field.set(targetObject, fieldValue);
	        return true;
	    } catch (IllegalAccessException e) {
	        return false;
	    }
	}
	
	public static boolean setFieldValue(Object targetObject, Field objectField, Object fieldValue) {
		try {
			System.out.println("Name : "+ objectField.getName()+ "\n" + "Feild ==== "+objectField+ "\n"+ fieldValue);
			
			if (objectField.getType() == int.class){
				System.out.println("**** int Contition True");
				String stringValue = (String)fieldValue;
				int intValue = Integer.parseInt(stringValue);
				objectField.set(targetObject,intValue );
			}else if(objectField.getType() ==  double.class){
				String stringValue = (String)fieldValue;
				double doubleValue = Double.parseDouble(stringValue);
				objectField.set(targetObject, doubleValue);
			}else if (objectField.getType() == String.class){				
				System.out.println("**** String Contition True");
				objectField.set(targetObject, (String)fieldValue);
			}
		}catch (IllegalAccessException e) {
			e.printStackTrace();
	        return false;
	    }
		return true;
	}
	
}