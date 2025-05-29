package vcsc.core.abstracts.state;

import java.util.HashMap;
import java.util.Map;

public class StateRegistry {
  private static final StateRegistry instance = new StateRegistry();
  private final Map<Class<?>, State<?>> states = new HashMap<>();

  private StateRegistry() {}

  public static StateRegistry getInstance() {
    return instance;
  }

  public <T extends State<?>> void registerState(T state) {
    states.put(state.getClass(), state);
  }

  @SafeVarargs
  public final <T extends State<?>> void registerStates(T... stateArray) {
    for (T state : stateArray) {
      registerState(state);
    }
  }

  public <T extends State<T>> T getState(Class<T> stateClass) {
    if (!states.containsKey(stateClass)) {
      throw new IllegalArgumentException("State not registered: " + stateClass.getName());
    }
    return stateClass.cast(states.get(stateClass));
  }

  public void clearStates() {
    states.clear();
  }
}
