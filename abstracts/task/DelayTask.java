package vcsc.core.abstracts.task;


import java.util.Collections;
import java.util.Set;

public class DelayTask implements Task {
    long startTime;
    long delay;
    boolean finished = false;

    public DelayTask(long delay) {
        this.delay = delay;
    }

    @Override
    public boolean start() {
        System.out.println("[DelayTask::start] Delaying for " + delay + " milliseconds");
        startTime = System.currentTimeMillis();
        finished = false;
        return true;
    }

    @Override
    public void loop() {
        if (System.currentTimeMillis() - startTime >= delay) {
            finished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void cancel() {
        System.out.println("[DelayTask::cancel] Delay canceled.");
        finished = true;
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public boolean conflictsWith(Task other) {
        return false;
    }

    @Override
    public Set<Class<?>> requirements() {
        return Collections.emptySet();
    }
}
