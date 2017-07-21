package talkmongo.representation;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import talkmongo.representation.annotations.Column;

public class TableDefinition {
	
	private String tableName;
	private Map<String, Column> fieldNameToDBColumnMap = new LinkedHashMap<String, Column>();
	private Map<String, String> fieldNameToDBColumnNameMap = new LinkedHashMap<String, String>();
	private Map<String, String> dBcolumnNameToFieldNameMap = new LinkedHashMap<String, String>();
	private Map<String, Field> fieldNameToFieldMap = new LinkedHashMap<String, Field>();
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Map<String, Column> getFieldNameToDBColumnMap() {
		return fieldNameToDBColumnMap;
	}
	public void setFieldNameToDBColumnMap(Map<String, Column> fieldNameToDBColumnMap) {
		this.fieldNameToDBColumnMap = fieldNameToDBColumnMap;
	}
	public Map<String, String> getFieldNameToDBColumnNameMap() {
		return fieldNameToDBColumnNameMap;
	}
	public void setFieldNameToDBColumnNameMap(
			Map<String, String> fieldNameToDBColumnNameMap) {
		this.fieldNameToDBColumnNameMap = fieldNameToDBColumnNameMap;
	}
	public Map<String, String> getDBcolumnNameToFieldNameMap() {
		return dBcolumnNameToFieldNameMap;
	}
	public void setDBcolumnNameToFieldNameMap(
			Map<String, String> bcolumnNameToFieldNameMap) {
		dBcolumnNameToFieldNameMap = bcolumnNameToFieldNameMap;
	}
	public Map<String, Field> getFieldNameToFieldMap() {
		return fieldNameToFieldMap;
	}
	public void setFieldNameToFieldMap(Map<String, Field> fieldNameToFieldMap) {
		this.fieldNameToFieldMap = fieldNameToFieldMap;
	}
	
	//getFieldNameToDBColumnMap
	
	public void addToFieldNameToDBColumnMap(String fieldName, Column column)
	{
		this.fieldNameToDBColumnMap.put(fieldName, column);
	}
	
	public void addToFieldNameToDBColumnNameMap(String fieldName, String dbColumnName) {
		this.fieldNameToDBColumnNameMap.put(fieldName, dbColumnName);
	}
	
	public void addToDBcolumnNameToFieldNameMap(String dbColumnName, String fieldName) {
		this.dBcolumnNameToFieldNameMap.put(dbColumnName, fieldName);
	}
	
	public void addToFieldNameToFieldMap(String fieldName, Field field) {
		this.fieldNameToFieldMap.put(fieldName,field);
	}	
	
	@Override
	public String toString(){
		String result = tableName+ "\n";
		
		result += "fieldNameToFieldMap" + "\n";
		result += fieldNameToFieldMap + "\n";
		
		result += "fieldNameToDBColumnNameMap"+ "\n";
		result += fieldNameToDBColumnNameMap + "\n";
		
		result += "FIELDS"+ "\n\n";
		int i = 1;
		for (String fieldName : fieldNameToFieldMap.keySet()){
					
			//SET the value from DB to the Object feild
			Field objectField = fieldNameToFieldMap.get(fieldName);
			result += i+ "\n"; 
			result += fieldName+ "\n";
			result += "Feild ==== "+objectField+ "\n";
			result += "GenericType: " +objectField.getGenericType()+"\n";
			result += "Type: " +objectField.getType()+"\n";
			result += "getSimpleName: " +objectField.getType().getSimpleName() + "\n";
			if (objectField.getType() == int.class){
				result += "int Condition True" + "\n" + "isPrimitive: "+ objectField.getType().isPrimitive()+ "\n";
			}else if (objectField.getType() == String.class){
				result += "String Condition True" + "\n"+ "isPrimitive: "+ objectField.getType().isPrimitive()+ "\n";
			}else {
				result += "No Match" + "\n";
			}
			result += "\n";
			i++;
			
			
			
		}
		
		return result;
	}
}
