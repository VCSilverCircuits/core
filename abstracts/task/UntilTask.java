package vcsc.core.abstracts.task;

import java.util.function.Supplier;

public class UntilTask implements Task {
    Supplier<Boolean> _condition;
    boolean _finished = false;

    public UntilTask(Supplier<Boolean> condition) {
        _condition = condition;
    }

    @Override
    public boolean start() {
        return true;
    }

    @Override
    public void loop() {
        if (!_finished && _condition.get()) {
            _finished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return _finished || _condition.get();
    }

    @Override
    public void cancel() {
        _finished = true;
    }

    @Override
    public boolean isAsync() {
        return false;
    }
}
