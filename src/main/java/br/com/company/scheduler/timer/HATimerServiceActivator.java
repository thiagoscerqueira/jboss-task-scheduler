package br.com.company.scheduler.timer;

import org.jboss.as.clustering.singleton.SingletonService;
import org.jboss.logging.Logger;
import org.jboss.msc.service.DelegatingServiceContainer;
import org.jboss.msc.service.ServiceActivator;
import org.jboss.msc.service.ServiceActivatorContext;
import org.jboss.msc.service.ServiceController;

public class HATimerServiceActivator implements ServiceActivator {
    private final Logger log = Logger.getLogger(this.getClass());

    @Override
    public void activate(ServiceActivatorContext context) {
        log.info("HATimerService will be installed!");

        HATimerService service = new HATimerService();
        SingletonService<String> singleton = new SingletonService<String>(service, HATimerService.SINGLETON_SERVICE_NAME);

        singleton.build(new DelegatingServiceContainer(context.getServiceTarget(), context.getServiceRegistry()))
                .setInitialMode(ServiceController.Mode.ACTIVE)
                .install()
        ;
    }
}
