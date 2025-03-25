package vcsc.core.abstracts.state;

import vcsc.core.abstracts.action.Action;
import vcsc.core.abstracts.actuator.Actuator;
import vcsc.core.abstracts.actuator.PoweredPIDFActuator;

public class PoweredPIDFState extends State<PoweredPIDFState> {
    private PoweredPIDFActuator primaryActuator;
    private double power;
    private double targetPosition;
    private double speed;

    public PoweredPIDFState() {
        super();
    }

    @Override
    public void registerActuator(Actuator actuator) {
        super.registerActuator(actuator);
        primaryActuator = (PoweredPIDFActuator) actuators.get(0);
    }

    public double getPower() {
        return power;
    }

    public void setPower(Action<PoweredPIDFState> action, double power) {
        this.power = power;
        notifyActuators();
        targetPosition = getRealPosition();
    }

    public void reset(Action<PoweredPIDFState> action) {
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

    public void setTargetPosition(Action<PoweredPIDFState> action, double targetPosition) {
        setTargetPosition(action, targetPosition, false);
    }

    public void setTargetPosition(Action<PoweredPIDFState> action, double targetPosition, boolean suppressNotification) {
        assertLock(action);
        this.targetPosition = targetPosition;
        if (!suppressNotification) {
            notifyActuators();
        }
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(Action<PoweredPIDFState> action, double speed) {
        assertLock(action);
        this.speed = speed;
    }

}
