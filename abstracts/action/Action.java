package vcsc.core.abstracts.action;

import java.util.Set;

import vcsc.core.abstracts.state.State;
import vcsc.core.abstracts.state.StateRegistry;
import vcsc.core.abstracts.task.Task;

public abstract class Action<S extends State<S>> implements Task {
    protected S state;

    public Action(Class<S> stateClass) {
        StateRegistry reg = StateRegistry.getInstance();
        this.state = reg.getState(stateClass);
    }

    // Start running
    public boolean start() {
        System.out.println("[" + this.getClass().getSimpleName() + "::start] Action started.");
        return state.tryLock(this);
    }

    protected void end() {
        System.out.println("[" + this.getClass().getSimpleName() + "::end] Action finished.");
        releaseLock();
    }

    protected void releaseLock() {
        state.releaseLock(this);
    }

    // Perform updates if needed
    public abstract void loop();

    // Check if finished
    public abstract boolean isFinished();

    public void cancel() {
        System.out.println("[" + this.getClass().getSimpleName() + "::cancel] Action canceled.");
    }

    public boolean isAsync() {
        return false; // Default implementation, can be overridden
    }

    public Set<Class<?>> requirements() {
        return Set.of(state.getClass());
    }

    public boolean conflictsWith(Task other) {
        return other.requirements().contains(state.getClass());
    }
}
