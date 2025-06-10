package org.firstinspires.ftc.teamcode.vcsc.core.abstracts.templates.rotator.actions;

import org.firstinspires.ftc.teamcode.vcsc.core.abstracts.action.Action;
import org.firstinspires.ftc.teamcode.vcsc.core.abstracts.templates.rotator.RotatorPose;
import org.firstinspires.ftc.teamcode.vcsc.core.abstracts.templates.rotator.RotatorState;

public class A_SetRotatorPose<S extends RotatorState<S, P>, P extends RotatorPose>
    extends Action<S> {
  P pose;

  boolean _isFinished = true;

  public A_SetRotatorPose(Class<S> klass, P pose) {
    super(klass);
    this.pose = pose;
  }

  @Override
  public boolean start() {
    boolean started = super.start();
    if (!started) {
      return false; // If the action cannot start, return false
    }
    // Set the angle to a default value (e.g., 0 degrees)
    _isFinished = false;
    this.state.setPose(this, pose);
    return true;
  }

  @Override
  public void loop() {
    if (state.idle() && state.getPose() != null && state.getPose().equals(pose)) {
      end(); // End the action if the pose is reached
    }
  }

  @Override
  public boolean isFinished() {
    return _isFinished;
  }

  @Override
  protected void end() {
    super.end();
    _isFinished = true;
  }

  @Override
  public void cancel() {
    super.cancel();
    end();
  }
}
