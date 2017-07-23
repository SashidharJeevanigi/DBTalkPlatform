package talkmongo.representation;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import talkmongo.representation.TableDefinition;
import talkmongo.representation.annotations.Column;
import talkmongo.representation.annotations.Table;
import talkmongo.representation.logging.LoggerSettings;
import talkmongo.representation.Entity;

public class DatabaseTableDefinitions {
	private Map<Class<? extends Entity>, TableDefinition> entityToTableDefinitionMap;
	
	protected DatabaseTableDefinitions(){
		entityToTableDefinitionMap = new HashMap<Class<? extends Entity>, TableDefinition>();		
	}

	public <T extends Entity> TableDefinition getTableDefinition(Class<T> userDefinedEntityClass){
		TableDefinition tableDef = entityToTableDefinitionMap.get(userDefinedEntityClass);
		if (tableDef == null)
		{
			addTableDefinition(userDefinedEntityClass);
			tableDef = entityToTableDefinitionMap.get(userDefinedEntityClass);
		}
		LoggerSettings.logger.log(Level.FINE,"\tTable definition obtained for table ** " + tableDef.getTableName() + " **" + " based on the Java class ** "+ userDefinedEntityClass.toString() + " **");
		return tableDef;
	}

	
	private <T extends Entity> void addTableDefinition(Class<T> userDefinedEntityClass)	{
		TableDefinition tableDef = new TableDefinition();

		Table tableDefinitionByClass = userDefinedEntityClass.getAnnotation(Table.class);
		tableDef.setTableName(tableDefinitionByClass.tableName());

		Field[] fields = userDefinedEntityClass.getDeclaredFields();
		for (Field field : fields){
			Column column = field.getAnnotation(Column.class);
			if (column != null){
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
