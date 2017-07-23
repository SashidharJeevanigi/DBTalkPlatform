package talkmongo.representation;

import talkmongo.representation.dbinterface.DBDefinition;
import talkmongo.representation.dbinterface.DBConnection;
import talkmongo.representation.MongoDBConnection;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


public class DBConnector {
	
	private Map<String, DBDefinition> dbConnectionNameToDBDefinitionMap;
	String dbConnectionsFileName = "DBConnections.xml"; 
	private static DBConnector instance = null;
	
	protected DBConnector()
	{
		dbConnectionNameToDBDefinitionMap = new HashMap<String, DBDefinition>();
		
	}
	
	public static DBConnector getInstance()
	{
		if (instance == null)
		{
			instance = new DBConnector();
		}
		return instance;
	}


	public Map<String, DBDefinition> getDbConnectionNameToDBConnectionMap() {
		return dbConnectionNameToDBDefinitionMap;
	}

	public void setDbConnectionNameToDBConnectionMap(Map<String, DBDefinition> dbConnectionNameToDBConnectionMap) {
		this.dbConnectionNameToDBDefinitionMap = dbConnectionNameToDBConnectionMap;
	}

	DBConnector(String DatabaseName){
		
	}
	
	public DBDefinition getDBDefinition(String dbConnectionName) 
	{
		DBDefinition dbDefinition = dbConnectionNameToDBDefinitionMap.get(dbConnectionName);
		if (dbDefinition == null)
		{
			readAndStoreDBConnections(dbConnectionName);
			dbDefinition = dbConnectionNameToDBDefinitionMap.get(dbConnectionName);
		}
		
		return dbDefinition;
	}
	
	public DBConnection getNewDBConnection(String dbConnectionName) 
	{
		
		DBDefinition dbDefinition = getDBDefinition(dbConnectionName);
		
		DBConnection dbConnection = null;
		if(MongoDBDefinition.class.isAssignableFrom(dbDefinition.getClass())) {
			dbConnection = ((MongoDBDefinition)dbDefinition).getNewMongoConnection();	
		}
		
		return dbConnection;
	}
	
	

	public void readAndStoreDBConnections(String dbConnectionName){
		try {	
	         File inputFile = new File(dbConnectionsFileName);
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	         NodeList nList = doc.getElementsByTagName("db-connection");
	         System.out.println("----------------------------");
	         
	         for (int temp = 0; temp < nList.getLength(); temp++) {
	         
	        	Node nNode = nList.item(temp);
	            	           
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	            	
	               Element element = (Element) nNode;
	               	               
	               String dbType = element.getAttribute("db-type");
	               String dbConnectionNameInFile = element.getAttribute("db-name");
	               
	               if(dbConnectionNameInFile.equals(dbConnectionName)) {

	            	   DBDefinition dbDefinition = null;
	            	   
	            	   //TODO: HANDLE ALL THE DB TYPES HERE
	            	   
	            	   if(dbType.equals("MONGODB")) {
	            		   dbDefinition = getMongoDBDefinition(element);	            	
		            	   System.out.println("MONGO DB *****  : " + dbDefinition);
		               }
	            	   
	            	  
	            	   dbConnectionNameToDBDefinitionMap.put(dbConnectionName, dbDefinition);
	            	   
	               }
	            
	               
	               //System.out.println("\tport : " + getTagValue("port", eElement));
	               	               
	            }	            
	         }

	      } catch (Exception e) {
	         e.printStackTrace();
	      }

	}
	
	

	private static MongoDBDefinition getMongoDBDefinition(Element element) {
	   String dbName = element.getAttribute("db-name");
  	   String hostname = getTagValue("host", element);
  	   int port = Integer.parseInt(getTagValue("port", element));
  	   MongoDBDefinition dbDefinition = new MongoDBDefinition(dbName,hostname,port);
  	   
  	   return dbDefinition;
	}
	
	private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
	
	public static void main(String args[]) {
		try {	
	         File inputFile = new File("DBConnections.xml");
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	         NodeList nList = doc.getElementsByTagName("db-connection");
	         System.out.println("----------------------------");
	         for (int temp = 0; temp < nList.getLength(); temp++) {
	            Node nNode = nList.item(temp);
	            
	            //System.out.println("\nCurrent Element :" + nNode.getNodeName()  + " ***" + nNode.toString());
	            
	            
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	            	
	               Element element = (Element) nNode;
	               
	               //System.out.println("\nCurrent Element :" + nNode.getNodeName()  + " ***" + element.toString());
	               
	               
	               //System.out.println("DBtype : " + element.getAttribute("db-type") +   " ***  DBName: " + element.getAttribute("db-name") );
	               
	               String dbType = element.getAttribute("db-type");
	               
	               if(dbType.equals("MONGODB")) {
	            	  // MongoDBConnection dbConnection = getMongoDBConnection(element);	            	
	            	   //System.out.println("MONGO DB *****  : " + dbConnection);
	               }
	               
	               //System.out.println("\tport : " + getTagValue("port", eElement));
	               	               
	            }	            
	         }

	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	}
}
