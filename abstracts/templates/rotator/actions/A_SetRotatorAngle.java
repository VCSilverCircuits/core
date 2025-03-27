package vcsc.core.abstracts.templates.rotator.actions;

import vcsc.core.abstracts.action.Action;
import vcsc.core.abstracts.templates.rotator.RotatorPose;
import vcsc.core.abstracts.templates.rotator.RotatorState;

public class A_SetRotatorAngle<S extends RotatorState<S, P>, P extends RotatorPose> extends Action<S> {
    double angle;

    public A_SetRotatorAngle(Class<S> klass, double angle) {
        super(klass);
        this.angle = angle;
    }

    @Override
    public boolean start() {
        boolean started = super.start();
        if (!started) {
            end();
            return false; // If the action cannot start, return false
        }
        // Set the angle to a default value (e.g., 0 degrees)
        this.state.setAngle(this, angle);
        return true;
    }

    @Override
    public void loop() {
        if (state.idle() && state.getAngle() == angle) {
            end(); // End the action if the angle is reached
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
