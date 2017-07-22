package talkmongo.representation.logging;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerSettings {

	private static int indentLevel_;
	public static Logger logger = Logger.getLogger("DBTALK_LOGGER");

	public static int getIndentLevel() {
		return indentLevel_;
	}

	public static void setIndentLevel(int indentLevel) {
		LoggerSettings.indentLevel_ = indentLevel;
	}
	
	   public static final StringBuffer indent() {
	    	StringBuffer buffer = new StringBuffer();
	    	for (int i = 0; i < indentLevel_ ; i++)
	        	buffer = buffer.append("  ");
	        return buffer ;
	    }


	public static void configureLog(int logLevel , String logFileName) {
		switch(logLevel){
		case 0 :
			setLogLevel(Level.OFF);
			return ;
		case 1 :
			setLogLevel(Level.SEVERE);
			break;
		case 2 :
			setLogLevel(Level.WARNING);
			break;
		case 4 :
			setLogLevel(Level.INFO);
			break;
		case 5 :
			setLogLevel(Level.FINE);
			break;
		case 6 :
			setLogLevel(Level.FINER);
			break;
		case 7 :
			setLogLevel(Level.FINEST);
			break;

		default :
			setLogLevel(Level.OFF);
			return ;
		}

		try {
		    //if( !Util.isValidPath(logFileName) ){
			CustomFileHandler fileHandler = new CustomFileHandler(logFileName);
			fileHandler.setFormatter(new CustomFormatter());
			logger.addHandler(fileHandler);
			logger.setUseParentHandlers(false);
			
			/*CustomHandler customHandler = new CustomHandler(logFileName);
			customHandler.setFormatter(new CustomFormatter());
			logger.addHandler(customHandler);*/
			//}
		} catch (Exception e) {		
			e.printStackTrace();
		}
	}

	private static void setLogLevel(Level level){

		Handler[] handlers = Logger.getLogger( "" ).getHandlers();

		for ( int index = 0; index < handlers.length; index++ ) {
			handlers[index].setLevel( level);
		}

		logger.setLevel(level);


	}
	
	

}
