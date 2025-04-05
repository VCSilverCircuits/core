package vcsc.core.abstracts.power;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import java.util.HashSet;

import vcsc.core.abstracts.actuator.Actuator;

public class PowerManager {
    VoltageSensor voltageSensor;
    public PowerManager(HardwareMap hardwareMap) {
        this.voltageSensor = hardwareMap.voltageSensor.iterator().next();
    }
    public void currentPowerEstimate() {
        HashSet<Actuator<?>> actuators = new HashSet<>();
    }

    public double getCurrentVoltage() {
        return voltageSensor.getVoltage();
    }
}
