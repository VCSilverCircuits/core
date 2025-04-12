package vcsc.core.abstracts.templates.poweredPIDF.actions;

import vcsc.core.abstracts.action.Action;
import vcsc.core.abstracts.templates.poweredPIDF.PoweredPIDFPose;
import vcsc.core.abstracts.templates.poweredPIDF.PoweredPIDFState;

public class A_PoweredPIDFReset<S extends PoweredPIDFState<S, P>, P extends PoweredPIDFPose> extends Action<S> {
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
    public void loop() {

    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
