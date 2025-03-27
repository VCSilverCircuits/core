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
                System.out.println("Task " + task.getClass().getSimpleName() + " conflicts with task " + other.getClass().getSimpleName() + ". Cannot start.");
                other.cancel();
                //return false;
            }
        }

        boolean started = task.start();
        if (started) {
            runningTasks.add(task);
        }
        return started;
    }

    public void clearTasks() {
        for (Task task : runningTasks) {
            task.cancel();
        }
        runningTasks.clear();
    }

    public void cancelTask(Task task) {
        task.cancel();
        runningTasks.remove(task);
    }

    public void loop() {
        List<Task> toRemove = new ArrayList<>();
        MultipleTelemetry telemetry = GlobalTelemetry.getInstance();
        telemetry.addLine("===== Starting task cycle =====");
        telemetry.addData("Currently running tasks", runningTasks.size());
        for (Task task : runningTasks) {
            task.loop();
            telemetry.addLine("Running task: " + task.getClass().getSimpleName());
            if (task.isFinished()) {
                toRemove.add(task);
                telemetry.addLine("Task finished: " + task.getClass().getSimpleName());
            }
        }
        runningTasks.removeAll(toRemove);
    }
}
