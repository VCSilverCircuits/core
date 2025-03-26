package vcsc.core.abstracts.task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class TaskSequence implements Task {
    ArrayList<Task> tasks = new ArrayList<>();
    ArrayList<Task> runningTasks = new ArrayList<>();
    int currentTaskIndex = 0;
    boolean isRunning = false;
    boolean stopOnFail = true; // Default behavior to stop on failure

    public TaskSequence() {
    }

    public TaskSequence then(Task task) {
        tasks.add(task);
        return this;
    }

    public TaskSequence then(Task... tasks) {
        this.then(new ParallelTask(tasks));
        return this;
    }

    public TaskSequence thenAsync(Task task) {
        tasks.add(new BackgroundTask(task));
        return this;
    }

    public TaskSequence thenAsync(Task... tasks) {
        this.then(new BackgroundTask(new ParallelTask(tasks)));
        return this;
    }

    public TaskSequence thenDelay(long delay) {
        tasks.add(new DelayTask(delay));
        return this;
    }

    public TaskSequence thenWaitUntil(Supplier<Boolean> condition) {
        tasks.add(new UntilTask(condition));
        return this;
    }

    public boolean start() {
        if (tasks.isEmpty()) {
            return false;
        }
        currentTaskIndex = 0;
        isRunning = true;
        return tasks.get(currentTaskIndex).start();
    }

    private boolean startNext() {
        currentTaskIndex++;
        if (currentTaskIndex < tasks.size()) {
            Task nextTask = tasks.get(currentTaskIndex);
            if (nextTask.start()) {
                runningTasks.add(nextTask);
                return true; // Successfully started the next task
            } else if (stopOnFail) {
                // If the next task fails to start, we stop the sequence
                isRunning = false;
            }
        }
        return false; // No more tasks to start
    }

    public void loop() {
        if (!isRunning || currentTaskIndex >= tasks.size()) {
            return;
        }

        Task currentTask = tasks.get(currentTaskIndex);
        if (currentTask.isFinished()) {
            if (!startNext()) {
                isRunning = false; // No more tasks to run
            }
        }

        for (Task task : runningTasks) {
            task.loop();
            if (task.isFinished()) {
                runningTasks.remove(task);
            }
        }
    }

    public boolean isFinished() {
        return !isRunning;
    }

    public void cancel() {
        for (Task task : tasks) {
            task.cancel();
        }
        isRunning = false;
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public boolean conflictsWith(Task other) {
        for (Task task : tasks) {
            if (task.conflictsWith(other)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<Class<?>> requirements() {
        HashSet<Class<?>> requirements = new HashSet<>();
        for (Task task : tasks) {
            requirements.addAll(task.requirements());
        }
        return requirements;
    }
}
