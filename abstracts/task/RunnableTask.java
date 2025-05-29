package vcsc.core.abstracts.task;

import java.util.Collections;
import java.util.Set;

public class RunnableTask implements Task {
  Runnable runnable;

  public RunnableTask(Runnable runnable) {
    this.runnable = runnable;
  }

  @Override
  public boolean start() {
    if (runnable != null) {
      runnable.run();
      return true;
    }
    return false;
  }

  @Override
  public void loop() {
    // No loop needed for a simple runnable
  }

  @Override
  public boolean isFinished() {
    return true;
  }

  @Override
  public void cancel() {
    // No cancellation needed for a simple runnable
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
