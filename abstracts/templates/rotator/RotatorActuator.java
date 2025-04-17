package vcsc.core.abstracts.templates.rotator;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import vcsc.core.abstracts.actuator.Actuator;
import vcsc.core.abstracts.state.State;

public class RotatorActuator<S extends RotatorState<S, P>, P extends RotatorPose> extends Actuator<S> {
    protected ServoImplEx servo;
    protected double angle;
    protected boolean hasPosition = false;

    public RotatorActuator(HardwareMap hardwareMap, String name) {
        servo = hardwareMap.get(ServoImplEx.class, name);
    }

    @Override
    public void loop() {
//        double rotPosition = angle; // Convert angle to a value between 0 and 1 for the servo
//        if (angle != servo.getPosition()) {
        if (hasPosition) {
            servo.setPosition(angle);
        }
//        }
    }

    @Override
    public void updateState(State<S> newState) {
        RotatorState<S, P> wristState = (RotatorState<S, P>) newState;
        angle = wristState.getAngle();
        hasPosition = true;
    }

}
