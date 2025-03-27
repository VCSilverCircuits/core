package vcsc.core.abstracts.templates.rotator.actions;

import vcsc.core.abstracts.action.Action;
import vcsc.core.abstracts.templates.rotator.RotatorPose;
import vcsc.core.abstracts.templates.rotator.RotatorState;

public class A_SetRotatorPose<S extends RotatorState<S, P>, P extends RotatorPose> extends Action<S> {
    P pose;

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
        return true;
    }

    @Override
    public void cancel() {
        end();
    }
}
