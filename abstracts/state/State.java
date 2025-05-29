package vcsc.core.abstracts.state;

import java.util.ArrayList;
import vcsc.core.abstracts.action.Action;
import vcsc.core.abstracts.actuator.Actuator;

public abstract class State<S extends State<S>> {
  protected ArrayList<Actuator<S>> actuators = new ArrayList<>();
  protected Action<S> lockedBy = null;

  public boolean idle() {
    for (Actuator<S> actuator : actuators) {
      if (!actuator.idle()) {
        return false;
      }
    }
    return true;
  }

  public void registerActuator(Actuator<S> actuator) {
    actuators.add(actuator);
  }

  public boolean tryLock(Action<S> action) {
    if (lockedBy == null || lockedBy.isFinished() || true) {
      lockedBy = action;
      return true;
    }
    return false; // Another action is already controlling this state
  }

  public boolean hasLock(Action<S> action) {
    return lockedBy == action;
  }

  public void releaseLock(Action<S> action) {
    if (lockedBy == action) {
      lockedBy = null;
    }
  }

  public boolean isLocked() {
    return lockedBy != null;
  }

  protected void assertLock(Action<S> action) throws IllegalStateException {
    if (!hasLock(action)) {
      throw new IllegalStateException("Action does not have lock on this state");
    }
  }

  public void cancelAction() {
    if (lockedBy != null) {
      lockedBy.cancel();
      lockedBy = null;
    }
  }

  protected void notifyActuators() {
    for (Actuator<S> actuator : actuators) {
      actuator.updateState(this);
    }
  }
}
