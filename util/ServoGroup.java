package vcsc.core.util;

import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.ServoConfigurationType;

// TODO: Make this actually work maybe
public class ServoGroup extends ServoImplEx {
    ServoImplEx[] servos;

    public ServoGroup(ServoImplEx... servos) {
        super((ServoControllerEx) servos[0].getController(), servos[0].getPortNumber(), ServoConfigurationType.getStandardServoType());
        this.servos = servos;
    }
}
