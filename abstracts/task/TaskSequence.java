package vcsc.core.abstracts.task;

import com.pedropathing.follower.Follower;
import com.pedropathing.pathgen.PathChain;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Supplier;

public class TaskSequence implements Task {
    ArrayList<Task> tasks = new ArrayList<>();
    ArrayList<Task> runningTasks = new ArrayList<>();
    int currentTaskIndex = 0;
    boolean isRunning = false;
    boolean stopOnFail = true; // Default behavior to stop on failure

    public TaskSequence(Task... tasks) {
        for (Task task : tasks) {
            this.then(task);
        }
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

    public TaskSequence thenFollowPath(Follower follower, PathChain pathChain, boolean holdEnd) {
        tasks.add(new FollowPathTask(follower, pathChain, holdEnd));
        return this;
    }

    public TaskSequence thenFollowPath(Follower follower, PathChain pathChain) {
        tasks.add(new FollowPathTask(follower, pathChain));
        return this;
    }

    public TaskSequence thenLog(String message) {
        tasks.add(new LogTask(message));
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
        System.out.println("[TaskSequence::start] Task sequence starting with " + tasks.size() + " tasks:");
        for (Task task : tasks) {
            System.out.println("     " + task.getClass().getSimpleName());
        }

        boolean started = tasks.get(currentTaskIndex).start();
        if (started) {
            runningTasks.add(tasks.get(currentTaskIndex));
            System.out.println("[TaskSequence::start] Task sequence started successfully.");
        } else {
            System.out.println("[TaskSequence::start] Task sequence failed to start.");
        }
        return started;
    }

    private boolean startNext() {
        // TODO: Make sure it doesn't have any conflicts before starting... somehow
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

    public ArrayList<Task> getTasks() {
        return tasks;
    }


    public void loop() {
        System.out.println("[TaskSequence::loop] Looping through tasks:");
        if (!isRunning || currentTaskIndex >= tasks.size()) {
            System.out.println("     Task sequence not running");
            isRunning = false;
            return;
        }

        Task currentTask = tasks.get(currentTaskIndex);
        System.out.println("     Currently processing task #" + currentTaskIndex + ": " + currentTask.getClass().getSimpleName());
        if (currentTask.isFinished() || currentTask.isAsync()) {
            if (!startNext()) {
                isRunning = false; // No more tasks to run
            }
        }

        ArrayList<Task> toRemove = new ArrayList<>();
        for (Task task : runningTasks) {
            task.loop();
            if (task.isFinished()) {
                System.out.println("     Task finished: " + task.getClass().getSimpleName());
                toRemove.add(task);
            }
        }
        runningTasks.removeAll(toRemove);
    }

    public boolean isFinished() {
        return !isRunning;
    }

    public void cancel() {
        System.out.println("[TaskSequence::cancel] Canceling task sequence:");
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
        return tasks.get(0).requirements();
//        HashSet<Class<?>> requirements = new HashSet<>();
//        for (Task task : tasks) {
//            requirements.addAll(task.requirements());
//        }
//        return requirements;
    }
}
