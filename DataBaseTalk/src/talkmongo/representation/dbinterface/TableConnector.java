package talkmongo.representation.dbinterface;

import talkmongo.representation.Entity;

public interface TableConnector {
	
	public boolean hasNextObject();
	
	public <T extends Entity> T getNextObject();
}
