package vcsc.core.abstracts.behavior;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import vcsc.core.abstracts.state.State;
import vcsc.core.abstracts.task.Task;

public abstract class Behavior implements Task {
    protected ArrayList<Class<? extends State<?>>> requirements = new ArrayList<>();

    public abstract boolean start();

    private void end() {
    }

    // Perform updates if needed
    public abstract void loop();

    // Check if finished
    public abstract boolean isFinished();

    public void cancel() {

    }

    protected final void addRequirement(Class<? extends State<?>> requirement) {
        requirements.add(requirement);
    }

    public final Set<Class<?>> requirements() {
        return new HashSet<>(requirements);
    }

    public final boolean conflictsWith(Task other) {
        for (Class<?> req : other.requirements()) {
            if (this.requirements.contains(req)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAsync() {
        return false;
    }
}
