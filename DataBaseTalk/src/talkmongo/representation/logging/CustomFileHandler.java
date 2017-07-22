
package talkmongo.representation.logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.LogRecord;


public class CustomFileHandler extends FileHandler{

	
	public CustomFileHandler(String pattern) throws IOException,
			SecurityException {
		super(pattern);
	}

	/* (non-Javadoc)
	 * @see java.util.logging.FileHandler#publish(java.util.logging.LogRecord)
	 */
	@Override
	public synchronized void publish(LogRecord record) {
		if (!isLoggable(record)) {
		    return;
		}
		System.out.println(getFormatter().format(record));
		super.publish(record);
	}
	
	

}
