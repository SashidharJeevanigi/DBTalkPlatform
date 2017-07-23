package talkmongo;

import talkmongo.representation.dbinterface.TableConnector;
import talkmongo.representation.MongoDBDefinition;
import talkmongo.representation.MongoTableConnector;
import talkmongo.representation.dbinterface.DBDefinition;

public class TableConnectionHelper {
	
	private static TableConnectionHelper instance = null;
	
	protected TableConnectionHelper(){
				
	}
	
	public static TableConnectionHelper getInstance(){
		if (instance == null)
		{
			instance = new TableConnectionHelper();
		}
		return instance;
	}

	
	public  TableConnector getTableConnector(DBDefinition dbDefinition,Class entityObjectClass) {
		TableConnector tableConnector=null;
		
		//For Mongo DB 
		if(MongoDBDefinition.class.isAssignableFrom(dbDefinition.getClass())) {
			tableConnector = new MongoTableConnector(dbDefinition, entityObjectClass);
		}
		
		//TODO - Handle other DBs
		
		return tableConnector;
	}

}
