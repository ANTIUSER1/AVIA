package ru.integrotech.airline.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import ru.integrotech.airline.register.RegisterLoader;

public class Releaser {

	private static final Logger log = Logger.getLogger(RegisterLoader.class
			.getName());

	public static void release() {
		log.warning(" Releasing  current connection ");
		RegisterLoader.getInstance("").release();
		log.log(Level.WARNING, "   Cache  was released successfully   ");
	}

}
