
package talkmongo.representation.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomFormatter extends Formatter{

	 private StringBuilder logForWebservice = new StringBuilder();
	 private final static String format = "{0,date} {0,time}";
	 private MessageFormat formatter;
     private Object args[] = new Object[1];
     Date dat = new Date();

	    // Line separator string.  This is the value of the line.separator
	    // property at the moment that the SimpleFormatter was created.
       //	    private String lineSeparator = "\n" ;

	 public synchronized String format(LogRecord record) {
			StringBuffer sb = new StringBuffer();

			// Minimize memory allocations here.
			dat.setTime(record.getMillis());
			args[0] = dat;
			StringBuffer text = new StringBuffer();
			if (formatter == null) {
			    formatter = new MessageFormat(format);
			}
			//formatter.format(args, text, null);

			int length = String.valueOf(LoggerSettings.getIndentLevel()).length();

			if(LoggerSettings.getIndentLevel() != 0){
				for(int ii = 0 ; ii < length ; ii ++){
					sb.append(" ");
				}
			}

			sb.append(LoggerSettings.indent().toString());
			sb.append(text);
			sb.append(" ");
			if (record.getSourceClassName() != null) {
			    //sb.append(record.getSourceClassName());
			} else {
			    sb.append(record.getLoggerName());
			}
			if (record.getSourceMethodName() != null) {
			    sb.append(" ");
			    //sb.append(record.getSourceMethodName());
			}
			//sb.append(lineSeparator);
			if(LoggerSettings.getIndentLevel() > 0){
				sb.append(LoggerSettings.getIndentLevel()+ LoggerSettings.indent().toString());
			}
			else{
				LoggerSettings.indent().toString();
			}

			String message = formatMessage(record);
			//sb.append(record.getLevel().getLocalizedName());
			//sb.append(": ");
			sb.append(message);
			//sb.append(lineSeparator);
			if (record.getThrown() != null) {
			    try {
			        StringWriter sw = new StringWriter();
			        PrintWriter pw = new PrintWriter(sw);
			        record.getThrown().printStackTrace(pw);
			        pw.close();
				sb.append(sw.toString());
			    } catch (Exception ex) {
			    }
			}
			sb.append("\n");
			logForWebservice.append(sb.toString());
			return sb.toString();
		    }

}
