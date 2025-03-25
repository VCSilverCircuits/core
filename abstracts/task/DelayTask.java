package vcsc.core.abstracts.task;


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
        return false;
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
}
