package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogHandler {

	public static Logger getLogger(Class<?> className) {
		return LogManager.getLogger(className);
	}
}