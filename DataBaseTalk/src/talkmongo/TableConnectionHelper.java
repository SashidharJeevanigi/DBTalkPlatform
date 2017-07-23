package talkmongo.representation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.logging.Level;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import talkmongo.representation.dbinterface.TableConnector;
import talkmongo.representation.dbinterface.DBDefinition;
import talkmongo.representation.logging.LoggerSettings;

public class TableConnectionHelper {
	
	private static TableConnectionHelper instance = null;
	
	protected TableConnectionHelper()
	{
		
		
	}
	
	public static TableConnectionHelper getInstance()
	{
		if (instance == null)
		{
			instance = new TableConnectionHelper();
		}
		return instance;
	}

	
	public  TableConnector getTableConnector(DBDefinition dbDefinition,Class entityObjectClass) {
		TableConnector tableConnector=null;
		if(MongoDBDefinition.class.isAssignableFrom(dbDefinition.getClass())) {
			tableConnector = new MongoTableConnector(dbDefinition, entityObjectClass);
		}
		
		return tableConnector;
	}

}
