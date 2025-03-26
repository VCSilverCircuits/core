package vcsc.core.abstracts.templates.poweredPIDF;

import vcsc.core.abstracts.action.Action;
import vcsc.core.abstracts.actuator.Actuator;
import vcsc.core.abstracts.state.State;

public class PoweredPIDFState<S extends PoweredPIDFState<S, P>, P extends PoweredPIDFPose> extends State<S> {
    protected PoweredPIDFActuator<S, P> primaryActuator;
    protected double power;
    protected double targetPosition;
    protected double speed;
    P targetPose;

    public PoweredPIDFState() {
        super();
    }

    @Override
    public void registerActuator(Actuator<S> actuator) {
        super.registerActuator(actuator);
        primaryActuator = (PoweredPIDFActuator<S, P>) actuators.get(0);
    }

    public double getPower() {
        return power;
    }

    public void setPower(Action<S> action, double power) {
        if (this.power != 0) {
            targetPosition = getRealPosition();
        }
        this.power = power;
        notifyActuators();
    }

    public void cancelMotion(Action<S> action) {
        assertLock(action);
        this.power = 0;
        targetPosition = getRealPosition();
        notifyActuators();
    }

    public void reset(Action<S> action) {
        primaryActuator.reset();
    }

    public double getRealPosition() {
        return primaryActuator.getPosition();
    }

    public double getCurrent() {
        return primaryActuator.getCurrent();
    }

    public double getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(Action<S> action, double targetPosition) {
        setTargetPosition(action, targetPosition, false);
    }

    public void setTargetPosition(Action<S> action, double targetPosition, boolean suppressNotification) {
        assertLock(action);
        this.targetPosition = targetPosition;
        if (!suppressNotification) {
            notifyActuators();
        }
    }

    public P getTargetPose() {
        return targetPose;
    }

    public void setTargetPose(Action<S> action, P targetPose) {
        assertLock(action);
        this.targetPose = targetPose;
        setTargetPosition(action, targetPose.getPosition());
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(Action<S> action, double speed) {
        assertLock(action);
        this.speed = speed;
        notifyActuators();
    }

}
