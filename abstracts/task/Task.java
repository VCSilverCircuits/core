package vcsc.core.abstracts.task;

public interface Task {
    boolean start();

    // Perform updates if needed
    void loop();

    // Check if finished
    boolean isFinished();

    void cancel();

    boolean isAsync();
}
