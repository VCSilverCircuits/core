package vcsc.core.util.gamepad;

import java.util.HashMap;

import vcsc.core.abstracts.task.ParallelTask;
import vcsc.core.abstracts.task.Task;

public class BindingSet {
    HashMap<GamepadButton, Task> bindings = new HashMap<>();
    BindingSet parent;

    public BindingSet(BindingSet parent) {
        this.parent = parent;
    }

    public BindingSet() {
        this(null);
    }

    public void bind(GamepadButton button, Task task) {
        bindings.put(button, task);
    }

    public void bind(GamepadButton button, Task... tasks) {
        bind(button, new ParallelTask(tasks));
    }

    public Task getTask(GamepadButton button) {
        Task task = bindings.get(button);
        if (task == null && parent != null) {
            task = parent.getTask(button);
        }
        return task;
    }

}
