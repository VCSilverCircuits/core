package vcsc.core.abstracts.task;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import java.util.ArrayList;
import java.util.List;

import vcsc.core.util.GlobalTelemetry;

public class TaskManager {
    static final TaskManager instance = new TaskManager();
    ArrayList<Task> runningTasks = new ArrayList<>();

    public static TaskManager getInstance() {
        return instance;
    }

    public boolean runTask(Task task) {
        for (Task other : runningTasks) {
            if (task.conflictsWith(other)) {
                System.out.println("[TaskManager::runTask] WARNING: Task " + task.getClass().getSimpleName() + " conflicts with task " + other.getClass().getSimpleName() + ". Cancelling " + other.getClass().getSimpleName());
                cancelTask(other);
                //return false;
            }
        }

        clearTasks();

        boolean started = task.start();
        if (started) {
            runningTasks.add(task);
            System.out.println("[TaskManager::runTask] Successfully started task: " + task.getClass().getSimpleName());
        } else {
            System.out.println("[TaskManager::runTask] Failed to start task: " + task.getClass().getSimpleName());
        }
        return started;
    }

    public void clearTasks() {
        System.out.println("[TaskManager::clearTasks] Clearing all tasks");
        for (Task task : runningTasks) {
            System.out.println("[TaskManager::clearTasks] Canceling task: " + task.getClass().getSimpleName());
            task.cancel();
        }
        runningTasks.clear();
    }

    public void cancelTask(Task task) {
        System.out.println("[TaskManager::cancelTask] Canceling task: " + task.getClass().getSimpleName());
        task.cancel();
        runningTasks.remove(task);
    }

    public void loop() {
        List<Task> toRemove = new ArrayList<>();
        MultipleTelemetry telemetry = GlobalTelemetry.getInstance();
        telemetry.addLine("===== Starting task cycle =====");
        telemetry.addData("Currently running tasks", runningTasks.size());

        System.out.println("[TaskManager::loop] Currently running tasks (" + runningTasks.size() + "):");

        for (Task task : runningTasks) {
            System.out.println("     " + task.getClass().getSimpleName());
            task.loop();
            telemetry.addLine("Updating task: " + task.getClass().getSimpleName());
            if (task.isFinished()) {
                System.out.println("[TaskManager::loop] Task finished: " + task.getClass().getSimpleName() + ". Scheduling for removal.");
                toRemove.add(task);
                telemetry.addLine("Task finished: " + task.getClass().getSimpleName());
            }
        }
        runningTasks.removeAll(toRemove);
    }
}
