package talkmongo.representation;

import com.mongodb.MongoClient;
import java.lang.String;
import talkmongo.representation.dbinterface.DBConnection;

public class MongoDBConnection implements DBConnection {
	MongoClient mongoClient;
	String hostName;
	int port; 
	
	
	public MongoDBConnection(String hostName, int port){ //TODO: Add authentication
		this.hostName = hostName;
		this.port = port;

		long start = System.currentTimeMillis();
		mongoClient = new MongoClient(this.hostName, this.port);
		long end = System.currentTimeMillis();
		System.out.println("New Mongo - Time : " + (end - start) + " MS");
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
	
}
