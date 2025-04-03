package vcsc.core.abstracts.templates.poweredPIDF.actions;

import static vcsc.core.abstracts.templates.poweredPIDF.actions.PIDMode.EXCEED;
import static vcsc.core.abstracts.templates.poweredPIDF.actions.PIDMode.SETTLE;

import vcsc.core.abstracts.action.Action;
import vcsc.core.abstracts.templates.poweredPIDF.PoweredPIDFPose;
import vcsc.core.abstracts.templates.poweredPIDF.PoweredPIDFState;

public class A_SetPoweredPIDFTargetPosition<S extends PoweredPIDFState<S, P>, P extends PoweredPIDFPose> extends Action<S> {
    private final PIDMode PIDMode;
    private final double targetPosition;
    private DIRECTION direction;
    private boolean _finished = false;

    public A_SetPoweredPIDFTargetPosition(Class<S> klass, double targetPosition, PIDMode PIDMode) {
        super(klass);
        this.PIDMode = PIDMode;
        this.targetPosition = targetPosition;
    }

    public A_SetPoweredPIDFTargetPosition(Class<S> klass, double targetPosition) {
        this(klass, targetPosition, SETTLE);
    }

    @Override
    public boolean start() {
        boolean started = super.start();
        if (!started) {
            return false; // If the action cannot start, return false
        }
        _finished = false;
        this.state.setTargetPosition(this, targetPosition);
        this.direction = (targetPosition > this.state.getTargetPosition()) ? DIRECTION.UP : DIRECTION.DOWN;
        return true;
    }

    @Override
    public void loop() {
        if (_finished) {
            return;
        }

        if (PIDMode == EXCEED) {
            if (direction == DIRECTION.UP && state.getRealPosition() > targetPosition) {
                end();
            } else if (direction == DIRECTION.DOWN && state.getRealPosition() < targetPosition) {
                end();
            }
        } else { // SETTLE
            if (state.idle() && state.getTargetPosition() == targetPosition) {
                end();
            }
        }
    }

    @Override
    protected void end() {
        super.end();
        _finished = true;
    }

    @Override
    public void cancel() {
        super.cancel();
        _finished = true; // Mark as finished to stop the action
        this.state.cancelMotion(this);
        end();
    }

    @Override
    public boolean isFinished() {
        return _finished;
    }

    private enum DIRECTION {
        UP,
        DOWN
    }
}
