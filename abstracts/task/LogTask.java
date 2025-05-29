package vcsc.core.abstracts.task;

import java.util.Collections;
import java.util.Set;

public class LogTask implements Task {
  String message;

  public LogTask(String message) {
    this.message = message;
  }

  @Override
  public boolean start() {
    System.out.println(message);
    return true;
  }

  @Override
  public void loop() {}

  @Override
  public boolean isFinished() {
    return true;
  }

  @Override
  public void cancel() {}

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
