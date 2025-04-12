package vcsc.core.abstracts.templates.poweredPIDF.actions;

import vcsc.core.abstracts.action.Action;
import vcsc.core.abstracts.templates.poweredPIDF.PoweredPIDFPose;
import vcsc.core.abstracts.templates.poweredPIDF.PoweredPIDFState;

public class A_PoweredPIDFUpdater<S extends PoweredPIDFState<S, P>, P extends PoweredPIDFPose> extends Action<S> {
    Double power = null, targetPosition = null;
    P targetPose = null;
    boolean isFinished = true;

    public A_PoweredPIDFUpdater(Class<S> stateClass) {
        super(stateClass);
    }

    @Override
    public boolean start() {
        boolean started = super.start();
        if (started) {
            isFinished = false;
        }
        return started;
    }

    @Override
    public void loop() {
        if (isFinished) {
            return;
        }
        if (power != null) {
            state.setPower(this, power);
        } else if (targetPosition != null) {
            state.setTargetPosition(this, targetPosition);
        } else if (targetPose != null) {
            state.setTargetPose(this, targetPose);
        }
    }

    public void updatePower(double power) {
        this.power = power;
        this.targetPose = null;
        this.targetPosition = null;
        loop();
    }

    public void updateTargetPosition(double targetPosition) {
        this.targetPosition = targetPosition;
        this.power = null;
        this.targetPose = null;
        loop();
    }

    public void updateTargetPose(P targetPose) {
        this.targetPose = targetPose;
        this.power = null;
        this.targetPosition = null;
        loop();
    }

    @Override
    public void cancel() {
        super.cancel();
        end();
    }

    @Override
    protected void end() {
        super.end();
        isFinished = true;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
