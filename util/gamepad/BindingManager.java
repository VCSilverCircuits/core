package vcsc.core.util.gamepad;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.HashMap;

import vcsc.core.abstracts.task.Task;
import vcsc.core.abstracts.task.TaskManager;

public class BindingManager {
    static BindingManager instance = new BindingManager();
    HashMap<RobotMode, BindingSet> gamepad1Bindings = new HashMap<>();
    HashMap<RobotMode, BindingSet> gamepad2Bindings = new HashMap<>();

    public static BindingManager getInstance() {
        return instance;
    }

    public void setGamepad1Bindings(RobotMode mode, BindingSet gamepad1Bindings) {
        this.gamepad1Bindings.put(mode, gamepad1Bindings);
    }

    public void setGamepad2Bindings(RobotMode mode, BindingSet gamepad2Bindings) {
        this.gamepad2Bindings.put(mode, gamepad2Bindings);
    }

    public void setBindings(RobotMode mode, BindingSet gamepad1Bindings, BindingSet gamepad2Bindings) {
        this.gamepad1Bindings.put(mode, gamepad1Bindings);
        this.gamepad2Bindings.put(mode, gamepad2Bindings);
    }

    public BindingSet getGamepad1Bindings(RobotMode mode) {
        return gamepad1Bindings.get(mode);
    }

    public BindingSet getGamepad2Bindings(RobotMode mode) {
        return gamepad2Bindings.get(mode);
    }

    public void loop(Gamepad gamepad1, Gamepad gamepad2, RobotMode mode) {
        BindingSet gamepad1Bindings = getGamepad1Bindings(mode);
        BindingSet gamepad2Bindings = getGamepad2Bindings(mode);

        for (GamepadButton btn : GamepadButton.values()) {
            if (btn.isPressed(gamepad1)) {
                Task task = gamepad1Bindings.getTask(btn);
                if (task != null) {
                    TaskManager.getInstance().runTask(task);
                }
            }
            if (btn.isPressed(gamepad2)) {
                Task task = gamepad2Bindings.getTask(btn);
                if (task != null) {
                    TaskManager.getInstance().runTask(task);
                }
            }
        }
    }

}
