package vcsc.core.abstracts.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
    System.out.println("[ParallelTask::start] Starting ParallelTask with children:");
    boolean success = true;
    for (Task task : _tasks) {
      success = task.start();
      System.out.println("     " + task.getClass().getSimpleName() + " started: " + success);
    }
    return success;
  }

  @Override
  public void loop() {
    System.out.println("[ParallelTask::loop] Looping ParallelTask with children:");
    for (Task task : _tasks) {
      if (task.isFinished()) {
        System.out.println("     " + task.getClass().getSimpleName() + " finished.");
      } else {
        System.out.println("     " + task.getClass().getSimpleName() + " not finished.");
      }
      task.loop();
    }
  }

  @Override
  public boolean isFinished() {
    for (Task task : _tasks) {
      if (!task.isFinished()) {
        System.out.println("(ParallelTask) Task not finished: " + task.getClass().getSimpleName());
        return false;
      }
    }
    return true;
  }

  @Override
  public void cancel() {
    System.out.println("[ParallelTask::cancel] Canceling ParallelTask with children:");
    for (Task task : _tasks) {
      System.out.println("     " + task.getClass().getSimpleName() + " canceled.");
      task.cancel();
    }
  }

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public boolean conflictsWith(Task other) {
    for (Task task : _tasks) {
      if (task.conflictsWith(other)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Set<Class<?>> requirements() {
    HashSet<Class<?>> requirements = new HashSet<>();
    for (Task task : _tasks) {
      requirements.addAll(task.requirements());
    }
    return requirements;
  }
}
