package br.com.company.scheduler.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BananaJob implements IJob {

    @Override
    @Scheduled(cron = "*/5 * * * * *")
    public void execute() {
        System.out.println("Hi, Banana job!");
    }
}
