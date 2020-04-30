package ru.integrotech.su.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

import ru.integrotech.airline.exceptions.ErrorSupporter;
import ru.integrotech.airline.register.RegisterLoader;

import com.ibm.rules.engine.ruledef.runtime.CustomExceptionHandler;
import com.ibm.rules.engine.ruledef.runtime.RuleInstance;

public class SURulesCustomExceptionHandler implements CustomExceptionHandler {

	private static final Logger log = Logger.getLogger(RegisterLoader.class
			.getName());

	public SURulesCustomExceptionHandler() {

	}

	@Override
	public void handleActionException(Exception e, RuleInstance rule)
			throws Exception {

		log.log(Level.WARNING, e.getMessage());
		RegisterLoader.getInstance("").release();
		log.log(Level.WARNING,
				ErrorSupporter.DEFAULT_ERROR_MSG
						+ e.getMessage()
						+ "   Cache  was released successfully by CustomExceptionHandler");
		throw e;
	}

	@Override
	public void handleConditionException(Exception e) throws Exception {
		log.log(Level.WARNING, e.getMessage());
		RegisterLoader.getInstance("").release();
		log.log(Level.WARNING,
				e.getMessage()
						+ "  Cache  was released successfully by CustomExceptionHandler");
		throw e;
	}

}
