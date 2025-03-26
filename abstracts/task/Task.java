package vcsc.core.abstracts.task;

import java.util.Set;

public interface Task {
    boolean start();

    // Perform updates if needed
    void loop();

    // Check if finished
    boolean isFinished();

    void cancel();

    boolean isAsync();

    boolean conflictsWith(Task other);

    Set<Class<?>> requirements();
}
