package vcsc.core.abstracts.action;

import vcsc.core.abstracts.state.State;
import vcsc.core.abstracts.state.StateRegistry;

public abstract class Action<S extends State<S>>  {
    protected S state;

    public Action(Class<S> stateClass) {
        StateRegistry reg = StateRegistry.getInstance();
        this.state = reg.getState(stateClass);
    }

    // Start running
    public boolean start() {
        return state.tryLock(this);
    }

    private void end() {
        state.releaseLock(this);
    }

    // Perform updates if needed
    public abstract void loop();

    // Check if finished
    public abstract boolean isFinished();

    public void cancel() {
        state.cancelAction();
    }
}
