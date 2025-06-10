package org.firstinspires.ftc.teamcode.vcsc.core.abstracts.actuator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ActuatorRegistry {
  private static final ActuatorRegistry instance = new ActuatorRegistry();
  private final Map<Class<?>, Actuator<?>> actuators = new HashMap<>();

  private ActuatorRegistry() {}

  public static ActuatorRegistry getInstance() {
    return instance;
  }

  public <T extends Actuator<?>> void registerState(T actuator) {
    actuators.put(actuator.getClass(), actuator);
  }

  @SafeVarargs
  public final <T extends Actuator<?>> void registerActuators(T... actuatorArray) {
    for (T actuator : actuatorArray) {
      registerState(actuator);
    }
  }

  public HashSet<Actuator<?>> getActuators() {
    return (HashSet<Actuator<?>>) actuators.values();
  }

  public <T extends Actuator<?>> T getActuator(Class<T> actuatorClass) {
    if (!actuators.containsKey(actuatorClass)) {
      throw new IllegalArgumentException("State not registered: " + actuatorClass.getName());
    }
    return actuatorClass.cast(actuators.get(actuatorClass));
  }
}
