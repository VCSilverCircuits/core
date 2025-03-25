package vcsc.core.abstracts.behavior;

import vcsc.core.abstracts.task.Task;

public abstract class Behavior implements Task {
    public abstract boolean start();

    private void end() {
    }

    // Perform updates if needed
    public abstract void loop();

    // Check if finished
    public abstract boolean isFinished();

    public void cancel() {

    }

    public boolean isAsync() {
        return false;
    }
}
