package org.firstinspires.ftc.teamcode.vcsc.core.abstracts.behavior;

import java.util.HashSet;
import java.util.Set;
import org.firstinspires.ftc.teamcode.vcsc.core.abstracts.state.State;
import org.firstinspires.ftc.teamcode.vcsc.core.abstracts.task.Task;

public abstract class Behavior implements Task {
  protected Set<Class<? extends State<?>>> requirements = new HashSet<>();

  public boolean start() {
    System.out.println("[" + this.getClass().getSimpleName() + "::start] Behavior started.");
    return true;
  }

  protected void end() {
    System.out.println("[" + this.getClass().getSimpleName() + "::end] Behavior finished.");
  }

  // Perform updates if needed
  public abstract void loop();

  // Check if finished
  public abstract boolean isFinished();

  public void cancel() {
    System.out.println("[" + this.getClass().getSimpleName() + "::cancel] Behavior canceled.");
  }

  protected final void addRequirement(Class<? extends State<?>> requirement) {
    requirements.add(requirement);
  }

  @SuppressWarnings("unchecked")
  protected final void addRequirement(Behavior behavior) {
    for (Class<?> req : behavior.requirements()) {
      addRequirement((Class<? extends State<?>>) req);
    }
  }

  protected final void addRequirements(HashSet<Class<? extends State<?>>> requirements) {
    this.requirements.addAll(requirements);
  }

  @SafeVarargs
  protected final void addRequirements(Class<? extends State<?>>... requirements) {
    for (Class<? extends State<?>> req : requirements) {
      addRequirement(req);
    }
  }

  public final Set<Class<?>> requirements() {
    return new HashSet<>(requirements);
  }

  public final boolean conflictsWith(Task other) {
    for (Class<?> req : other.requirements()) {
      if (this.requirements.contains(req)) {
        return true;
      }
    }
    return false;
  }

  public boolean isAsync() {
    return false;
  }
}
