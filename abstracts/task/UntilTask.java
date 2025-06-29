package org.firstinspires.ftc.teamcode.vcsc.core.abstracts.task;

import java.util.Collections;
import java.util.Set;
import java.util.function.Supplier;

public class UntilTask implements Task {
  Supplier<Boolean> _condition;
  boolean _finished = false;

  public UntilTask(Supplier<Boolean> condition) {
    _condition = condition;
  }

  @Override
  public boolean start() {
    System.out.println("[UntilTask::start] Waiting until condition is met.");
    _finished = false;
    return true;
  }

  @Override
  public void loop() {
    if (!_finished && _condition.get()) {
      _finished = true;
    }
  }

  @Override
  public boolean isFinished() {
    return _finished || _condition.get();
  }

  @Override
  public void cancel() {
    System.out.println("[UntilTask::cancel] UntilTask canceled.");
    _finished = true;
  }

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public boolean conflictsWith(Task other) {
    return false;
  }

  @Override
  public Set<Class<?>> requirements() {
    return Collections.emptySet();
  }
}
