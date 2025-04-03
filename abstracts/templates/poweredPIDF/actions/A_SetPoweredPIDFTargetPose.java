package vcsc.core.abstracts.templates.poweredPIDF.actions;

import static vcsc.core.abstracts.templates.poweredPIDF.actions.PIDMode.EXCEED;

import vcsc.core.abstracts.action.Action;
import vcsc.core.abstracts.templates.poweredPIDF.PoweredPIDFPose;
import vcsc.core.abstracts.templates.poweredPIDF.PoweredPIDFState;

public class A_SetPoweredPIDFTargetPose<S extends PoweredPIDFState<S, P>, P extends PoweredPIDFPose> extends Action<S> {
    private final PIDMode PIDMode;
    private final P targetPose;
    private DIRECTION direction;
    private boolean _finished = false;

    public A_SetPoweredPIDFTargetPose(Class<S> klass, P targetPose, PIDMode PIDMode) {
        super(klass);
        this.PIDMode = PIDMode;
        this.targetPose = targetPose;
    }

    public A_SetPoweredPIDFTargetPose(Class<S> klass, P targetPose) {
        this(klass, targetPose, vcsc.core.abstracts.templates.poweredPIDF.actions.PIDMode.SETTLE);
    }

    @Override
    public boolean start() {
        boolean started = super.start();
        if (!started) {
            end();
            return false; // If the action cannot start, return false
        }
        _finished = false;
        this.state.setTargetPose(this, targetPose);
        this.direction = (targetPose.getPosition() > this.state.getTargetPosition()) ? DIRECTION.UP : DIRECTION.DOWN;
        return true;
    }

    @Override
    public void loop() {
        System.out.println("[A_SetPoweredPIDFTargetPose::loop] Class name: " + this.getClass().getSimpleName());
        System.out.println("[A_SetPoweredPIDFTargetPose::loop] idle: " + state.idle());
        System.out.println("[A_SetPoweredPIDFTargetPose::loop] State target pose: " + state.getTargetPose());
        System.out.println("[A_SetPoweredPIDFTargetPose::loop] Action target pose: " + targetPose);

        if (PIDMode == EXCEED) {
            if (direction == DIRECTION.UP && state.getRealPosition() > state.getTargetPosition()) {
                System.out.println("[A_SetPoweredPIDFTargetPose::loop] Exceeding UP");
                end();
            } else if (direction == DIRECTION.DOWN && state.getRealPosition() < state.getTargetPosition()) {
                System.out.println("[A_SetPoweredPIDFTargetPose::loop] Exceeding DOWN");
                end();
            }
        } else { // SETTLE
            if (state.idle() && state.getTargetPose() == targetPose) {
                System.out.println("[A_SetPoweredPIDFTargetPose::loop] Settled");
                end();
            }
        }
    }

    @Override
    protected void end() {
        _finished = true; // Mark as finished to stop the action
        super.end();
    }

    @Override
    public void cancel() {
        this.state.cancelMotion(this);
        super.end();
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
