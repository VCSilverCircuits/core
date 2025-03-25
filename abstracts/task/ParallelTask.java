package vcsc.core.abstracts.task;

import java.util.ArrayList;
import java.util.Collections;

public class ParallelTask implements Task {
    final ArrayList<Task> _tasks = new ArrayList<>();
    boolean stopOnFail = true;

    public ParallelTask(Task... tasks) {
        Collections.addAll(_tasks, tasks);
    }

    public ParallelTask(boolean stopOnFail, Task... tasks) {
        this.stopOnFail = stopOnFail;
        Collections.addAll(_tasks, tasks);
    }

    @Override
    public boolean start() {
        boolean success = true;
        for (Task task : _tasks) {
            success = task.start();
        }
        return success;
    }

    @Override
    public void loop() {
        for (Task task : _tasks) {
            task.loop();
        }
    }

    @Override
    public boolean isFinished() {
        for (Task task : _tasks) {
            if (!task.isFinished()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void cancel() {
        for (Task task : _tasks) {
            task.cancel();
        }
    }

    @Override
    public boolean isAsync() {
        return false;
    }
}
