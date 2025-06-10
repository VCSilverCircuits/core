package org.firstinspires.ftc.teamcode.vcsc.core.abstracts.templates.rotator;

import org.firstinspires.ftc.teamcode.vcsc.core.abstracts.action.Action;
import org.firstinspires.ftc.teamcode.vcsc.core.abstracts.state.State;

public class RotatorState<S extends RotatorState<S, P>, P extends RotatorPose> extends State<S> {
  double angle;
  P pose;

  public RotatorState() {
    super();
  }

  public double getAngle() {
    return angle;
  }

  public void setAngle(Action<S> action, double angle) {
    assertLock(action);
    this.angle = angle;
    pose = null;
    notifyActuators();
  }

  public void setPose(Action<S> action, P pose) {
    assertLock(action);
    setAngle(action, pose.getAngle());
    this.pose = pose;
  }

  public P getPose() {
    return pose;
  }
}
