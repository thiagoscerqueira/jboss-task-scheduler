package br.com.company.scheduler.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PineappleJob implements IJob {

    @Override
    @Scheduled(cron = "*/1 * * * * *")
    public void execute() {
        System.out.println("Hi, Pineapple job!");
    }

}
