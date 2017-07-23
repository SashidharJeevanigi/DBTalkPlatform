package talkmongo.representation;

import com.mongodb.MongoClient;
import talkmongo.representation.dbinterface.DBDefinition;
import java.lang.String;
import talkmongo.representation.DatabaseTableDefinitions;

public class MongoDBDefinition implements DBDefinition {
	MongoClient mongoClient;
	String hostName;
	int port;
	String connectionName;
	DatabaseTableDefinitions dbTableDefinitions;


	public DatabaseTableDefinitions getDbTableDefinitions() {
		return dbTableDefinitions;
	}

	public void setDbTableDefinitions(DatabaseTableDefinitions dbTableDefinitions) {
		this.dbTableDefinitions = dbTableDefinitions;
	}

	public String getConnectionName() {
		return connectionName;
	}

	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}

	public MongoDBDefinition(String connectionName, String hostName, int port){ //TODO: Add authentication
		this.connectionName = connectionName;
		this.hostName = hostName;
		this.port = port;
		this.dbTableDefinitions = new  DatabaseTableDefinitions();

	}

	public MongoDBConnection getNewMongoConnection() {		
		MongoDBConnection mongoDBConnection = new MongoDBConnection(this.hostName, this.port);		
		return mongoDBConnection;
	}

	public MongoClient getMongoClient() {
		return mongoClient;
	}

	public void setMongoClient(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((hostName == null) ? 0 : hostName.hashCode());
		result = prime * result + port;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final MongoDBConnection other = (MongoDBConnection) obj;
		if (hostName == null) {
			if (other.hostName != null)
				return false;
		} else if (!hostName.equals(other.hostName))
			return false;
		if (port != other.port)
			return false;
		return true;
	}

	public static int getDBConnectorKey(String hostName, int port){
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((hostName == null) ? 0 : hostName.hashCode());
		result = prime * result + port;
		return result;
	}

	@Override
	public String toString() {
		return "Connection Name: " + connectionName + " Host Name : " + hostName + " Port No : " + port;
	}
}

