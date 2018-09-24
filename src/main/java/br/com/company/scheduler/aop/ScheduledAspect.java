package br.com.company.scheduler.aop;

import br.com.company.scheduler.timer.HATimerExecutorIndicator;
import br.com.company.scheduler.timer.HATimerService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Aspect
@Component
public class ScheduledAspect {

    @Resource(mappedName = HATimerService.EXECUTOR_INDICATOR_CLASS_LOOKUP)
    private HATimerExecutorIndicator executorIndicator;

    @Around(value = "execution(  * br.com.company.scheduler.job.*.* (..) ) && @annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void scheduledAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (executorIndicator.isExecutor()) {
            joinPoint.proceed();
        }
    }


}

