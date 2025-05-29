package vcsc.core.abstracts.action;

import vcsc.core.abstracts.state.State;

public class A_Cancel<S extends State<S>> extends Action<S> {
  public A_Cancel(Class<S> stateClass) {
    super(stateClass);
  }

  @Override
  public boolean start() {
    super.start();
    this.state.cancelAction();
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
}
