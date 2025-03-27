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
        startTime = System.currentTimeMillis();
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
