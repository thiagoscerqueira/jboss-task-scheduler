package br.com.company.scheduler.timer;

import org.jboss.logging.Logger;
import org.jboss.msc.service.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.concurrent.atomic.AtomicBoolean;

public class HATimerService implements Service<String> {

	private static final Logger LOGGER = Logger.getLogger(HATimerService.class);

	public static final ServiceName SINGLETON_SERVICE_NAME = ServiceName.JBOSS.append("scheduler", "ha", "singleton",
			"timer");

	private final AtomicBoolean started = new AtomicBoolean(false);

	public static final String EXECUTOR_INDICATOR_CLASS_LOOKUP =
			"java:global/jboss-task-scheduler/HATimerExecutorIndicator!br.com.company.scheduler.timer.HATimerExecutorIndicator";

	public String getValue() throws IllegalStateException, IllegalArgumentException {
		LOGGER.infof("%s is %s at %s", HATimerService.class.getSimpleName(),
				(started.get() ? "started" : "not started"), System.getProperty("jboss.node.name"));
		return "";
	}

	public void start(StartContext arg0) throws StartException {
		if (!started.compareAndSet(false, true)) {
			throw new StartException("The job is still started!");
		}

		LOGGER.info("Start HASingleton timer job '" + this.getClass().getName() + "'");
		
		try {
			getExecutorIndicator(new InitialContext()).setAsExecutor();
		} catch (NamingException e) {
			throw new StartException("Could not initialize timer", e);
		}
	}

	public void stop(StopContext arg0) {
		if (!started.compareAndSet(true, false)) {
			LOGGER.warn("The job '" + this.getClass().getName() + "' is not active!");
		} else {
			LOGGER.info("Stop HASingleton timer job '" + this.getClass().getName() + "'");
			try {
				getExecutorIndicator(new InitialContext()).unSetAsExecutor();
			} catch (NamingException e) {
				LOGGER.error("Could not stop timer", e);
			}
		}
	}

	private HATimerExecutorIndicator getExecutorIndicator(InitialContext ic) throws NamingException {
		HATimerExecutorIndicator job = (HATimerExecutorIndicator) ic.lookup(EXECUTOR_INDICATOR_CLASS_LOOKUP);
		return job;
	}
}
