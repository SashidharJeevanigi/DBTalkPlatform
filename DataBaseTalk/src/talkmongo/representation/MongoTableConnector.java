package talkmongo.representation;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.lang.Integer;
import talkmongo.representation.TableDefinition;
import talkmongo.representation.DatabaseTableDefinitions;
import talkmongo.representation.MongoDBConnection;
import talkmongo.representation.dbinterface.TableConnector;
import talkmongo.representation.logging.LoggerSettings;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.logging.Level;


public class MongoTableConnector {//implements TableConnector{
	
	private Class entityObjectClass;
	private TableDefinition tableDefinition;
	private DB db;
	private DBCollection dbCollection;
	private DBCursor dbCursor;
	private MongoDBConnection dbConnection;
	
	
	public MongoTableConnector(MongoDBConnection dbConnection,Class entityObjectClass){
		this.entityObjectClass = entityObjectClass;
		this.dbConnection = dbConnection;
		LoggerSettings.setIndentLevel(2);
		LoggerSettings.logger.log(Level.FINE,"Reading table based on entityObjectClass: " +entityObjectClass.toString() +"\n");
		this.tableDefinition = DatabaseTableDefinitions.getInstance().getTableDefinition(entityObjectClass);	
		this.db = dbConnection.getMongoClient().getDB(this.tableDefinition.getTableName());
		this.dbCollection = db.getCollection(this.tableDefinition.getTableName());	
		this.dbCursor = this.dbCollection.find();
	}
	
	/***************
	 * 
	 * Create one more constructor with a QUERY
	 * 
	 */
	
	public boolean hasNextObject(){
		return this.dbCursor.hasNext();
	}
	
	public <T extends Entity> T getNextObject(){
		if(this.dbCursor.hasNext()){
			DBObject dbObject = this.dbCursor.next();
			T entityObject = getEntityObject(dbObject);
			return entityObject;			
		}else{
			return null;
		}
	}


	public <T extends Entity> T getEntityObject(DBObject dbObject){

		T entityObject = null;
		// CREATE a new instance of the class

		try{
			String className = entityObjectClass.getName();
			Class<?> clazz = Class.forName(className);
			Constructor<?> constructor = clazz.getConstructor();
			entityObject = (T)constructor.newInstance();
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}


		boolean success = true;
		// LOOP OVER THE fields of the Object
		for (String fieldName : tableDefinition.getFieldNameToFieldMap().keySet()){

			// GET the mapped DB column name for this feild of the Object
			String dbColumnName = tableDefinition.getFieldNameToDBColumnNameMap().get(fieldName);

			// GET value of that DB column for this record/row in DB
			Object valueFromDbColumn = dbObject.get(dbColumnName);

			// SET the value from DB to the Object feild
			Field objectField = tableDefinition.getFieldNameToFieldMap().get(fieldName);
			success = setFieldValue(entityObject, objectField, valueFromDbColumn);
			if (!success){
				return null;
			}
		}

		return entityObject;
	}


	
	public static boolean setFieldValue(Object targetObject, Field objectField, Object fieldValue) {
		try {
			//System.out.println("Name : "+ objectField.getName()+ "\n" + "Feild ==== "+objectField+ "\n"+ fieldValue);
			
			if (objectField.getType() == int.class){
				//System.out.println("**** int Contition True");
				String stringValue = (String)fieldValue;
				int intValue = Integer.parseInt(stringValue);
				objectField.set(targetObject,intValue );
			}else if(objectField.getType() ==  double.class){
				String stringValue = (String)fieldValue;
				double doubleValue = Double.parseDouble(stringValue);
				objectField.set(targetObject, doubleValue);
			}else if (objectField.getType() == String.class){				
				//System.out.println("**** String Contition True");
				objectField.set(targetObject, (String)fieldValue);
			}
		}catch (IllegalAccessException e) {
			e.printStackTrace();
	        return false;
	    }
		return true;
	}

}
