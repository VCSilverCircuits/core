package vcsc.core.abstracts.templates.poweredPIDF.actions;

import vcsc.core.abstracts.action.Action;
import vcsc.core.abstracts.templates.poweredPIDF.PoweredPIDFPose;
import vcsc.core.abstracts.templates.poweredPIDF.PoweredPIDFState;

public class A_SetPoweredPIDFPower<S extends PoweredPIDFState<S, P>, P extends PoweredPIDFPose> extends Action<S> {
    double power;

    public A_SetPoweredPIDFPower(Class<S> klass, double power) {
        super(klass);
        this.power = power;
    }

    @Override
    public boolean start() {
        boolean started = super.start();
        if (!started) {
            return false; // If the action cannot start, return false
        }
        this.state.setPower(this, power); // Set the power to the specified value
        return true;
    }

    @Override
    public void loop() {
        if (state.getPower() == power) {
            end(); // End the action if the power is set to the desired value
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
