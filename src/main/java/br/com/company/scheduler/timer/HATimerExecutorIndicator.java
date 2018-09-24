package br.com.company.scheduler.timer;

import javax.ejb.Singleton;

@Singleton
public class HATimerExecutorIndicator {

    private Boolean executor = false;

    public void setAsExecutor() {
        this.executor = true;
    }

    public void unSetAsExecutor() {
        this.executor = false;
    }

    public Boolean isExecutor() {
        return this.executor;
    }
}
