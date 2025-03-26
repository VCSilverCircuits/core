package vcsc.core.abstracts.task;

import java.util.ArrayList;

public class TaskManager {
    static final TaskManager instance = new TaskManager();
    ArrayList<Task> runningTasks = new ArrayList<>();

    public static TaskManager getInstance() {
        return instance;
    }

    public boolean runTask(Task task) {
        for (Task other : runningTasks) {
            if (task.conflictsWith(other)) {
                return false;
            }
        }

        boolean started = task.start();
        if (started) {
            runningTasks.add(task);
        }
        return started;
    }

    public void cancelTask(Task task) {
        task.cancel();
        runningTasks.remove(task);
    }

    public void loop() {
        for (Task task : runningTasks) {
            task.loop();
            if (task.isFinished()) {
                runningTasks.remove(task);
            }
        }
    }
}
