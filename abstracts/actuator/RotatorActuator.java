package vcsc.core.abstracts.actuator;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import vcsc.core.abstracts.state.RotatorState;
import vcsc.core.abstracts.state.State;
import vcsc.teamcode.cmp.elbow.ElbowState;

public class RotatorActuator extends Actuator {
    ServoImplEx servo;
    double angle;

    public RotatorActuator(HardwareMap hardwareMap, String name) {
        servo = hardwareMap.get(ServoImplEx.class, name);
    }

    @Override
    public void loop() {
        double rotPosition = angle / 180.0; // Convert angle to a value between 0 and 1 for the servo
        servo.setPosition(angle);
    }

    @Override
    public void updateState(State<?> newState) {
        RotatorState<?, ?> wristState = (RotatorState<?, ?>) newState;
        angle = wristState.getAngle();
    }

}
