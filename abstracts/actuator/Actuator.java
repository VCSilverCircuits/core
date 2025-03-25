package vcsc.core.abstracts.actuator;

import vcsc.core.abstracts.state.State;

public abstract class Actuator<S extends State<S>> {
    protected boolean _inAction;

    public void init() {

    }

    public abstract void loop();

    public boolean
    idle() {
        return _inAction;
    }

    protected void setInAction(boolean inAction) {
        _inAction = inAction;
    }

    public abstract void updateState(State<S> newState);
}
