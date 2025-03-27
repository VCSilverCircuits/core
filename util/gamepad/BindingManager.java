package vcsc.core.util.gamepad;

import java.util.HashMap;

import vcsc.core.abstracts.task.Task;
import vcsc.core.abstracts.task.TaskManager;

public class BindingManager {
    static BindingManager instance = new BindingManager();
    HashMap<RobotMode, BindingSet> gamepad1Bindings = new HashMap<>();
    HashMap<RobotMode, BindingSet> gamepad2Bindings = new HashMap<>();
    RobotMode defaultMode = null;

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
        if (!gamepad1Bindings.containsKey(mode)) {
            if (!gamepad1Bindings.containsKey(defaultMode)) {
                throw new IllegalArgumentException("No default gamepad1 bindings found");
            }
            return gamepad1Bindings.get(defaultMode);
        }
        return gamepad1Bindings.get(mode);
    }

    public BindingSet getGamepad2Bindings(RobotMode mode) {
        if (!gamepad2Bindings.containsKey(mode)) {
            if (!gamepad2Bindings.containsKey(defaultMode)) {
                throw new IllegalArgumentException("No default gamepad2 bindings found");
            }
            return gamepad2Bindings.get(defaultMode);
        }
        return gamepad2Bindings.get(mode);
    }

    public void loop(GamepadWrapper gamepad1, GamepadWrapper gamepad2, RobotMode mode) {
        BindingSet gamepad1Bindings = getGamepad1Bindings(mode);
        BindingSet gamepad2Bindings = getGamepad2Bindings(mode);

        for (GamepadButton btn : GamepadButton.values()) {
            if (gamepad1.debounceIsPressed(btn)) {
                Task task = gamepad1Bindings.getTask(btn);
                if (task != null) {
                    System.out.println(btn + " pressed on Gamepad1. Triggering task " + task.getClass().getSimpleName());
                    TaskManager.getInstance().runTask(task);
                } else {
                    System.out.println(btn + " pressed on Gamepad1. No task bound.");
                }
            }
            if (gamepad2.debounceIsPressed(btn)) {
                Task task = gamepad2Bindings.getTask(btn);
                if (task != null) {
                    System.out.println(btn + " pressed on Gamepad2. Triggering task " + task.getClass().getSimpleName());
                    TaskManager.getInstance().runTask(task);
                } else {
                    System.out.println(btn + " pressed on Gamepad2. No task bound.");
                }
            }
        }
    }

    public void setDefaultMode(RobotMode mode) {
        defaultMode = mode;
    }

}
