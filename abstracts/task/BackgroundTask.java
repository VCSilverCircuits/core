package org.firstinspires.ftc.teamcode.vcsc.core.abstracts.task;

import java.util.Set;

public class BackgroundTask implements Task {
  Task _task;

  public BackgroundTask(Task task) {
    _task = task;
  }

  @Override
  public boolean start() {
    return _task.start();
  }

  @Override
  public void loop() {
    _task.loop();
  }

  @Override
  public boolean isFinished() {
    return _task.isFinished();
  }

  @Override
  public void cancel() {
    _task.cancel();
  }

  @Override
  public boolean isAsync() {
    return true;
  }

  @Override
  public boolean conflictsWith(Task other) {
    return _task.conflictsWith(other);
  }

  @Override
  public Set<Class<?>> requirements() {
    return _task.requirements();
  }
}
