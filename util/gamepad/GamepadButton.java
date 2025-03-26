package vcsc.core.util.gamepad;

import com.qualcomm.robotcore.hardware.Gamepad;

public enum GamepadButton {
    Y, X, A, B, LEFT_BUMPER, RIGHT_BUMPER, BACK,
    START, DPAD_UP, DPAD_DOWN, DPAD_LEFT, DPAD_RIGHT,
    LEFT_STICK_BUTTON, RIGHT_STICK_BUTTON, LEFT_TRIGGER, RIGHT_TRIGGER;

    public boolean isPressed(Gamepad gamepad) {
        switch (this) {
            case A:
                return gamepad.a;
            case B:
                return gamepad.b;
            case X:
                return gamepad.x;
            case Y:
                return gamepad.y;
            case LEFT_BUMPER:
                return gamepad.left_bumper;
            case RIGHT_BUMPER:
                return gamepad.right_bumper;
            case BACK:
                return gamepad.back;
            case START:
                return gamepad.start;
            case DPAD_UP:
                return gamepad.dpad_up;
            case DPAD_DOWN:
                return gamepad.dpad_down;
            case DPAD_LEFT:
                return gamepad.dpad_left;
            case DPAD_RIGHT:
                return gamepad.dpad_right;
            case LEFT_STICK_BUTTON:
                return gamepad.left_stick_button;
            case RIGHT_STICK_BUTTON:
                return gamepad.right_stick_button;
            case LEFT_TRIGGER:
                return gamepad.left_trigger > 0;
            case RIGHT_TRIGGER:
                return gamepad.right_trigger > 0;
            default:
                return false;
        }
    }
}