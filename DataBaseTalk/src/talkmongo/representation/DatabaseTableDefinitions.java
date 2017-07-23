package talkmongo.representation;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import talkmongo.representation.TableDefinition;
import talkmongo.representation.annotations.Column;
import talkmongo.representation.annotations.Table;
import talkmongo.representation.Entity;

public class DatabaseTableDefinitions {
	private Map<Class<? extends Entity>, TableDefinition> entityToTableDefinitionMap;
	//private static DatabaseTableDefinitions instance = null;
	
	protected DatabaseTableDefinitions()
	{
		entityToTableDefinitionMap = new HashMap<Class<? extends Entity>, TableDefinition>();
		
	}

	/*public static DatabaseTableDefinitions getInstance()
	{
		if (instance == null)
		{
			instance = new DatabaseTableDefinitions();
		}
		return instance;
	}*/

	public <T extends Entity> TableDefinition getTableDefinition(Class<T> userDefinedEntityClass) 
	{
		TableDefinition tableDef = entityToTableDefinitionMap.get(userDefinedEntityClass);
		if (tableDef == null)
		{
			addTableDefinition(userDefinedEntityClass);
			return entityToTableDefinitionMap.get(userDefinedEntityClass);
		}
		return tableDef;
	}

	
	private <T extends Entity> void addTableDefinition(Class<T> userDefinedEntityClass)
	{
		TableDefinition tableDef = new TableDefinition();

		Table tableDefinitionByClass = userDefinedEntityClass.getAnnotation(Table.class);
		tableDef.setTableName(tableDefinitionByClass.tableName());

		Field[] fields = userDefinedEntityClass.getDeclaredFields();
		for (Field field : fields)
		{
			Column column = field.getAnnotation(Column.class);
			if (column != null)
			{
				String columnName = column.columnName();
				String fieldName = field.getName();
				tableDef.addToFieldNameToDBColumnMap(fieldName, column);
				tableDef.addToFieldNameToDBColumnNameMap(fieldName, columnName);
				tableDef.addToDBcolumnNameToFieldNameMap(columnName, fieldName);
				tableDef.addToFieldNameToFieldMap(fieldName, field);
				
			}
		}
		
		entityToTableDefinitionMap.put(userDefinedEntityClass, tableDef);
	}

}
