package org.firstinspires.ftc.teamcode.vcsc.core.abstracts.templates.poweredPIDF.actions;

import org.firstinspires.ftc.teamcode.vcsc.core.abstracts.action.Action;
import org.firstinspires.ftc.teamcode.vcsc.core.abstracts.templates.poweredPIDF.PoweredPIDFPose;
import org.firstinspires.ftc.teamcode.vcsc.core.abstracts.templates.poweredPIDF.PoweredPIDFState;

public class A_PoweredPIDFReset<S extends PoweredPIDFState<S, P>, P extends PoweredPIDFPose>
    extends Action<S> {
  public A_PoweredPIDFReset(Class<S> stateClass) {
    super(stateClass);
  }

  @Override
  public boolean start() {
    boolean started = super.start();
    if (started) {
      state.reset(this);
    }
    return started;
  }

  @Override
  public void loop() {}

  @Override
  public boolean isFinished() {
    return true;
  }
}
