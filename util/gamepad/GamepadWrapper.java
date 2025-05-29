package vcsc.core.util.gamepad;

import com.qualcomm.robotcore.hardware.Gamepad;
import java.util.HashMap;

public class GamepadWrapper {
  HashMap<GamepadButton, Boolean> debounceMap;
  Gamepad gamepad;

  public GamepadWrapper(Gamepad gamepad) {
    debounceMap = new HashMap<>();
    this.gamepad = gamepad;
    for (GamepadButton btn : GamepadButton.values()) {
      debounceMap.put(btn, false);
    }
  }

  public boolean isDebounced(GamepadButton btn) {
    return Boolean.TRUE.equals(debounceMap.get(btn));
  }

  public void debounce(GamepadButton btn) {
    debounceMap.put(btn, true);
  }

  public boolean isPressed(GamepadButton btn) {
    return btn.isPressed(gamepad) && !isDebounced(btn);
  }

  public boolean debounceIsPressed(GamepadButton btn) {
    if (isPressed(btn) && !gamepad.options) {
      debounce(btn);
      return true;
    }
    return false;
  }

  public void undebounce(GamepadButton btn) {
    debounceMap.put(btn, false);
  }

  public void loop() {
    //        System.out.println("=== GAMEPAD BUTTONS ===");
    for (GamepadButton btn : GamepadButton.values()) {
      //            System.out.println(btn + ": " + btn.isPressed(gamepad));
      if (!btn.isPressed(gamepad)) {
        undebounce(btn);
      }
    }
  }
}
