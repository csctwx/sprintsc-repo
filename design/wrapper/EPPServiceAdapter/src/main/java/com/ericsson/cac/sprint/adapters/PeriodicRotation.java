package com.ericsson.cac.sprint.adapters;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PeriodicRotation {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(PeriodicRotation.class);

	@Autowired
	private KeyExchangeManager manager;

	@Scheduled(fixedDelay = (10*60*1000))
	private void startTransitKeyRotationThread() {
		if (logger.isDebugEnabled()) {
			logger.debug("startTransitKeyRotationThread() - start");
		}
		if (logger.isInfoEnabled()) {
			logger.info("startTransitKeyRotationThread() - rotation keys started");
		}
		manager.getTransitProtocol().getKeyRotation().rotateKeys();
		if (logger.isInfoEnabled()) {
			logger.info("startTransitKeyRotationThread() - rotation keys completed");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("startTransitKeyRotationThread() - end");
		}
	}
}
